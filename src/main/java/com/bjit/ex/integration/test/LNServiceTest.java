package com.bjit.ex.integration.test;

import com.bjit.context.ContextGeneration;
import com.bjit.ex.integration.model.webservice.Item;
import com.bjit.ex.integration.model.webservice.TNR;
import com.bjit.ex.integration.transfer.actions.LNTransferAction;
import com.bjit.ex.integration.transfer.actions.factory.TransferActionType;
import com.bjit.ex.integration.transfer.service.impl.LN.ResponseResult;
import matrix.db.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LNServiceTest {

    public static void main(String[] args) {
        try {
            Context context = ContextGeneration.createContext();
            Map<String, String> itemAttrMap = new HashMap<>();
            Item item = new Item();
//            item.setId("11056.7015.37552.19169"); // tested for service (QS4)
//            item.setId("49248.18720.19567.25597");
//            item.setId("49248.18720.19567.23444");
            TNR tnr = new TNR();
            tnr.setType("CreateAssembly");
            tnr.setName("RAUZ0108042021OMF");
            item.setTnr(tnr);
            itemAttrMap.put("state-from", "IN_WORK");
            itemAttrMap.put("state-to", "RELEASED");
            item.setAttributes(itemAttrMap);

            LNTransferAction lnTransferAction = new LNTransferAction();
            Map<String, String> itemMessageMap = lnTransferAction.initiateTransferToLN(context, item, TransferActionType.ITEM_BOM, false);

            System.out.println("Printing error messages ------>");
            itemMessageMap.forEach((key, val) -> System.out.println("Key : " + key + "   Val : " + val));

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }
    }
}
