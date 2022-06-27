/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.test;

import com.bjit.context.ContextGeneration;
import com.bjit.ex.integration.transfer.actions.utilities.BusinessObjectUtils;
import static com.bjit.ex.integration.transfer.resultsender.ResponseResultSender.recipients;
import com.bjit.ex.integration.transfer.resultsender.util.ResultSenderUtil;
import java.util.ArrayList;
import java.util.Set;
import matrix.db.BusinessObject;
import matrix.db.Context;

/**
 *
 * @author Sajjad
 */
public class UtilityTest {
    
    static final org.apache.log4j.Logger LN_UTILITY_TEST_LOGGER = org.apache.log4j.Logger.getLogger(UtilityTest.class.getName());

    public static void main(String[] args) {
        try {
            Context context = null;
            context = ContextGeneration.createContext();
            
            /*BusinessObject bo = new BusinessObject("49248.18720.51730.26484");
            boolean result = TransferObjectUtils.isDrawingOrDocSupported(context, bo);
            System.out.println("result " + result);*/
            
            ArrayList<BusinessObject> reviewerBOList = new ArrayList<>();
            BusinessObject bo = new BusinessObject("11056.7015.37552.47019");            
            BusinessObjectUtils businessObjectUtil = new BusinessObjectUtils();
            reviewerBOList = businessObjectUtil.getChangeActionReviewersFromItem(context, bo);
            for (BusinessObject reviewerBO : reviewerBOList) {
                reviewerBO.open(context);
                String reviewerName = reviewerBO.getName();
                System.out.println("reviewerName :: " + reviewerName);
                Set<String> recipients = BusinessObjectUtils.getRecipientsByOwnerName(context, reviewerName);
                System.out.println("recipients :: "+recipients);
                reviewerBO.close(context);
            }         
            
            BusinessObject ChangeBO = new BusinessObject("35272.43169.15847.62724");
            businessObjectUtil = new BusinessObjectUtils();
            reviewerBOList = businessObjectUtil.getChangeActionReviewersFromChangeAction(context, ChangeBO);            
            for (BusinessObject reviewerBO : reviewerBOList) {
                reviewerBO.open(context);
                String reviewerName = reviewerBO.getName();
                System.out.println("reviewerName :: " + reviewerName);
                Set<String> recipients = BusinessObjectUtils.getRecipientsByOwnerName(context, reviewerName);
                System.out.println("recipients :: "+recipients);
                reviewerBO.close(context);
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            LN_UTILITY_TEST_LOGGER.error(ex);
        }
    }
}
