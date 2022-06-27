/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.service;

import com.bjit.ex.integration.transfer.inputparam.TransferInputParams;
import java.util.List;

/**
 *
 * @author Sajjad
 */
public interface ITransfer {

    public Object transfer(TransferInputParams transferObj, boolean isService) throws Exception;

    public Object transfer(TransferInputParams transferObj, boolean isService, List<String> transferedItems) throws Exception;
}
