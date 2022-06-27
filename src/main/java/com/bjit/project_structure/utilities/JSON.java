package com.bjit.project_structure.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Md. Omour Faruq Sumon
 */
public class JSON {

    private GsonBuilder gsonBuilder;
    private Gson gson;

    public JSON() {
        this(true);

    }

    public JSON(Boolean serializeNull) {

        gsonBuilder = new GsonBuilder();
        if (serializeNull) {
            gsonBuilder.serializeNulls();
        }
        gson = gsonBuilder.create();
    }

    /**
     *
     * @param object single object
     * @param <T> generic type
     * @return a json string
     */
    public <T> String serialize(T object) {
        try {
            return gson.toJson(object);

        } catch (Exception exp) {
            exp.printStackTrace(System.out);
            throw exp;
        }
    }

    /**
     *
     * @param object single object
     * @param <T> generic type
     * @param newProperties
     * @return a json string
     */
    /*public <T> String serialize(T object, HashMap<String, String> newProperties) {
        try {
            //return gson.toJson(object);
            JsonElement toJsonTree = gson.toJsonTree(object);
            newProperties.forEach((key, value) -> {
                toJsonTree.getAsJsonObject().addProperty(key, value);
            });
            return gson.toJson(toJsonTree);

        } catch (Exception exp) {
            exp.printStackTrace(System.out);
            throw exp;
        }
    }*/
    
    
    /**
     *
     * @param object single object
     * @param <T> generic type
     * @param newProperties
     * @return a json string
     */
    public <T> String serialize(T object, HashMap<String, Object> newProperties) {
        try {
            //return gson.toJson(object);
            JsonElement toJsonTree = gson.toJsonTree(object);
            newProperties.forEach((String key, Object value) -> {
                JsonElement objectElement = gson.toJsonTree(value);
                toJsonTree.getAsJsonObject().add(key, objectElement);
            });
            return gson.toJson(toJsonTree);

        } catch (Exception exp) {
            exp.printStackTrace(System.out);
            throw exp;
        }
    }

    
    public <T> String serializeObjectList(T object, String propertyName, List<Object> newProperties) {
        /*try {
            //return gson.toJson(object);
            JsonElement toJsonTree = gson.toJsonTree(object);
            newProperties.forEach((String key, Object value) -> {
                JsonElement objectElement = gson.toJsonTree(value);
                toJsonTree.getAsJsonObject().add(key, objectElement);
            });
            return gson.toJson(toJsonTree);

        } catch (Exception exp) {
            exp.printStackTrace(System.out);
            throw exp;
        }*/
        return null;
    }
    /**
     *
     * @param object list of objects
     * @param <T> generic list
     * @return json string
     */
    public <T> String serialize(List<T> object) {
        try {
            return gson.toJson(object);

        } catch (Exception exp) {
            exp.printStackTrace(System.out);
            throw exp;
        }
    }

    /**
     *
     * @param jsonSerializedString json object string
     * @param classType te be converted into the class
     * @param <T> generic type
     * @return converted object
     */
    public <T> T deserialize(String jsonSerializedString, Class<T> classType) {
        try {
            T object = gson.fromJson(jsonSerializedString, classType);
            return object;
        } catch (JsonSyntaxException exp) {
            exp.printStackTrace(System.out);
            throw exp;
        }
    }

    /**
     *
     * @param stringBusinessData a json string
     * @param key kew of the key value pair of the string
     * @return only the value of the key
     * @throws JsonSyntaxException
     */
    public String getValueFromJsonKey(String stringBusinessData, String key) throws JsonSyntaxException {
        try {
            Map jsonJavaRootObject = new Gson().fromJson(stringBusinessData, Map.class);
            return jsonJavaRootObject.get(key).toString();
        } catch (JsonSyntaxException exp) {
            exp.printStackTrace(System.out);
            throw exp;
        }
    }

    public Boolean FreeUpMemories() {
        try {
            gsonBuilder = null;
            gson = null;
            return true;
        } catch (Exception exp) {
            exp.printStackTrace(System.out);
            throw exp;
        }
    }

    public String addAProperty(Object object, String propertyName, String propertyValue) {
        JsonElement toJsonTree = gson.toJsonTree(object);
        toJsonTree.getAsJsonObject().addProperty(propertyName, propertyValue);
        return gson.toJson(toJsonTree);
    }
}
