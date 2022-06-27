///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.bjit.project_structure.utilities;
//
//import com.bjit.project_structure.model.TaskBean;
//import java.lang.reflect.Field;
//import java.util.HashMap;
//
///**
// *
// * @author BJIT
// */
//public class TaskMilestoneUtil {
//
//    private static final org.apache.log4j.Logger TASK_MILESTONE_UTIL_LOGGER = org.apache.log4j.Logger.getLogger(TaskMilestoneUtil.class);
//    private static final String ADD_TASK = "addTaskBelow";
//    private static final String POLICY = "Project Task";
//    private static final String TASK_REQUIRMENT = "Optional";
//    private static final String PROJECT_ROLE = "";
//    private static final String TASK_CONSTRAINT_DATE = "";
//    private static final String TASK_CONSTRAINT_TYPE = "As Soon As Possible";
//    private static final String DURATION_KEYWORDS = "";
//    private static final String DURATION = "1";
//    private static final String UNITS_DURATION = "d";
//    private static final String OWNER_OID = "";
//    private static final String ASSIGNEEOID = "";
//    private static final String CALENDAR = "";
//
//    public static HashMap<String, String> getTaskAttributeValueMap(TaskBean task, String parentId, Boolean autoName) throws IllegalArgumentException, IllegalAccessException {
//        TASK_MILESTONE_UTIL_LOGGER.info("Preparing task attrbite value map");
//        HashMap<String, String> attributeValueMap = new HashMap<>();
//        attributeValueMap.put("objectId", parentId);
//        attributeValueMap.put("parentId", parentId);
//        attributeValueMap.put("autoNameCheck", autoName.toString());
//        attributeValueMap.put("addTask", ADD_TASK);
//        attributeValueMap.put("Policy", POLICY);
//        attributeValueMap.put("TaskRequirement", TASK_REQUIRMENT);
//        attributeValueMap.put("ProjectRole", PROJECT_ROLE);
//        attributeValueMap.put("TaskConstraintDate", TASK_CONSTRAINT_DATE);
//        attributeValueMap.put("Task Constraint Type", TASK_CONSTRAINT_TYPE);
//        attributeValueMap.put("DurationKeywords", DURATION_KEYWORDS);
//        attributeValueMap.put("Duration", DURATION);
//        attributeValueMap.put("units_Duration", UNITS_DURATION);
//        attributeValueMap.put("OwnerOID", OWNER_OID);
//        attributeValueMap.put("AssigneeOID", ASSIGNEEOID);
//        attributeValueMap.put("Calendar", CALENDAR);
//
//        Field[] fields = task.getClass().getDeclaredFields();
//        for (Field field : fields) {
//            field.setAccessible(true);
//            String fieldName = field.getName();
//            String fieldValue = NullOrEmptyChecker.isNull(field.get(task)) ? "" : field.get(task).toString();
//            if (fieldName.equalsIgnoreCase("activity")) {
//                attributeValueMap.put(fieldName, fieldValue);
//            } else if (fieldName.equalsIgnoreCase("activityType")) {
//                attributeValueMap.put(fieldName, fieldValue.equalsIgnoreCase("Milestone") ? "Milestone" : "Task");
//            } else if (fieldName.equalsIgnoreCase("activityDescription")) {
//                attributeValueMap.put(fieldName, NullOrEmptyChecker.isNullOrEmpty(fieldValue) ? "" : fieldValue);
//            }
//        }
//        return attributeValueMap;
//    }
//}
