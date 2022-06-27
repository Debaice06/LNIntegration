/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.project_structure.processors;

import com.bjit.project_structure.utilities.ProjectSpaceUtil;
import com.bjit.project_structure.model.TaskBean;
import com.bjit.project_structure.utilities.NullOrEmptyChecker;
import com.bjit.project_structure.utilities.XMLMapUtilities;
import java.util.HashMap;
import java.util.Map;
import matrix.db.Context;
import matrix.db.JPO;
import matrix.util.MatrixException;

/**
 *
 * @author BJIT
 */
public class TaskProcessor {

    private static final String JPO_NAME = "emxTaskBase";
    private static final String JPO_METHOD_NAME = "createNewTask";
    private static final org.apache.log4j.Logger TASK_LOGGER = org.apache.log4j.Logger.getLogger(TaskProcessor.class);

    public String createTask(Context context, HashMap<String, String> attributeValueMap, TaskBean task, Boolean autoName) throws Exception {

        TASK_LOGGER.info("Task Creation Process has been started");

        if (NullOrEmptyChecker.isNullOrEmpty(task.getActivityType())) {
            TASK_LOGGER.warn("'" + task.getActivity() + "' is going to skip as their type is missing");
            return null;
        }

        if (NullOrEmptyChecker.isNullOrEmpty(task.getActivity())) {
            TASK_LOGGER.warn("'" + task.getActivity() + "' is going to skip as their name is missing");
            return null;
        }

        String newTaskId = "";
        try {

            TASK_LOGGER.info("JPO Name : " + JPO_NAME);
            TASK_LOGGER.info("JPO Method Name : " + JPO_METHOD_NAME);
            TASK_LOGGER.info("JPO Properties : " + attributeValueMap);

            String[] initArgs = {};
            Map resultMap = (Map) JPO.invoke(context, JPO_NAME, initArgs, JPO_METHOD_NAME, JPO.packArgs(attributeValueMap), Map.class);
            newTaskId = (String) resultMap.get("id");

            TASK_LOGGER.debug("New Task Id : " + newTaskId);
        } catch (MatrixException exp) {
            TASK_LOGGER.error(exp);
            throw exp;
        }

        return newTaskId;
    }

//    public static String createMilestone(Context context, HashMap<String, String> attributeValueMap, TaskBean task, Boolean autoName) throws Exception {
//        TASK_LOGGER.info("Task Creation Process has been started");
//        String newTaskId;
//        try {
//
////            HashMap milestoneAttributes = new XMLMapUtilities().getTaskAndMileStoneCreatePropertiesFromXMLMapper(attributeValueMap, "Milestone");
//
//            TASK_LOGGER.info("JPO Name : " + JPO_NAME);
//            TASK_LOGGER.info("JPO Method Name : " + JPO_METHOD_NAME);
//
//            String[] initArgs = {};
//            Map resultMap = (Map) JPO.invoke(context, JPO_NAME, initArgs, JPO_METHOD_NAME, JPO.packArgs(attributeValueMap), Map.class);
//            newTaskId = (String) resultMap.get("id");
//
//            ProjectSpaceUtil expandProjectSpace = new ProjectSpaceUtil();
//            //expandProjectSpace.updateObject(context, newTaskId, "type", "Milestone");
//
//            TASK_LOGGER.debug("New Task Id : " + newTaskId);
//        } catch (MatrixException exp) {
//            TASK_LOGGER.error(exp);
//            throw exp;
//        }
//        return newTaskId;
//    }
}
