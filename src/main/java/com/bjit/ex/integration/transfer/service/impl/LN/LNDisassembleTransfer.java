/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.service.impl.LN;

import com.bjit.ex.integration.transfer.inputparam.TransferInputParams;
import com.bjit.ex.integration.transfer.service.ITransfer;
import static com.bjit.ex.integration.transfer.service.impl.LN.LNResponseUtil.msgToBeIgnored;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.bjit.ex.integration.transfer.ws.soap.LNHandlerResolver;
import com.bjit.ex.integration.transfer.ws.soap.LNSoapHandler;
import com.infor.businessinterface.disassemblylines_val.DisAssemblyLinesVALService;
import com.infor.businessinterface.disassemblylines_val.ProcessDisAssemblyLinesRequestType;
import com.infor.businessinterface.disassemblylines_val.ProcessDisAssemblyLinesRequestType.DataArea;
import com.infor.businessinterface.disassemblylines_val.ProcessDisAssemblyLinesRequestType.ControlArea;
import com.infor.businessinterface.disassemblylines_val.ProcessDisAssemblyLinesRequestType.DataArea.DisAssemblyLinesVAL;
import com.infor.businessinterface.disassemblylines_val.ProcessDisAssemblyLinesResponseType;
import com.infor.businessinterface.disassemblylines_val.Result;
import com.infor.businessinterface.item_val.ActivationType;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.BindingProvider;
import matrix.db.BusinessObject;
import matrix.db.Context;

/**
 *
 * @author Sajjad
 */
public class LNDisassembleTransfer implements ITransfer{
    private static final org.apache.log4j.Logger LN_DISASSEMBLE_STRUCTURE_TRANSFER_LOGGER = org.apache.log4j.Logger.getLogger(LNDeliverableTransfer.class);
    @Override
    public Object transfer(TransferInputParams transferObj,boolean isService) throws Exception {
        LN_DISASSEMBLE_STRUCTURE_TRANSFER_LOGGER.info("::::: Disassemble Structure Transfer ::::: ");
        String transferType = transferObj.getTransferType();
        LN_DISASSEMBLE_STRUCTURE_TRANSFER_LOGGER.info("Transfer Type :: "+transferType);
        DisAssemblyLinesVAL disAssemblyLinesVALForRequest =  (DisAssemblyLinesVAL) transferObj.getTrasnferObjMap().get("disassembleLineRequestVAL");
        BusinessObject generalSystemBusinessObject = (BusinessObject) transferObj.getTrasnferObjMap().get("generalSystemBusinessObject");
        Context context = transferObj.getContext();
        TransferResult transferResult = sentDisassembleToLn(context, generalSystemBusinessObject, disAssemblyLinesVALForRequest);
        LN_DISASSEMBLE_STRUCTURE_TRANSFER_LOGGER.debug("Disassemble Structure Transfer Result List ::: " + transferResult.getResults());        
        return transferResult.getResults();
    }
    
    public TransferResult sentDisassembleToLn(Context context, BusinessObject generalSystemBO, DisAssemblyLinesVAL disAssemblyLinesVALForRequest){
        TransferResult transferResult = new TransferResult();
        ProcessDisAssemblyLinesRequestType processDisAssemblyLinesRequest = new ProcessDisAssemblyLinesRequestType();
        
        try {
            LNHandlerResolver handeler = null;
            Integer compnay = Integer.parseInt(ApplicationProperties.getProprtyValue("ln.activationType.company"));
            handeler = new LNHandlerResolver();
            ActivationType activationType = new ActivationType();
            activationType.setCompany(compnay);
            activationType.setSender(ApplicationProperties.getProprtyValue("ln.activationType.sender"));
            
            LNSoapHandler soapHandler = new LNSoapHandler(activationType);
            handeler.addHandler(soapHandler);
            
            URL url = new URL(ApplicationProperties.getProprtyValue("ln.disassemble.structure.wsdl.url"));
            
            DisAssemblyLinesVALService disAssemblyLinesVALService = new DisAssemblyLinesVALService(url);
            disAssemblyLinesVALService.setHandlerResolver(handeler);
            
            com.infor.businessinterface.disassemblylines_val.DisAssemblyLinesVAL disAssemblyLinesVAL = disAssemblyLinesVALService.getDisAssemblyLinesVALSoapPort();
            
            LNResponseUtil.setWebServiceTimeoutLimit((BindingProvider) disAssemblyLinesVAL);
            DataArea dataArea = new DataArea();
            dataArea.getDisAssemblyLinesVAL().add(disAssemblyLinesVALForRequest);
            dataArea.setMessageID(ApplicationProperties.getProprtyValue("ln.data.area.disassemble.structure.message.id"));//ln.data.area.disassemble.structure.message.id
	    dataArea.setReceiver(ApplicationProperties.getProprtyValue("ln.data.area.receiver"));
            processDisAssemblyLinesRequest.setDataArea(dataArea);
            
            ControlArea controlArea = new ControlArea();
            controlArea.setProcessingScope(com.infor.businessinterface.disassemblylines_val.ProcessingScope.NOT_APPLICABLE);
	    processDisAssemblyLinesRequest.setControlArea(controlArea);
            
            try {
                ProcessDisAssemblyLinesResponseType processDisAssemblyLinesResponse = disAssemblyLinesVAL.processDisAssemblyLines(processDisAssemblyLinesRequest);            
                transferResult = processResponse(context,generalSystemBO, processDisAssemblyLinesResponse);
            } catch (Result ex) {
                LN_DISASSEMBLE_STRUCTURE_TRANSFER_LOGGER.error(ex);
            } catch (Exception ex) {
                LN_DISASSEMBLE_STRUCTURE_TRANSFER_LOGGER.error(ex);
            }          
        } catch (MalformedURLException ex) {
            LN_DISASSEMBLE_STRUCTURE_TRANSFER_LOGGER.error(ex);
        }
        
        return transferResult;
    }
    /**
     * 
     * @param context
     * @param generalSystemBO
     * @param processDisAssemblyLinesResponse
     * @return
     * @throws Exception 
     */
    private TransferResult processResponse(Context context, BusinessObject generalSystemBO, ProcessDisAssemblyLinesResponseType processDisAssemblyLinesResponse) throws Exception {
        ArrayList<ResponseResult> resultList = new ArrayList<>();
        TransferResult tr = new TransferResult();

        List<ProcessDisAssemblyLinesResponseType.DataArea.DisAssemblyLinesVAL> successfulDisAssemblyLinesVAList = processDisAssemblyLinesResponse.getDataArea().getDisAssemblyLinesVAL();
        for (ProcessDisAssemblyLinesResponseType.DataArea.DisAssemblyLinesVAL successfulDisAssemblyLinesVAL : successfulDisAssemblyLinesVAList) {
            List<ProcessDisAssemblyLinesResponseType.DataArea.DisAssemblyLinesVAL.ItemDetails> successfulDisAssemblyLinesVALItemDetailsList = successfulDisAssemblyLinesVAL.getItemDetails();
            for (ProcessDisAssemblyLinesResponseType.DataArea.DisAssemblyLinesVAL.ItemDetails itemDetails : successfulDisAssemblyLinesVALItemDetailsList) {
                ResponseResult responseResult = new ResponseResult(itemDetails.getDisAssemblyItem(), "RESULT OK", true);
            }
        }

        if (processDisAssemblyLinesResponse.getInformationArea() != null) {
            List<com.infor.businessinterface.disassemblylines_val.InformationMessage> failedItemMessageList = processDisAssemblyLinesResponse.getInformationArea().getMessage();
            for (com.infor.businessinterface.disassemblylines_val.InformationMessage failedItemMessage : failedItemMessageList) {
                if (!msgToBeIgnored.contains(failedItemMessage.getMessageCode())) {
                    ResponseResult responseResult = LNResponseUtil.parseErrorMessage(failedItemMessage.getMessageText());
                    resultList.add(responseResult);
                }
            }
        }
        tr.setResults(resultList);
        return tr;
    }

    @Override
    public Object transfer(TransferInputParams transferObj, boolean isService, List<String> transferedItems) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
