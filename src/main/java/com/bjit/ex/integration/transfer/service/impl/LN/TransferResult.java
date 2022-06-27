/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.service.impl.LN;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Sajjad
 */
public class TransferResult {
    private ArrayList<ResponseResult> results = new ArrayList<ResponseResult>();

    public ArrayList getResults() {
        return results;
    }

    public void setResults(ArrayList results) {
        this.results = results;
    }
    
    public ArrayList getSuccessfulResults(){    
        ArrayList<ResponseResult> successfulResultList = new ArrayList<>();
        Iterator itr = results.iterator();
        while(itr.hasNext()){
            ResponseResult result = (ResponseResult)itr.next();
            if (result.isSuccessful()){
                successfulResultList.add(result);
            }
        }
        return successfulResultList;
    }
    
    public ArrayList getFailedResults(){
        ArrayList<ResponseResult> failedResultList = new ArrayList<>();
        Iterator itr = results.iterator();
        while(itr.hasNext()){
            ResponseResult result = (ResponseResult)itr.next();
            if (!result.isSuccessful()){
                failedResultList.add(result);
            }
        }
        return failedResultList;
    }

    void addNewResult(ResponseResult parseErrorMessage) {
        results.add(parseErrorMessage);
    }
    
    
}
