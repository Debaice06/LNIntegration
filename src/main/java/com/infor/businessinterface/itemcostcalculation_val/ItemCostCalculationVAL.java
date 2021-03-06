
package com.infor.businessinterface.itemcostcalculation_val;

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
@WebService(name = "ItemCostCalculation_VAL", targetNamespace = "http://www.infor.com/businessinterface/ItemCostCalculation_VAL")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ItemCostCalculationVAL {


    /**
     * 
     * @param calculateItemCostRequest
     * @return
     *     returns com.infor.businessinterface.itemcostcalculation_val.CalculateItemCostResponseType
     * @throws Result
     */
    @WebMethod
    @WebResult(name = "calculateItemCostResponse", partName = "calculateItemCostResponse")
    public CalculateItemCostResponseType calculateItemCost(
        @WebParam(name = "calculateItemCostRequest", partName = "calculateItemCostRequest")
        CalculateItemCostRequestType calculateItemCostRequest)
        throws Result
    ;

}
