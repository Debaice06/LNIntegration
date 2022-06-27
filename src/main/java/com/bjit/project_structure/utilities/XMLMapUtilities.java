/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.project_structure.utilities;

import com.bjit.project_structure.mapping.builder.MapperBuilder;
import com.bjit.project_structure.mapping.mapping_model.Mapping;
import com.bjit.project_structure.mapping.mapping_model.RangeValue;
import com.bjit.project_structure.mapping.mapping_model.RangeValues;
import com.bjit.project_structure.mapping.mapping_model.MapAttribute;
import com.bjit.project_structure.mapping.mapping_model.MapObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

/**
 *
 * @author BJIT
 */
public class XMLMapUtilities {

    private static Mapping mapper;
    public static HashMap<String, String> typeMap;

    private void initializeMapper() {
        try {
            //mapper = Optional.ofNullable(mapper).orElse(new MapperBuilder().getMapper(MapperBuilder.XML));
            if(NullOrEmptyChecker.isNull(mapper)){
                mapper = new MapperBuilder().getMapper(MapperBuilder.XML);
            }
            
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public HashMap<String, String> getTypeMap() {
        if (NullOrEmptyChecker.isNullOrEmpty(typeMap)) {
            typeMap = new HashMap<>();
            typeMap.put("Milestone", "Milestone");
            typeMap.put("Project Space", "Project Space");
        }
        return typeMap;
    }

//    public HashMap getTaskAndMileStoneCreatePropertiesFromXMLMapper(HashMap<String, String> attributeValueMap, String type) {
//        initializeMapper();
//        HashMap taskAttributes = new HashMap();
//
//        mapper.getXmlMapElementObjects().getXmlMapElementObject().forEach((MapObject elementObject) -> {
//            if (elementObject.getType().equalsIgnoreCase(type)) {
//                elementObject.getXmlMapElementAttributes().getXmlMapElementAttribute().forEach((MapAttribute elementAttribute) -> {
//                    String sourceName = elementAttribute.getSourceName();
//                    String destinationName = elementAttribute.getDestinationName();
//
//                    if (attributeValueMap.containsKey(sourceName)) {
//                        taskAttributes.put(destinationName, attributeValueMap.get(sourceName));
//                    }
//                });
//            }
//        });
//
//        return taskAttributes;
//    }
    public HashMap<String, String> getCreateOrUpdateProperties(String typeOfObject, String objectOperation, HashMap<String, String> taskAttributeValueMap) {
        try {

            final String objectType = Optional.ofNullable(getTypeMap().get(typeOfObject)).orElse("Task");

            initializeMapper();

            HashMap<String, String> updateProperties = new HashMap();

            mapper.getXmlMapElementObjects().getXmlMapElementObject().forEach((MapObject elementObject) -> {
                String type = elementObject.getType();
                String operation = elementObject.getOperation();
                String runtimeInterfaceList = elementObject.getRuntimeInterfaceList();

                if (type.equalsIgnoreCase(objectType) && Optional.ofNullable(operation).orElse("").equalsIgnoreCase(objectOperation)) {
                    elementObject.getXmlMapElementAttributes().getXmlMapElementAttribute().forEach((MapAttribute elementAttribute) -> {
                        String sourceName = elementAttribute.getSourceName();
                        String destinationName = elementAttribute.getDestinationName();
                        String description = elementAttribute.getDescription();
                        String fixedValue = Optional.ofNullable(elementAttribute.getFixedValue()).orElse("");
                        String dataType = Optional.ofNullable(elementAttribute.getDataType()).orElse("");
                        final Integer dataLength = elementAttribute.getDataLength();

                        if (dataType.equals(Constants.DATE)) {
                            String dateFormate = Optional.ofNullable(elementAttribute.getDateFormat()).orElse("");
                            String mappedDate = Optional.ofNullable(elementAttribute.getDate()).orElse("");

                            if (Constants.CURRENT_DATE.equals(mappedDate)) {
                                Date curDate = new Date();

                                SimpleDateFormat format = new SimpleDateFormat(dateFormate);
                                String formatedDate = format.format(curDate);

                                updateProperties.put(destinationName, formatedDate);
                                return;
                            }
                        }

                        if (Optional.ofNullable(taskAttributeValueMap).orElse(new HashMap<>()).containsKey(sourceName)) {
                            if (!NullOrEmptyChecker.isNullOrEmpty(fixedValue)) {
                                updateProperties.put(destinationName, fixedValue.equalsIgnoreCase("null") ? null : fixedValue);
                                return;
                            }

                            String sourceValue = taskAttributeValueMap.get(sourceName);
                            String destinationValue;

                            destinationValue = Optional.ofNullable(Optional.ofNullable(elementAttribute.getRangeValues())
                                    .orElse(new RangeValues()).getValue())
                                    .orElse(new ArrayList<>())
                                    .stream()
                                    .filter(rangeValue -> rangeValue.getSrc().equalsIgnoreCase(sourceValue))
                                    .findFirst()
                                    .orElse(new RangeValue())
                                    .getValue();

                            if (!NullOrEmptyChecker.isNullOrEmpty(destinationValue)) {

                                if (!NullOrEmptyChecker.isNullOrEmpty(dataLength)) {
                                    destinationValue = abbreviate(destinationValue, dataLength);
                                }

                                updateProperties.put(destinationName, destinationValue);
                            } else {
                                String updatedSourceValue = sourceValue;
                                if (!NullOrEmptyChecker.isNullOrEmpty(dataLength)) {
                                    updatedSourceValue = abbreviate(sourceValue, dataLength);
                                }
                                updateProperties.put(destinationName, updatedSourceValue);
                            }

                        } else {
                            updateProperties.put(destinationName, Optional.ofNullable(elementAttribute.getFixedValue()).orElse(""));
                        }
                    });
                    updateProperties.put("runtimeInterfaceList", runtimeInterfaceList);
                }

            });

            return updateProperties;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public String abbreviate(String str, int maxWidth) {
        if (str.length() <= maxWidth) {
            return str;
        }

        return maxWidth > 20 ? str.substring(0, (maxWidth - 3)) + "..." : str.substring(0, maxWidth);
    }
}
