/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.resultsender.impls;

import com.bjit.ex.integration.transfer.actions.utilities.SelectionCodeType;
import com.bjit.ex.integration.transfer.service.impl.LN.ResponseResult;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BJIT
 */
public class TransferResponseServiceImpl implements TransferResponseService {

    public List<ResponseResult> lNResponse(String itemName) {

        List<ResponseResult> responseResults = new ArrayList<>();
        
        
        ResponseResult responseResult = new ResponseResult();
        responseResult.setItem(itemName.toUpperCase());
        responseResult.setSelectionCodeType(SelectionCodeType.ITM);
        responseResult.setResultText("RESULT OK");
        responseResults.add(responseResult);

        return responseResults;
    }

}
