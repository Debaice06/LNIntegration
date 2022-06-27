/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.context;


//import com.bjit.matrix.connection.Passport;
import java.util.logging.Level;
import java.util.logging.Logger;
import matrix.db.BusinessObject;
import matrix.db.Context;
import matrix.db.MQLCommand;

/**
 *
 * @author Sajjad
 */
public class TestContext {

    public static void main(String[] args) {
        modifyAttributeValue();
        /*try {
            String[] passport = Passport.getTicket("https://3dspace-18xdev3-plm.valmet.com:8180/3dspace/", "jklalrahab", "Passport1").split(";");
            String ticket = passport[0];
            String jsessionId = passport[1];
            System.out.println("passport: " + ticket);
            Context context = new Context("https://3dspace-18xdev3-plm.valmet.com:8180/3dspace/" + ticket);
            System.out.println("contest "+context.isConnected());
            //context.setProtocol("http");
            String objetId = "59468.24021.59976.48714";
            context.connect();
            System.out.println("\n\n\n Conected :::: "+context.isConnected());
            //String query = "temp query bus Provide * * where 'attribute[MBOM_MBOMReference.MBOM_Item_Code]==VAL33' select id";
            //String query = "print bus 216.54875.18780.1122 select owner attribute[MBOM_MBOMReference.MBOM_Item_Code].value";
            //String query = "delete businessobject  Provide 0000000004 1.1";
            String query = "temp query bus Provide * * where 'owner == jklalrahab' select id";
            //String query = "Print bus "+objetId+" where 'attribute[MBOM_MBOMReference.MBOM_Item_Code]==VAL33'";            

            MQLCommand mql = new MQLCommand();
            mql.executeCommand(context, query);
            String result = mql.getResult().trim();
            System.out.println("result " + result);
            BusinessObject bo = new BusinessObject("59468.24021.59976.48714");
            System.out.println("NAME   ::: "+bo.getName());
            context.disconnect();

        } catch (Exception e) {
            System.out.println("Exception:: " + e);
        }*/
    }

    private static void modifyAttributeValue() {
        //context.generation.user.id=jklalrahab
        //context.generation.user.pass=Passport1
        ContextGeneration cg = new ContextGeneration();
        try {
            Context context = cg.getPassportContext18x("coexusr1", "Passport1");
            System.out.println("IS CONNECTED : "+context.isConnected());
            BusinessObject bo = new BusinessObject("23040.48585.36424.41043");
            bo.setAttributeValue(context, "PLMEntity.V_nature", "2");
        } catch (Exception ex) {
            Logger.getLogger(TestContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
