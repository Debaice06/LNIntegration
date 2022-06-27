/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjit.ex.integration.transfer.ws.soap;

import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

/**
 *
 * @author Sajjad
 */
public class LNHandlerResolver implements HandlerResolver {

    static org.apache.log4j.Logger LN_HANDLER_RESOLVER = org.apache.log4j.Logger.getLogger(LNHandlerResolver.class.getName());
    private List<Handler> handlerChain = new ArrayList<Handler>();

    @Override
    public List<Handler> getHandlerChain(PortInfo portInfo) {
        return handlerChain;
    }

    public void addHandler(Handler handler) {
        this.handlerChain.add(handler);
        LN_HANDLER_RESOLVER.debug("ADD done successfully");
    }
}
