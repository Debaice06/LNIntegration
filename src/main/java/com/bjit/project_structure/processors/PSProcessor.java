/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.project_structure.processors;

import com.bjit.project_structure.model.ActivitiesBean;
import com.bjit.project_structure.utilities.NullOrEmptyChecker;
import com.bjit.project_structure.utilities.Constants;
import com.bjit.project_structure.utilities.ReflectionUtilities;
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
public class PSProcessor {

    private static final org.apache.log4j.Logger PROJECT_SPACE_LOGGER = org.apache.log4j.Logger.getLogger(PSProcessor.class);

    public static synchronized void checkContext(Context context) {
        if (NullOrEmptyChecker.isNull(context)) {
            String errorMessage = "Context is Null";
            PROJECT_SPACE_LOGGER.error(errorMessage);
            throw new NullPointerException(errorMessage);
        }
    }

    public static synchronized String searchByTypeName(Context context, String type, String name) throws MatrixException {

        try {
            String errorMessage;
            PROJECT_SPACE_LOGGER.info("Object type : '" + type + "' Object name : '" + name + "'");
            PSProcessor.checkContext(context);
            if (NullOrEmptyChecker.isNullOrEmpty(type)) {
                errorMessage = "'Type' is Null or Empty";
                PROJECT_SPACE_LOGGER.error(errorMessage);
                throw new NullPointerException(errorMessage);
            }

            if (NullOrEmptyChecker.isNullOrEmpty(name)) {
                errorMessage = "'Name' of '" + type + "' is Null or Empty";
                PROJECT_SPACE_LOGGER.error(errorMessage);
                throw new NullPointerException(errorMessage);

            }

            String[] constructor = {null};
            HashMap params = new HashMap();
            params.put("name", name);
            params.put("type", type);

            String jpoClassName = "CloneObjectUtil";
            String jpoMethodName = "searchByTypeName";

            PROJECT_SPACE_LOGGER.info("JPO class name is : '" + jpoClassName + "' method name is : '" + jpoMethodName + "'");

            String objectId = (String) JPO.invoke(context, jpoClassName, constructor, jpoMethodName, JPO.packArgs(params), String.class);
            PROJECT_SPACE_LOGGER.debug("Object Id : " + objectId);
            return objectId;
        } catch (MatrixException exp) {
            PROJECT_SPACE_LOGGER.error(exp.getMessage());
            throw exp;
        } catch (NullPointerException exp) {
            PROJECT_SPACE_LOGGER.error(exp.getMessage());
            throw exp;
        } catch (Exception exp) {
            PROJECT_SPACE_LOGGER.error(exp.getMessage());
            throw exp;
        }
    }

    public static synchronized String createProjectSpace(Context context, ActivitiesBean activitiesBean) throws MatrixException {

        String objectId = searchByTypeName(context, "Project Space", activitiesBean.getProjectCode());
        if (!NullOrEmptyChecker.isNullOrEmpty(objectId)) {

            return objectId;
        }
        String initargs[] = {};
        HashMap<String, String> attributesMap = new XMLMapUtilities().getCreateOrUpdateProperties(Constants.PROJECT_SPACE, Constants.CREATE, new ReflectionUtilities().getHashMapFromAnnotaionAndValue(activitiesBean));

        String newProjectId = null;
        try {
            Map result = (Map) JPO.invoke(context, "emxProjectSpaceBase", initargs, "createNewProject", JPO.packArgs(attributesMap), Map.class);
            newProjectId = (String) result.get("id");
            PROJECT_SPACE_LOGGER.debug("New project id : " + newProjectId);
        } catch (MatrixException exp) {
            PROJECT_SPACE_LOGGER.error(exp);
            throw exp;
        }
        return newProjectId;
    }
}
