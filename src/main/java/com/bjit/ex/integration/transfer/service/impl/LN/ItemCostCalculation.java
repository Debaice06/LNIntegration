/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.service.impl.LN;

import com.bjit.context.DisableSSLCertificate;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.bjit.ex.integration.transfer.ws.soap.LNHandlerResolver;
import com.bjit.ex.integration.transfer.ws.soap.LNSoapHandler;
import com.infor.businessinterface.item_val.ActivationType;
import com.infor.businessinterface.itemcostcalculation_val.CalculateItemCostRequestType;
import com.infor.businessinterface.itemcostcalculation_val.CalculateItemCostResponseType;
import com.infor.businessinterface.itemcostcalculation_val.InformationArea;
import com.infor.businessinterface.itemcostcalculation_val.InformationMessage;
import com.infor.businessinterface.itemcostcalculation_val.ItemCostCalculationVAL;
import com.infor.businessinterface.itemcostcalculation_val.ItemCostCalculationVALService;
import com.infor.businessinterface.itemcostcalculation_val.ProcessingScope;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.BindingProvider;

/**
 *
 * @author Tomal
 */
public class ItemCostCalculation {

    private static final org.apache.log4j.Logger Item_Cost_Calculation_Logger = org.apache.log4j.Logger.getLogger(ItemCostCalculation.class);

    public void calculateCost(List<String> itemName) {
        Item_Cost_Calculation_Logger.info("\n" + ":::::: COST CALCULATION :::::");
        CalculateItemCostRequestType calCulateItemCostRequest = new CalculateItemCostRequestType();
        CalculateItemCostRequestType.ControlArea controlArea = new CalculateItemCostRequestType.ControlArea();
        controlArea.setProcessingScope(ProcessingScope.BUSINESS_ENTITY);
        calCulateItemCostRequest.setControlArea(controlArea);

        CalculateItemCostRequestType.DataArea dataArea = new CalculateItemCostRequestType.DataArea();
        dataArea.setMessageID(ApplicationProperties.getProprtyValue("ln.cost.calculation.message.id"));
        dataArea.setReceiver(ApplicationProperties.getProprtyValue("ln.data.area.receiver"));
        List<CalculateItemCostRequestType.DataArea.ItemCostCalculationVAL> itemCostCalculationVAL = dataArea.getItemCostCalculationVAL();
        CalculateItemCostRequestType.DataArea.ItemCostCalculationVAL itemCostCalVal = new CalculateItemCostRequestType.DataArea.ItemCostCalculationVAL();
        for (int i = 0; i < itemName.size(); i++) {
            itemCostCalVal = new CalculateItemCostRequestType.DataArea.ItemCostCalculationVAL();
            itemCostCalVal.setItem(itemName.get(i));
            itemCostCalculationVAL.add(itemCostCalVal);
        }
        calCulateItemCostRequest.setDataArea(dataArea);

        try {
            String certificatePropertyValue = ApplicationProperties.getProprtyValue("LN.disable.certificate");
            Item_Cost_Calculation_Logger.info("Certificate disable for LN : "+ certificatePropertyValue);
            if (certificatePropertyValue.equalsIgnoreCase("true")){
                DisableSSLCertificate.DisableCertificate();
            }
        } catch (KeyManagementException ex) {
            Item_Cost_Calculation_Logger.error(ex);      
        } catch (NoSuchAlgorithmException ex) {
            Item_Cost_Calculation_Logger.error(ex);
        }

        LNHandlerResolver handeler = null;
        Integer compnay = Integer.parseInt(ApplicationProperties.getProprtyValue("ln.activationType.company"));
        handeler = new LNHandlerResolver();
        ActivationType activationType = new ActivationType();
        activationType.setCompany(compnay);
        activationType.setSender(ApplicationProperties.getProprtyValue("ln.activationType.sender"));
        LNSoapHandler soapHandler = new LNSoapHandler(activationType);
        handeler.addHandler(soapHandler);
        String costCalculationWSDLUrl = ApplicationProperties.getProprtyValue("ln.cost.calculation.wsdl.url");
        Item_Cost_Calculation_Logger.info("cost Calculation WSDL Url : "+ costCalculationWSDLUrl);
        URL url = null;
        try {
            url = new URL(costCalculationWSDLUrl);

        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(ItemCostCalculation.class.getName()).log(Level.SEVERE, null, ex);
        }
        ItemCostCalculationVALService service = new ItemCostCalculationVALService(url);
        service.setHandlerResolver(handeler);
        ItemCostCalculationVAL itemCostCalculationVALSoapPort = service.getItemCostCalculationVALSoapPort();
        setWebServiceTimeoutLimit((BindingProvider) itemCostCalculationVALSoapPort);

        CalculateItemCostResponseType calculateItemCostResponse = null;

        try {
            calculateItemCostResponse = itemCostCalculationVALSoapPort.calculateItemCost(calCulateItemCostRequest);
        } catch (Exception ex) {
            Item_Cost_Calculation_Logger.error("Response from Item Cost calucalation Service : " + ex.getMessage());
        }
        if (calculateItemCostResponse != null) {
            processCostCalculationResponse(calculateItemCostResponse);
        }

    }

    private static void setWebServiceTimeoutLimit(BindingProvider bindingProvider) {
        int timeoutInMiliSec = Integer.parseInt("20000000");//20000000
        //Setting Request timeout
        bindingProvider.getRequestContext().put("com.sun.xml.internal.ws.connect.timeout", timeoutInMiliSec);
        bindingProvider.getRequestContext().put("com.sun.xml.internal.ws.request.timeout", timeoutInMiliSec);
    }

    private void processCostCalculationResponse(CalculateItemCostResponseType calculateItemCostResponse) {
        CalculateItemCostResponseType.DataArea dataArea = calculateItemCostResponse.getDataArea();
        InformationArea informationArea = calculateItemCostResponse.getInformationArea();
        List<CalculateItemCostResponseType.DataArea.ItemCostCalculationVAL> itemCostCalculationVAL = dataArea.getItemCostCalculationVAL();
        if (itemCostCalculationVAL != null) {
            for (int i = 0; i < itemCostCalculationVAL.size(); i++) {
                CalculateItemCostResponseType.DataArea.ItemCostCalculationVAL get = itemCostCalculationVAL.get(i);
                Item_Cost_Calculation_Logger.debug("Cost Calculation enabled for Item: " + get.getItem().trim());
            }
        }
        if (informationArea != null) {
            List<InformationMessage> errorMsgList = informationArea.getMessage();
            for (int i = 0; i < errorMsgList.size(); i++) {
                InformationMessage get = errorMsgList.get(i);
                Item_Cost_Calculation_Logger.error("Error Text: " + get.getMessageText());
            }
        }
    }
}
