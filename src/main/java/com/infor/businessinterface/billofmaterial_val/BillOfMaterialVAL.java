
package com.infor.businessinterface.billofmaterial_val;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "BillOfMaterial_VAL", targetNamespace = "http://www.infor.com/businessinterface/BillOfMaterial_VAL")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface BillOfMaterialVAL {


    /**
     * 
     * @param processItemBOMDataRequest
     * @return
     *     returns com.infor.businessinterface.billofmaterial_val.ProcessItemBOMDataResponseType
     * @throws Result
     */
    @WebMethod
    @WebResult(name = "processItemBOMDataResponse", partName = "processItemBOMDataResponse")
    public ProcessItemBOMDataResponseType processItemBOMData(
        @WebParam(name = "processItemBOMDataRequest", partName = "processItemBOMDataRequest")
        ProcessItemBOMDataRequestType processItemBOMDataRequest)
        throws Result
    ;

}
