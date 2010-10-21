/*
 * Copyright 2003-2005 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wsrp4j.consumer.proxyportlet.impl;

import javax.portlet.PortletSession;

import org.apache.wsrp4j.commons.consumer.interfaces.consumer.ConsumerEnvironment;
import org.apache.wsrp4j.commons.consumer.interfaces.producer.Producer;
import org.apache.wsrp4j.commons.consumer.interfaces.session.UserSessionMgr;
import org.apache.wsrp4j.consumer.proxyportlet.SessionHandler;
import org.apache.wsrp4j.commons.exception.WSRPException;

/**
 * @version $Id: SessionHandlerImpl.java 374677 2006-02-03 14:28:31Z cziegeler $
 */
public class SessionHandlerImpl implements SessionHandler {

    private PortletSession portletSession;

    private ConsumerEnvironment consumerEnv;

    private static SessionHandler instance;

    private SessionHandlerImpl(ConsumerEnvironment consumerEnv) {
        this.consumerEnv = consumerEnv;
    }

    public static SessionHandler getInstance(ConsumerEnvironment consumerEnv) {
        if (instance == null) {
            instance = new SessionHandlerImpl(consumerEnv);
        }

        return instance;
    }

    public void setPortletSession(PortletSession portletSession) {
        this.portletSession = portletSession;
    }

    public UserSessionMgr getUserSession(String producerID, String userID) throws WSRPException {
        if (portletSession == null) {
            try {
                throw new Exception("PortletSession is null. " + "Call setPortletSession() first.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            Producer producer = consumerEnv.getProducerRegistry().getProducer(producerID);

            if (producer != null) {

                String markupURL = producer.getMarkupInterfaceEndpoint();

                return new UserSessionImpl(producerID, userID, markupURL, portletSession);
            }
        }

        return null;
    }
}
