/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.context;

import com.bjit.common.code.utility.security.ContextPasswordSecurity;
import com.bjit.ex.integration.transfer.util.ApplicationProperties;
import matrix.db.Context;
import matrix.util.MatrixException;

/**
 *
 * @author Sajjad
 */
public class ContextGeneration {
    
    public static Context context;
    static final org.apache.log4j.Logger CONTEXT_GENERATION_LOGGER = org.apache.log4j.Logger.getLogger(ContextGeneration.class.getName());
    public Context getContext(String userID, String userPassword) {
        Context context = null;
        try {
            String host = "http://unsecured-3dspace-testa-plm.valmet.com:8080/internal";

            context = new Context(host);
            context.setUser(userID);
            if (userPassword != null && !userPassword.equals("")) {
                context.setPassword(userPassword);
            }
            context.connect();

        } catch (MatrixException ex) {
           
            ex.printStackTrace();
        }
        return context;
    }

    public Context getPassportContext18x(String userID, String userPassword) throws Exception {
        Context context = null;
        String _18xURL = "";
        String ticket = "";
        try {
            
            _18xURL = com.bjit.ex.integration.transfer.util.ApplicationProperties.getProprtyValue("context.generation.18x.url");
            System.out.println("\n\n\n\n\n _18xURL :::: "+_18xURL);
            String[] passport = Passport.getTicket(_18xURL, userID, userPassword).split(";");//java.net.ConnectException: Connection timed out: connect
            ticket = passport[0];
            String jsessionId = passport[1];
        }catch(Exception exp){
            CONTEXT_GENERATION_LOGGER.info("Connection timed out : failed to connect !! ");
            exp.printStackTrace(System.out);
        }
        try{
            context = new Context(_18xURL + ticket);
            context.connect();

        } catch (MatrixException exp) {
            CONTEXT_GENERATION_LOGGER.info("Unable to create context !! ");
            exp.printStackTrace(System.out);
            throw exp;
        }
        return context;
    }

    public Context getPassportContext21x(String userID, String userPassword) throws Exception {
        Context context = null;
        String _21xURL = "";
        String ticket = "";
        try {

            _21xURL = com.bjit.ex.integration.transfer.util.ApplicationProperties.getProprtyValue("context.generation.21x.url");
            CONTEXT_GENERATION_LOGGER.info("\nContext_URL :::: " + _21xURL);
            CONTEXT_GENERATION_LOGGER.info("userId ::: "+userID);
            com.bjit.common.code.utility.context.ContextGeneration contextGenerate = new com.bjit.common.code.utility.context.ContextGeneration();
            context = contextGenerate.createContext(_21xURL, userID, userPassword);

        } catch (Exception exp) {
            CONTEXT_GENERATION_LOGGER.info("Connection timed out : failed to connect !! ");
            exp.printStackTrace(System.out);
        }
        return context;
    }

    public static Context createContext() throws Exception {
        try {
            String userId = ApplicationProperties.getProprtyValue("context.generation.user.id");
            String userPass = ApplicationProperties.getProprtyValue("context.generation.user.pass");
            ContextPasswordSecurity contextPassword = new ContextPasswordSecurity();
            userPass = contextPassword.decryptPassword(userPass);
            context = new ContextGeneration().getPassportContext21x(userId, userPass);
            return context;
        } catch (Exception exp) {
            CONTEXT_GENERATION_LOGGER.error(exp.getMessage());
            throw exp;
        }
    }
}
