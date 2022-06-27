/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.test;

import com.bjit.compareBOM.Action.BOMCompareAction;

/**
 *
 * @author Sajjad
 */
public class BOMCompareTest {
    public static void main(String[] args) {
        String type = "CreateAssembly";
        String name = "MASS-EPS1-00007254";
        String rev = "1.1";
        
        BOMCompareAction action = new BOMCompareAction();
        action.fatchMultiLevelBOMfromLN(type, name, rev, "5");
    }
    
}
