package com.bjit.dw.enovia.action.processors;

import com.bjit.dw.enovia.mapping.builder.MapperBuilder;
import com.bjit.dw.enovia.mapping.mapping_model.Mapping;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.bjit.ex.integration.transfer.util.NullOrEmptyChecker;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class MapperProcessor {
    private static final Logger MAPPER_PROCESSOR_LOGGER = Logger.getLogger(MapperProcessor.class);
    private Mapping mapper;
    private Map<String, Map<String, String>> attributeMap;

    public MapperProcessor() {
        loadMapperObject();
    }

    private void loadMapperObject() {
        try {
            mapper = new MapperBuilder<>().getMapper(MapperBuilder.XML);
        } catch (Exception e) {
            MAPPER_PROCESSOR_LOGGER.error(e.getMessage());
        }
    }

    public String getEnoviaUpdateAllowedTypes() {
        String allowedTypes = "";
        if(!NullOrEmptyChecker.isNull(mapper)) {
            String restrictedItemsStr = ApplicationProperties.getProprtyValue("LN.nightly.translation.update.restricted.item.type");
            List<String> restrictedItemList = Arrays.asList(restrictedItemsStr.split(","));

            try {
                List<String> allowedItemTypes = mapper.getXmlMapObjects().getXmlMapObject().stream()
                        .filter(xmlMapElementObject -> !restrictedItemList.contains(xmlMapElementObject.getType()))
                        .map(xmlMapElementObject -> xmlMapElementObject.getType())
                        .collect(Collectors.toList());

                allowedTypes = String.join(",", allowedItemTypes);
            } catch (Exception e) {
                MAPPER_PROCESSOR_LOGGER.error(e.getMessage());
            }
        }
        return allowedTypes;
    }

    public Set<String> getAttributeSelectables() {
        Set<String> attributeSelectables = new HashSet<>();
        if(!NullOrEmptyChecker.isNull(mapper)) {
            mapper.getXmlMapObjects().getXmlMapObject().forEach(object -> {
                object.getXmlMapAttributes().getXmlMapAttribute().forEach(attribute -> {
                    attributeSelectables.add(attribute.getSelectable());
                });
            });
        }
        return attributeSelectables;
    }

    public Mapping getMapper() {
        return this.mapper;
    }

    public Map<String, Map<String, String>> getMappingAttributeMap() {
        if(NullOrEmptyChecker.isNullOrEmpty(this.attributeMap)) {
            this.attributeMap = new HashMap<>();
            mapper.getXmlMapObjects().getXmlMapObject().forEach(object -> {
                Map<String, String> attrs = new HashMap<>();
                object.getXmlMapAttributes().getXmlMapAttribute().forEach(attr -> {
                    attrs.put(attr.getSourceName(), attr.getDestinationName());
                });
                attributeMap.put(object.getType(), attrs);
            });
        }
        return this.attributeMap;
    }
}
