/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.resultsender.impls;

import com.bjit.ex.integration.transfer.service.impl.LN.ResponseResult;
import java.util.List;

/**
 *
 * @author BJIT
 */
public interface TransferResponseService {
    
    public List<ResponseResult> lNResponse(String itemName); 
    
    
}
