/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.project_structure;

import com.bjit.context.ContextGeneration;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.bjit.project_structure.model.*;
import com.bjit.project_structure.processors.PSProcessor;
import com.bjit.project_structure.processors.TaskProcessor;
import com.bjit.project_structure.utilities.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matrixone.apps.domain.util.ContextUtil;
import com.matrixone.apps.domain.util.FrameworkException;
import matrix.db.BusinessObject;
import matrix.db.Context;
import matrix.db.Relationship;
import matrix.db.RelationshipType;
import matrix.util.MatrixException;
import net.sf.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author Bjit
 */
public class TaskImportProcess {

    private static final org.apache.log4j.Logger TASK_IMPORT_PROCESS = org.apache.log4j.Logger.getLogger(TaskImportProcess.class);
    private static Integer numberOfELements = 0;
    private Boolean deleteAllTasksAndMileStones = false;
    private Boolean deleteUnSynchedTasksOrMileStones = true;

    public static void main(String[] args) throws Exception {
        String str = "{\"Project\":\"N8064AXC1\",\"TableData\":[{\"Project\":\"N8064AXC1\",\"Activity\":\"0\",\"ActivityDescription\":\"Top Activity\",\"ActivityStatus\":\"Released\",\"ActivityType\":\"WBS Element\",\"ParentActivity\":null,\"AcutalEndDate\":null,\"NetQuantity\":null,\"Unit\":null,\"ProductType\":null,\"ProductTypeDesc\":null,\"Pos\":null,\"Selectable\":\"0\",\"ContractDeliverableNo\":null},{\"Project\":\"N8064AXC1\",\"Activity\":\"90\",\"ActivityDescription\":\"Milestones\",\"ActivityStatus\":\"Released\",\"ActivityType\":\"WBS Element\",\"ParentActivity\":\"0\",\"AcutalEndDate\":null,\"NetQuantity\":null,\"Unit\":null,\"ProductType\":null,\"ProductTypeDesc\":null,\"Pos\":null,\"Selectable\":\"0\",\"ContractDeliverableNo\":null},{\"Project\":\"N8064AXC1\",\"Activity\":\"9010\",\"ActivityDescription\":\"Recognition milestones\",\"ActivityStatus\":\"Released\",\"ActivityType\":\"WBS Element\",\"ParentActivity\":\"90\",\"AcutalEndDate\":null,\"NetQuantity\":null,\"Unit\":null,\"ProductType\":null,\"ProductTypeDesc\":null,\"Pos\":null,\"Selectable\":\"0\",\"ContractDeliverableNo\":null},{\"Project\":\"N8064AXC1\",\"Activity\":\"901001\",\"ActivityDescription\":\"100% Recognition\",\"ActivityStatus\":\"Released\",\"ActivityType\":\"Milestone\",\"ParentActivity\":\"9010\",\"AcutalEndDate\":\"2019-01-29 13:41:00\",\"NetQuantity\":null,\"Unit\":null,\"ProductType\":null,\"ProductTypeDesc\":null,\"Pos\":null,\"Selectable\":\"1\",\"ContractDeliverableNo\":null}]}";
        ObjectMapper objectMapper = new ObjectMapper();
        WBSImportBean wbsImport = objectMapper.readValue(str, WBSImportBean.class);
        TaskImportProcess taskImport = new TaskImportProcess();
        wbsImport = taskImport.projectAndTaskCreate(wbsImport);
        System.out.println("Project --> " + wbsImport.getProjectCode());
        wbsImport.getTableData().forEach((TaskBean taskBean) -> {
            TASK_IMPORT_PROCESS.info("Activity:-->> " + taskBean.getParentActivity());
            TASK_IMPORT_PROCESS.info("Parent Activity-->> " + taskBean.getParentActivity());
        });
    }

    public WBSImportBean projectAndTaskCreate(WBSImportBean importBean) throws Exception {
        if (NullOrEmptyChecker.isNull(importBean)) {
            return importBean;
        }
        ActivitiesBean activitiesBean = new ActivitiesBean();
        activitiesBean.setProjectCode(importBean.getProjectCode());
        activitiesBean.setDescription(importBean.getDescription());
        HashMap<String, String> taskNamesAndIds = new HashMap<>();
        try {
            Context context = ContextGeneration.createContext();
            if (!context.isConnected()) {
                throw new Exception("Context couldn't be connected");
            }

            try {
                TASK_IMPORT_PROCESS.info("Starting the transaction");
                ContextUtil.startTransaction(context, true);
                try {
                    String newProjectSpaceObjectId = PSProcessor.createProjectSpace(context, activitiesBean);
                    TASK_IMPORT_PROCESS.debug("New projectSpace Id : " + newProjectSpaceObjectId);
                    taskNamesAndIds.put(importBean.getProjectCode(), newProjectSpaceObjectId);

                    ProjectSpaceUtil projectSpaceUtil = new ProjectSpaceUtil();

                    HashMap<String, String> projectSpaceProperties = getCreateOrUpdatePropertiesAndSetInterfaces(context, newProjectSpaceObjectId, Constants.UPDATE, activitiesBean);
                    projectSpaceUtil.updateItem(context, newProjectSpaceObjectId, projectSpaceProperties);
                    HashMap<String, ActivityModel> projectSpaceExpandedMap = projectSpaceUtil.expandTask(context, newProjectSpaceObjectId);

                    List<TaskBean> sortTasksAndMileStonesData = sortTasksAndMileStonesData(importBean.getTableData());
                    importBean.setTableData(sortTasksAndMileStonesData);

                    createTaskOrMileStone(context, importBean, newProjectSpaceObjectId, taskNamesAndIds, projectSpaceExpandedMap, projectSpaceUtil);

                    TASK_IMPORT_PROCESS.info("Committing the transaction");
                    ContextUtil.commitTransaction(context);
                } catch (KeyManagementException | NoSuchAlgorithmException exp) {
                    TASK_IMPORT_PROCESS.error("Aborting the transaction");
                    TASK_IMPORT_PROCESS.error(exp);
                    ContextUtil.abortTransaction(context);
                    throw exp;
                } catch (Exception exp) {
                    TASK_IMPORT_PROCESS.error("Aborting the transaction");
                    TASK_IMPORT_PROCESS.error(exp);
                    ContextUtil.abortTransaction(context);
                    throw exp;
                }
            } catch (Exception exp) {
                TASK_IMPORT_PROCESS.error(exp);
                throw exp;
            }
        } catch (Exception exp) {
            TASK_IMPORT_PROCESS.error(exp);
            throw exp;
        }
        return importBean;
    }

    @Deprecated
    private void populateTheTaskList(HashMap<String, List<TaskBean>> parentChildRelationShip, List<TaskBean> taskList, String taskName, HashMap<String, String> traversed) {

        if (traversed.containsKey(taskName)) {
            return;
        }
        traversed.put(taskName, taskName);

        List<TaskBean> childTaskList = parentChildRelationShip.get(taskName);

        if (NullOrEmptyChecker.isNullOrEmpty(childTaskList)) {
            return;
        }

        taskList.addAll(childTaskList);

        TASK_IMPORT_PROCESS.debug("Parent Task Name : " + taskName);
        childTaskList.forEach((TaskBean task) -> {
            String childTaskName = task.getActivity();
            populateTheTaskList(parentChildRelationShip, taskList, childTaskName, traversed);
        });
    }

    private void sortTheTaskList(HashMap<String, List<TaskBean>> parentChildRelationShip, List<TaskBean> taskList, String taskName, HashMap<String, String> traversed) {

        if (traversed.containsKey(taskName)) {
            TASK_IMPORT_PROCESS.debug("Task : '" + taskName + "' is a duplicate task in the task list ");
            return;
        }
        traversed.put(taskName, taskName);

        List<TaskBean> childTaskList = parentChildRelationShip.get(taskName);

        if (NullOrEmptyChecker.isNullOrEmpty(childTaskList)) {
            TASK_IMPORT_PROCESS.debug("Task '" + taskName + "' has no children");
            return;
        }

        TASK_IMPORT_PROCESS.warn("Searching child tasks for : '" + taskName + "'");
        childTaskList.forEach((TaskBean task) -> {
            taskList.add(task);

            String childTaskName = task.getActivity();
            sortTheTaskList(parentChildRelationShip, taskList, childTaskName, traversed);
        });
    }

    private List<TaskBean> sortTasksAndMileStonesData(List<TaskBean> taskAndMileStoneData) {
        JSON json = new JSON(Boolean.FALSE);

        int totalTasks = taskAndMileStoneData.size();
        TASK_IMPORT_PROCESS.info("Tasks before sorting " + json.serialize(taskAndMileStoneData));
        TASK_IMPORT_PROCESS.info("Number of Tasks before sorting : " + totalTasks);

        HashMap<String, List<TaskBean>> parentChildRelationShip = new HashMap<>();
        HashMap<String, String> childParentTracker = new HashMap<>();
        taskAndMileStoneData.forEach((TaskBean taskOrMileStone) -> {
            String parentActivity = taskOrMileStone.getParentActivity();
            if (NullOrEmptyChecker.isNullOrEmpty(parentActivity)) {
                parentActivity = "root";
            }

            if (parentChildRelationShip.containsKey(parentActivity)) {
                List<TaskBean> tasksAndMileStonesAsChildren = parentChildRelationShip.get(parentActivity);
                tasksAndMileStonesAsChildren.add(taskOrMileStone);
            } else {
                List<TaskBean> tasksAndMileStonesAsChildren = new ArrayList<>();
                tasksAndMileStonesAsChildren.add(taskOrMileStone);
                parentChildRelationShip.put(parentActivity, tasksAndMileStonesAsChildren);
            }

            childParentTracker.put(taskOrMileStone.getActivity(), parentActivity);
        });

        TASK_IMPORT_PROCESS.info("Parent Child Relationship " + json.serialize(parentChildRelationShip));

        /**
         * Starting another new process
         */
        List<TaskBean> rootTasks = parentChildRelationShip.get("root");
        List<TaskBean> sortedTasks = new ArrayList<>();
        HashMap<String, String> traversed = new HashMap<>();

        rootTasks.forEach((TaskBean task) -> {
            String taskName = task.getActivity();
            sortedTasks.add(task);
            TASK_IMPORT_PROCESS.warn("Searching child tasks for : '" + taskName + "'");

//            populateTheTaskList(parentChildRelationShip, sortedTasks, taskName, traversed);
            sortTheTaskList(parentChildRelationShip, sortedTasks, taskName, traversed);
        });
        TASK_IMPORT_PROCESS.warn("Sorted Tasks : " + json.serialize(sortedTasks));

        int sortedTotalTasks = sortedTasks.size();
        int missingTasksAmount = totalTasks - sortedTotalTasks;
        TASK_IMPORT_PROCESS.warn("Missed after sorting : " + missingTasksAmount);

        showTheMissingTaskList(missingTasksAmount, taskAndMileStoneData, sortedTasks);

        return sortedTasks;
    }

    private void showTheMissingTaskList(int missingTasksAmount, List<TaskBean> taskAndMileStoneData, List<TaskBean> sortedTasks) {
        TASK_IMPORT_PROCESS.debug("##################################################");
        TASK_IMPORT_PROCESS.debug("# Missed tasks and milestones list after sorting #");
        TASK_IMPORT_PROCESS.debug("##################################################");
        if (missingTasksAmount > 0) {
            List<TaskBean> originalList = new ArrayList<>(taskAndMileStoneData);
            List<TaskBean> sortedList = new ArrayList<>(sortedTasks);
            originalList.removeAll(sortedList);

            originalList.forEach(action -> {
                TASK_IMPORT_PROCESS.warn("Task name : '" + action.getActivity() + "' parent name '" + action.getParentActivity() + "'");
            });

        } else if (missingTasksAmount < 0) {
            List<TaskBean> originalList = new ArrayList<>(taskAndMileStoneData);
            List<TaskBean> sortedList = new ArrayList<>(sortedTasks);
            sortedList.removeAll(originalList);

            sortedList.forEach(action -> {
                TASK_IMPORT_PROCESS.warn("Task name : '" + action.getActivity() + "' parent name '" + action.getParentActivity() + "'");
            });
        }

        TASK_IMPORT_PROCESS.debug("##################################################");
        TASK_IMPORT_PROCESS.debug("# Missed tasks and milestones list after sorting #");
        TASK_IMPORT_PROCESS.debug("##################################################");
    }

    private TaskDataBean getTaskOrMileStoneDataFromFile(String fileName) throws Exception {
        TASK_IMPORT_PROCESS.info("Getting task data from service");
        TaskDataBean taskData = getDataFromService(TaskDataBean.class, fileName);
        return taskData;
    }

    private HashMap<String, ActivityModel> createTaskOrMileStone(Context context, TaskDataBean taskData, String newProjectSpaceObjectId, HashMap<String, String> taskNamesAndIds, HashMap<String, ActivityModel> projectSpaceExpandedMap, ProjectSpaceUtil projectSpaceUtil) throws MatrixException {
//        if (deleteAllTasksAndMileStones) {
//            projectSpaceUtil.deleteTaskAndMileStone(context, projectSpaceExpandedMap);
//        }
        if (NullOrEmptyChecker.isNull(taskData)) {
            throw new NullPointerException("'TaskData' is empty");
        }

        if (NullOrEmptyChecker.isNullOrEmpty(taskData.getTableData())) {
            BusinessObject projectSpaceObject = new BusinessObject(newProjectSpaceObjectId);
            projectSpaceObject.open(context);
            String projectSpaceName = projectSpaceObject.getName();
            projectSpaceObject.close(context);
            projectSpaceObject = null;
            throw new NullPointerException("No Task or MileStone found under '" + projectSpaceName + "' Project Space");
        }

        taskData.getTableData().forEach((TaskBean task) -> {
            try {
                String activityTaskName = task.getActivity();
                TASK_IMPORT_PROCESS.debug("Task name : " + activityTaskName);

                if (NullOrEmptyChecker.isNullOrEmpty(task.getActivityType())) {
                    TASK_IMPORT_PROCESS.warn("'" + activityTaskName + "' skipping the task as the type of the task is empty");
                    return;
                }

                String parentActivityName = task.getParentActivity();
                String parentIdFromLNJsonResponse = NullOrEmptyChecker.isNull(task.getParentActivity()) ? newProjectSpaceObjectId : taskNamesAndIds.get(parentActivityName);

                if (NullOrEmptyChecker.isNullOrEmpty(parentIdFromLNJsonResponse)) {
                    String parentActivity = task.getParentActivity();
                    ActivityModel parentActivityModel = projectSpaceExpandedMap.get(parentActivity);
                    if (NullOrEmptyChecker.isNull(parentActivityModel)) {
                        TASK_IMPORT_PROCESS.warn("'" + activityTaskName + "' has been being skipped as it's parent '" + parentActivity + "' has not found in the system");
                        return;
                    }

                    parentIdFromLNJsonResponse = parentActivityModel.getActivityId();
                    if (NullOrEmptyChecker.isNullOrEmpty(parentIdFromLNJsonResponse)) {
                        throw new RuntimeException("Faulty ProjectSpace and Task structure");
                    }
                }

                String activityName = task.getActivity();
                String activityType = task.getActivityType();

                TASK_IMPORT_PROCESS.debug("Task parent activity : " + parentActivityName);
                TASK_IMPORT_PROCESS.debug("Task parent id : " + parentIdFromLNJsonResponse);
                TASK_IMPORT_PROCESS.debug("Activity Type : '" + activityType + "'");

                TASK_IMPORT_PROCESS.debug("################");
                TASK_IMPORT_PROCESS.debug("# FOUND A TASK #");
                TASK_IMPORT_PROCESS.debug("################");

                if (projectSpaceExpandedMap.containsKey(activityName)) {
                    ActivityModel activityModel = projectSpaceExpandedMap.get(activityName);
                    String v6ParentId = activityModel.getParentId();
                    String taskName = activityModel.getActivityName();
                    if (/*activityModel.getParentName().equalsIgnoreCase(parentActivityName) && */v6ParentId.equalsIgnoreCase(parentIdFromLNJsonResponse)) {
                        HashMap<String, String> createOrUpdatePropertiesAndSetInterfaces = getCreateOrUpdatePropertiesAndSetInterfaces(context, activityModel.getActivityId(), Constants.UPDATE, task);
                        updateTask(context, taskNamesAndIds, createOrUpdatePropertiesAndSetInterfaces, projectSpaceUtil, activityModel.getActivityName(), parentActivityName, activityModel, task);

                    } else {
                        disConnectAndReConnecteTask(context, projectSpaceUtil, taskNamesAndIds, activityModel, parentIdFromLNJsonResponse, task);
                    }
                } else {
                    createTask(context, projectSpaceUtil, taskNamesAndIds, projectSpaceExpandedMap, parentIdFromLNJsonResponse, task, activityName, parentActivityName);
                }
            } catch (Exception exp) {
                TASK_IMPORT_PROCESS.error(exp);
                throw new RuntimeException(exp);
            }
        });

        HashMap<String, ActivityModel> projectSpaceAndTasksInV6 = new HashMap<>(projectSpaceExpandedMap);
        Set<String> tasksInV6 = projectSpaceAndTasksInV6.keySet();
        Set<String> tasksInLN = taskNamesAndIds.keySet();

        tasksInV6.removeAll(tasksInLN);
        tasksInV6.forEach((String removedTask) -> {

            boolean fullSynchronization = Boolean.parseBoolean(ApplicationProperties.getProprtyValue("full.synchronization.by.deleting.or.disconnecting.tasks.or.milestones"));

            if (fullSynchronization) {
                if (deleteUnSynchedTasksOrMileStones) {
                    projectSpaceUtil.deleteTaskAndMileStone(context, projectSpaceExpandedMap.get(removedTask));
                } else {
                    disconnectTask(context, projectSpaceExpandedMap.get(removedTask));
                }
            }

            projectSpaceExpandedMap.remove(removedTask);
        });
        taskNamesAndIds.clear();

//        if (deleteAllTasksAndMileStones) {
//            projectSpaceUtil.deleteTaskAndMileStone(context, projectSpaceExpandedMap);
//        }
        return projectSpaceExpandedMap;
    }

    private HashMap<String, String> getCreateOrUpdatePropertiesAndSetInterfaces(Context context, String objectIdForAddingInterface, String projectStructureConstant, TaskBean task) throws MatrixException {
        //HashMap<String, String> createOrUpdateProperties = new XMLMapUtilities().getCreateOrUpdateProperties(task.getActivityType(), Constants.UPDATE, new ReflectionUtilities().getHashMapFromAnnotaionAndValue(task));
        HashMap<String, String> createOrUpdateProperties = new XMLMapUtilities().getCreateOrUpdateProperties(task.getActivityType(), projectStructureConstant, new ReflectionUtilities().getHashMapFromAnnotaionAndValue(task));
        return addInterfaceToTheObject(createOrUpdateProperties, context, objectIdForAddingInterface);
    }

    private HashMap<String, String> getCreateOrUpdatePropertiesAndSetInterfaces(Context context, String objectIdForAddingInterface, String projectConstants, ActivitiesBean activitiesBean) throws MatrixException {
//        HashMap<String, String> createOrUpdateProperties = new XMLMapUtilities().getCreateOrUpdateProperties(Constants.PROJECT_SPACE, Constants.UPDATE, new ReflectionUtilities().getHashMapFromAnnotaionAndValue(activitiesBean));
        HashMap<String, String> createOrUpdateProperties = new XMLMapUtilities().getCreateOrUpdateProperties(Constants.PROJECT_SPACE, projectConstants, new ReflectionUtilities().getHashMapFromAnnotaionAndValue(activitiesBean));
        return addInterfaceToTheObject(createOrUpdateProperties, context, objectIdForAddingInterface);
    }

    private HashMap<String, String> addInterfaceToTheObject(HashMap<String, String> createOrUpdateProperties, Context context, String objectIdForAddingInterface) throws MatrixException {
        try {
            String runtimeInterfaceList = createOrUpdateProperties.get("runtimeInterfaceList");
            createOrUpdateProperties.remove("runtimeInterfaceList");
            new ObjectUtility().addInterface(context, objectIdForAddingInterface, runtimeInterfaceList, "");
            return createOrUpdateProperties;
        } catch (MatrixException ex) {
            TASK_IMPORT_PROCESS.error(ex);
            throw ex;
        }
    }

    private Boolean disconnectTask(Context context, ActivityModel v6ActivityModel) {
        try {
            String taskName = v6ActivityModel.getActivityName();
            String v6ParentId = v6ActivityModel.getParentId();

            TASK_IMPORT_PROCESS.info("Disconnecting '" + taskName + "' from '" + v6ActivityModel.getParentName() + "'");

            BusinessObject v6ParentBusinessObject = new BusinessObject(v6ParentId);
            v6ParentBusinessObject.open(context);

            TASK_IMPORT_PROCESS.debug("Task id is : " + v6ActivityModel.getActivityId());
            String relationshipId = v6ActivityModel.getRelationshipId();
            Relationship relationShip = new Relationship(relationshipId);
            v6ParentBusinessObject.disconnect(context, relationShip);
            return true;
        } catch (MatrixException ex) {
            TASK_IMPORT_PROCESS.info(ex);
            return false;
        }
    }

    private void createTask(Context context, ProjectSpaceUtil projectSpaceUtil, HashMap<String, String> taskNamesAndIds, HashMap<String, ActivityModel> projectSpaceExpandedMap, String parentIdFromLNJsonResponse, TaskBean task, String activityName, String parentActivityName) throws Exception {
        HashMap<String, String> taskOrMileStoneCreateProperties = new ReflectionUtilities().getHashMapFromAnnotaionAndValue(task);
        taskOrMileStoneCreateProperties.put("ActivityType", taskOrMileStoneCreateProperties.get("ActivityType").equalsIgnoreCase(Constants.MILE_STONE) ? Constants.MILE_STONE : Constants.TASK);
        taskOrMileStoneCreateProperties.put("objectId", parentIdFromLNJsonResponse);
        taskOrMileStoneCreateProperties.put("parentId", parentIdFromLNJsonResponse);
        HashMap<String, String> taskAttributeValueMap = new XMLMapUtilities().getCreateOrUpdateProperties(task.getActivityType(), Constants.CREATE, taskOrMileStoneCreateProperties);

        TASK_IMPORT_PROCESS.info("create and connect task with the parent");

        TaskProcessor taskProcessor = new TaskProcessor();
        String newTaskId = taskProcessor.createTask(context, taskAttributeValueMap, task, Boolean.FALSE);
        TASK_IMPORT_PROCESS.debug("New Task Id : " + newTaskId);

        if (NullOrEmptyChecker.isNullOrEmpty(newTaskId)) {
            TASK_IMPORT_PROCESS.warn("'" + task.getActivity() + "' is going to skip as their type is missing");
            return;
        }

        taskNamesAndIds.put(task.getActivity(), newTaskId);

        ActivityModel newChildactivityModel = new ActivityModel();
        newChildactivityModel.setActivityId(newTaskId);
        newChildactivityModel.setActivityName(activityName);
        newChildactivityModel.setActivityType("Task");
        newChildactivityModel.setParentName(parentActivityName);
        newChildactivityModel.setParentId(parentIdFromLNJsonResponse);
        newChildactivityModel.setRelationShipName("SubTask");

        HashMap<String, String> createOrUpdateProperties = getCreateOrUpdatePropertiesAndSetInterfaces(context, newTaskId, Constants.UPDATE, task);
        updateTask(context, taskNamesAndIds, createOrUpdateProperties, projectSpaceUtil, newChildactivityModel.getActivityName(), parentActivityName, newChildactivityModel, task);
    }

    private void disConnectAndReConnecteTask(Context context, ProjectSpaceUtil projectSpaceUtil, HashMap<String, String> taskNamesAndIds, ActivityModel activityModel, String parentIdFromLNJsonResponse, TaskBean task) throws MatrixException {
        String taskName = activityModel.getActivityName();
        String v6ParentId = activityModel.getParentId();
        String parentActivityName = task.getParentActivity();
        TASK_IMPORT_PROCESS.info("Disconnecting '" + taskName + "' from '" + activityModel.getParentName() + "'");

        BusinessObject v6ParentBusinessObject = new BusinessObject(v6ParentId);
        v6ParentBusinessObject.open(context);

        TASK_IMPORT_PROCESS.debug("Task id is : " + activityModel.getActivityId());
        String relationshipId = activityModel.getRelationshipId();
        Relationship relationShip = new Relationship(relationshipId);
        v6ParentBusinessObject.disconnect(context, relationShip);

        RelationshipType relationshipType = new RelationshipType();
        relationshipType.setName("Subtask");

        TASK_IMPORT_PROCESS.info("Connecting '" + taskName + "' as a child of '" + parentActivityName + "'");

        BusinessObject parentBusinessObject = new BusinessObject(parentIdFromLNJsonResponse);
        BusinessObject childBusinessObject = new BusinessObject(activityModel.getActivityId());
        parentBusinessObject.open(context);
        childBusinessObject.open(context);

        Relationship connect = parentBusinessObject.connect(context, relationshipType, true, childBusinessObject);
        String newRelationshipId = connect.getName();

        TASK_IMPORT_PROCESS.debug("'" + taskName + "' has connected as a child of '" + parentActivityName + "' by '" + newRelationshipId + "' connection id");

        activityModel.setParentName(parentActivityName);
        activityModel.setParentId(parentIdFromLNJsonResponse);
        activityModel.setRelationshipId(newRelationshipId);

        taskNamesAndIds.put(task.getActivity(), activityModel.getActivityId());

        HashMap<String, String> createOrUpdateProperties = getCreateOrUpdatePropertiesAndSetInterfaces(context, activityModel.getActivityId(), Constants.UPDATE, task);
        updateTask(context, taskNamesAndIds, createOrUpdateProperties, projectSpaceUtil, activityModel.getActivityName(), parentActivityName, activityModel, task);
    }

    private void updateTask(Context context, HashMap<String, String> taskNamesAndIds, HashMap<String, String> updateProperties, ProjectSpaceUtil projectSpaceUtil, String activityName, String parentActivityName, ActivityModel activityModel, TaskBean task) throws FrameworkException {
        TASK_IMPORT_PROCESS.debug("Activity '" + activityName + "' already connected to the parent '" + parentActivityName + "'");
        TASK_IMPORT_PROCESS.debug("Task Id : " + activityModel.getActivityId());

        if (NullOrEmptyChecker.isNullOrEmpty(task.getActivityType())) {
            TASK_IMPORT_PROCESS.warn("'" + task.getActivity() + "' is going to be skipped as it's type is empty");
            return;
        }

        taskNamesAndIds.put(task.getActivity(), activityModel.getActivityId());
        TASK_IMPORT_PROCESS.info("Update The Task");

        projectSpaceUtil.updateItem(context, activityModel.getActivityId(), updateProperties);
    }

    public <T> T getDataFromService(String serviceUrl, String query, Class<T> classReference) throws Exception {
        try {
            String dataFromService = ServiceRequester.callService(serviceUrl, query);

            String trimmedData = dataFromService.trim();
            JSONObject jsonObject = JSONObject.fromObject(trimmedData);
            String jsonStringData = jsonObject.toString();

            TASK_IMPORT_PROCESS.debug("Json Object : " + jsonStringData);

            JSON jsonizer = new JSON();
            T data = jsonizer.deserialize(jsonStringData, classReference);
            return data;
        } catch (Exception exp) {
            throw exp;
        }
    }

    public String readFileAsString(String fileName) throws Exception {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

    public <T> T getDataFromService(Class<T> classReference, String fileName) throws Exception {
        try {
            TASK_IMPORT_PROCESS.info("Reading data from file");
            String directory = "C:/Users/BJIT/Desktop/ProjectSpaceFiless/" + fileName;
            TASK_IMPORT_PROCESS.debug("File directory is : " + directory);

            String dataFromService = readFileAsString(directory);

            String trimmedData = dataFromService.trim();
            JSONObject jsonObject = JSONObject.fromObject(trimmedData);
            String jsonStringData = jsonObject.toString();

            TASK_IMPORT_PROCESS.debug("Json Object : " + jsonStringData);

            JSON jsonizer = new JSON();
            T data = jsonizer.deserialize(jsonStringData, classReference);
            return data;
        } catch (Exception exp) {
            throw exp;
        }
    }

    private <T> List<T> getNumberOfElements(List<T> listObject, Integer numberOfELements) {
        if (NullOrEmptyChecker.isNull(numberOfELements) || numberOfELements == 0) {
            return listObject;
        }

        List<T> newList = new ArrayList<>();

        for (int iterator = 0; iterator < numberOfELements; iterator++) {
            newList.add(listObject.get(iterator));
        }

        return newList;
    }

    public HashMap<String, String> getProjectSpaceProperties(ActivitiesBean activitiesBean) {
        HashMap<String, String> updateProperties = new XMLMapUtilities().getCreateOrUpdateProperties("Project Space", "update", new ReflectionUtilities().getHashMapFromAnnotaionAndValue(activitiesBean));

        return updateProperties;
    }
}
