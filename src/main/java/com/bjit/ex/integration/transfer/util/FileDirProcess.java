/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.List;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.bjit.ex.integration.mapproject.xml_mapping_model.XmlMapElementObject;

/**
 *
 * @author Sajjad
 */
public class FileDirProcess {
    static final org.apache.log4j.Logger FILE_DIRECTORY_PROCESS = org.apache.log4j.Logger.getLogger(FileDirProcess.class.getName());
    public static String itemNextStatus = "";
    public static  String itemCurrentStatus = "";
    public ItemInfo parseFile(File xmlFile) throws IOException, JDOMException {
    	ItemInfo itemInfo = new ItemInfo();
        SAXBuilder builder = new SAXBuilder();
        String itemId = "";
        String itemName = "";
        String itemRev = "";
        try {
            long fileParseStartTime = System.currentTimeMillis();
            org.jdom.Document document = (org.jdom.Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            List<Element> elementList = rootNode.getChildren();
            
            for (Element element : elementList) {
            	List childrenList = element.getChildren();
            	for (Object children : childrenList) {
            		XmlMapElementObject objMap = (XmlMapElementObject)children;
					System.out.println(objMap.getType());
				}
			}
            itemId = rootNode.getChild("id").getValue();
            itemName = rootNode.getChild("name").getValue();
            itemRev = rootNode.getChild("revision").getValue();
            itemNextStatus = rootNode.getChild("nextState").getValue();
            itemCurrentStatus = rootNode.getChild("currentState").getValue();

            itemInfo.setItemId(itemId);
            itemInfo.setItemName(itemName);
            itemInfo.setItemRev(itemRev);
            itemInfo.setCurrentState(itemCurrentStatus);
            itemInfo.setNextState(itemNextStatus);

            long fileParseEndTime = System.currentTimeMillis();
            long fileParseElapsedTime = fileParseEndTime - fileParseStartTime;
            FILE_DIRECTORY_PROCESS.info("\nFile parse elapsed time : "+fileParseElapsedTime);
            FILE_DIRECTORY_PROCESS.info("ItemId = "+itemId+" Next Status = "+itemNextStatus+"  Current status = "+ itemCurrentStatus);
        } catch (IOException io) {
            FILE_DIRECTORY_PROCESS.info(io.getMessage());
            throw io;
        } catch (JDOMException jdomex) {
            FILE_DIRECTORY_PROCESS.info(jdomex.getMessage());
            throw jdomex;
        }
        return itemInfo;
    }

    public static FileFilter getFileFilter(File sourceDirPath,
            final String fileExtension) throws Exception {
        if (fileExtension == null || fileExtension.isEmpty()) {
            throw new Exception("File extension is required! ");
        }
        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isFile()
                        && file.getName().toLowerCase()
                                .endsWith(fileExtension.toLowerCase());
            }
        };
        return filter;
    }

    public static void moveFile(File sourceFile, String destDir)
            throws Exception {
        createDirectory(destDir);
        File moveFile = new File(sourceFile.getAbsolutePath());
        boolean isMoved = moveFile.renameTo(new File(destDir
                + moveFile.getName()));
        if (isMoved) {
            moveFile.delete();
        } else {
            long sTime = System.currentTimeMillis();
            moveFile.renameTo(new File(destDir + moveFile.getName() + sTime
                    + ".bac"));
        }
    }

    public static void createDirectory(String destDir) throws Exception {
        File destFolder = new File(destDir);
        if (!destFolder.exists()) {
            boolean isDirectoryCreated = destFolder.mkdirs();
            if (isDirectoryCreated) {
                FILE_DIRECTORY_PROCESS.info(destFolder.getAbsolutePath()+" Directory created." );
            } else {
                throw new Exception(destFolder.getAbsolutePath()
                        + " Directory creation failed!");
            }
        }
    }

}
