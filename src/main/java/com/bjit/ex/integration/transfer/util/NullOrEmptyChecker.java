package com.bjit.ex.integration.transfer.util;

import java.util.List;
import java.util.Map;

public class NullOrEmptyChecker {

    public static synchronized Boolean isNullOrEmpty(String checkString) {
        return (checkString == null) || (checkString.equalsIgnoreCase(""));
    }

    public static synchronized Boolean isNullOrEmpty(Integer checkNumber) {
        return isNull(checkNumber) || checkNumber < 1;
    }

    public static synchronized Boolean isNullOrEmpty(Map checkMap) {
        return checkMap == null || checkMap.isEmpty();
    }

    public static synchronized Boolean isNullOrEmpty(List checkList) {
        return checkList == null || checkList.isEmpty();
    }

    public static synchronized Boolean isNullOrEmpty(Object[] checkArray) {
        return checkArray == null || checkArray.length == 0;
    }

    public static synchronized Boolean isNull(Object checkObject) {
        return checkObject == null;
    }

    public static synchronized String capitalizeFirstLetter(String original) {
        if (!isNullOrEmpty(original)) {
            return original.substring(0, 1).toUpperCase() + original.substring(1);
        }
        return original;
    }

    public static synchronized <T> void clear(T object) {
        if (!isNull(object)) {
            object = null;
        }
    }

    public static synchronized <T> void clear(List object) {
        if (!isNullOrEmpty(object)) {
            object.clear();
            object = null;
        }
    }

    public static synchronized <T> void clear(Map object) {
        if (!isNullOrEmpty(object)) {
            object.clear();
            object = null;
        }
    }

    public static synchronized <T> void clear(Object[] object) {
        if (!isNullOrEmpty(object)) {
            object = null;
        }
    }
}
