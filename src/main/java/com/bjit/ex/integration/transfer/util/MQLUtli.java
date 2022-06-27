/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.util;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import matrix.db.Context;
import matrix.db.MQLCommand;
import matrix.util.MatrixException;

/**
 *
 * @author Sajjad
 */
public class MQLUtli {
    public List getUserRole(Context context,String personName){
        List<String> roleList = new ArrayList<>();
        try {            
            String command = "print \"Person\" \""+personName+"\" select assignment;";
            MQLCommand mqlCommand = new MQLCommand();
            mqlCommand.executeCommand(context, command);
            String result = mqlCommand.getResult();
            System.out.println("result :: "+result.split("assignment = ").length);
            for(int i=1;i<result.split("assignment = ").length;i++){
                roleList.add(result.split("assignment = ")[i].trim());
            }
            mqlCommand.close(context);
            
        } catch (MatrixException ex) {
            Logger.getLogger(MQLUtli.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("roleList "+roleList);
        return roleList;
    }
    
}
