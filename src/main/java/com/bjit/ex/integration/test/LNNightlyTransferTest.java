/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.test;

import com.bjit.ex.integration.transfer.actions.GTSNightlyUpdateTransferAction;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sajjad
 */
public class LNNightlyTransferTest {

    public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, IOException {
        List<String> list = new ArrayList<>();
        //list.add("65092");//V6
        //list.add("23352");//PDM VAL
        //list.add("54272");//PDM ODI
        list.add("54274"); //QS4
        GTSNightlyUpdateTransferAction action = new GTSNightlyUpdateTransferAction(list);

//       GTSNightlyUpdateTransferAction action = new GTSNightlyUpdateTransferAction();
    }
}
