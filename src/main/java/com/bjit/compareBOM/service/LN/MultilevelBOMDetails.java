/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.compareBOM.service.LN;

import com.bjit.context.DisableSSLCertificate;
import com.bjit.ex.integration.transfer.service.impl.LN.LNResponseUtil;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import com.bjit.ex.integration.transfer.ws.soap.LNHandlerResolver;
import com.bjit.ex.integration.transfer.ws.soap.LNSoapHandler;
import com.infor.businessinterface.item_val.ActivationType;
//import com.infor.businessinterface.multilevelbomdetails_val.ActivationType;
import com.infor.businessinterface.multilevelbomdetails_val.GetBOMDetailsRequestType;
import com.infor.businessinterface.multilevelbomdetails_val.GetBOMDetailsResponseType;
import com.infor.businessinterface.multilevelbomdetails_val.MultilevelBOMDetailsVALService;
import com.infor.businessinterface.multilevelbomdetails_val.Result;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.xml.ws.BindingProvider;

/**
 *
 * @author Sajjad
 */
public class MultilevelBOMDetails {

    static final org.apache.log4j.Logger BOM_COMPARE_ACTION = org.apache.log4j.Logger.getLogger(MultilevelBOMDetails.class.getName());
    private GetBOMDetailsResponseType bomDetailsResponse;

    public GetBOMDetailsResponseType getBOMDetailsFromLn(List<GetBOMDetailsRequestType.DataArea.MultilevelBOMDetailsVAL> multilevelBOMDetailsVAL) throws com.infor.businessinterface.multilevelbomdetails_val.Result {
        if (this.bomDetailsResponse == null) {
            Thread t = new Thread() {
                public void run() {
                    synchronized (this) {
                        try {
                            DisableSSLCertificate.DisableCertificate();
                        } catch (KeyManagementException | NoSuchAlgorithmException ex) {
                            ex.printStackTrace();
                            BOM_COMPARE_ACTION.error(ex.getMessage());
                        }

                        try {
                            URL url = new URL(ApplicationProperties.getProprtyValue("ln.bomcompare.service.wsdl.url"));
                            MultilevelBOMDetailsVALService service = new MultilevelBOMDetailsVALService(url);

                            LNHandlerResolver handeler = null;
                            Integer compnay = Integer.parseInt(ApplicationProperties.getProprtyValue("ln.activationType.company"));
                            handeler = new LNHandlerResolver();
                            ActivationType activationType = new ActivationType();
                            activationType.setCompany(compnay);// Item VAL activation type
                            activationType.setSender(ApplicationProperties.getProprtyValue("ln.activationType.sender"));
                            LNSoapHandler soapHandler = new LNSoapHandler(activationType);
                            handeler.addHandler(soapHandler);
                            service.setHandlerResolver(handeler);

                            GetBOMDetailsRequestType.ControlArea bomDetailsControlArea = new GetBOMDetailsRequestType.ControlArea();
                            bomDetailsControlArea.setProcessingScope(com.infor.businessinterface.multilevelbomdetails_val.ProcessingScope.BUSINESS_ENTITY);
                            GetBOMDetailsRequestType.DataArea bomDetailsDataArea = new GetBOMDetailsRequestType.DataArea();
                            //bomDetailsDataArea.setMessageID(properties.getMessage("lsm.ln.transfer.bomcompare.message.id"));
                            bomDetailsDataArea.setMessageID(ApplicationProperties.getProprtyValue("ln.data.area.multilevelbom.message.id"));
                            bomDetailsDataArea.setReceiver(ApplicationProperties.getProprtyValue("ln.data.area.receiver"));
                            bomDetailsDataArea.getMultilevelBOMDetailsVAL().addAll(multilevelBOMDetailsVAL);

                            GetBOMDetailsRequestType getBOMDetailsRequest = new GetBOMDetailsRequestType();
                            getBOMDetailsRequest.setControlArea(bomDetailsControlArea);
                            getBOMDetailsRequest.setDataArea(bomDetailsDataArea);

                            com.infor.businessinterface.multilevelbomdetails_val.MultilevelBOMDetailsVAL bomDetailsService = service.getMultilevelBOMDetailsVALSoapPort();
                            LNResponseUtil.setWebServiceTimeoutLimit((BindingProvider) bomDetailsService);
                            GetBOMDetailsResponseType response= bomDetailsService.getBOMDetails(getBOMDetailsRequest);
                            //GetBOMDetailsResponseType bomDetailsResponse = bomDetailsService.getBOMDetails(getBOMDetailsRequest);
                            bomDetailsResponse = response;
                            notify();
                        } catch (MalformedURLException ex) {
                            ex.printStackTrace();
                            BOM_COMPARE_ACTION.error(ex.getMessage());
                        } catch (Result ex) {
                            ex.printStackTrace();
                            BOM_COMPARE_ACTION.error(ex.getMessage());
                        }
                    }
                }
            };
            t.setContextClassLoader(MultilevelBOMDetails.class.getClassLoader());
            t.start();
            synchronized (t) {
                try {
                    t.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    BOM_COMPARE_ACTION.error(e.getMessage());
                }
            }
        }
        return this.bomDetailsResponse;
    }
}
