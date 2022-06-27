/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.mapproject.utility;

import com.bjit.ex.integration.mapproject.builder.MapperBuilder;
import com.bjit.ex.integration.mapproject.xml_mapping_model.Mapping;
import com.bjit.ex.integration.mapproject.xml_mapping_model.XmlMapElementBOMRelationship;
import com.bjit.ex.integration.mapproject.xml_mapping_model.XmlMapElementObject;
import java.util.ArrayList;

/**
 *
 * @author Sajjad
 */
public class MapperUtil {
    
    /**
     * 
     * @return
     * @throws Exception 
     */
     public ArrayList<String> getTypeNameList() throws Exception {
        ArrayList<String> typeList = new ArrayList<>();
        Mapping mapper = new MapperBuilder().getMapper(MapperBuilder.XML);
        mapper.getXmlMapElementObjects().getXmlMapElementObject().forEach((xmlMapElementObject) -> {
            typeList.add(xmlMapElementObject.getType());
         });
        return typeList;
    }
    
    
    /**
     * 
     * @return
     * @throws Exception 
     */
    public ArrayList<String> getRelNameList() throws Exception {
        ArrayList<String> relList = new ArrayList<>();
        Mapping mapper = new MapperBuilder().getMapper(MapperBuilder.XML);
        mapper.getXmlMapElementBOMRelationships().getXmlMapElementBOMRelationship().forEach((xmlMapElementBOMRelationship) -> {
            relList.add(xmlMapElementBOMRelationship.getName());
        });
        return relList;
    }
}
