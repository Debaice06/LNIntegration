package com.bjit.dw.enovia.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DWDataFetchActionTest {
    public static void main(String[] args) throws RuntimeException, SQLException {
        DWDataFetchAction dataFetchAction = new DWDataFetchAction();
        List<String> itemList = new ArrayList<>();
        itemList.add("RAU0101861M");
        itemList.add("RAU0101872M");
        itemList.add("RAU0101873M");
        itemList.add("RAU0101874M");
        itemList.add("RAU0101918SU");
        itemList.add("KSD7547728");
        String lastExecutionDateTime = "2021-10-17 00:00:00";
        List<Map<String, String>> maps = dataFetchAction.fetchDWData(itemList, lastExecutionDateTime);
        System.out.println(":: Response ::");
        System.out.println(maps);
    }
}
