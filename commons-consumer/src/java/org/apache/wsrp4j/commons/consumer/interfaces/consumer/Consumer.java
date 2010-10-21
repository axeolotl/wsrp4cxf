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
package org.apache.wsrp4j.commons.consumer.interfaces.consumer;

import org.apache.wsrp4j.commons.consumer.interfaces.session.SessionHandler;
import org.apache.wsrp4j.commons.consumer.interfaces.portlet.PortletRegistry;
import org.apache.wsrp4j.commons.consumer.interfaces.portletdriver.PortletDriverRegistry;
import org.apache.wsrp4j.commons.consumer.interfaces.producer.ProducerRegistry;
import org.apache.wsrp4j.commons.consumer.interfaces.user.UserRegistry;
import org.apache.wsrp4j.commons.consumer.interfaces.urlgenerator.URLRewriter;
import org.apache.wsrp4j.commons.consumer.interfaces.urlgenerator.URLTemplateComposer;

/**
 * The consumer provides access to consumer specific components.
 * 
 * @version $Id: Consumer.java 374672 2006-02-03 14:10:58Z cziegeler $
 */
public interface Consumer {

    /**
     * Get the session handler of the consumer.
     * 
     * @return Interface to the consumer specific session handler
     **/
    SessionHandler getSessionHandler();

    /**
     * Get the portlet registry of the consumer.
     * 
     * @return Interface to the consumer specific portlet registry
     **/
    PortletRegistry getPortletRegistry();

    /**
     * Get the portlet driver registry of the consumer.
     * 
     * @return Interface to the consumer specific portlet driver registry
     **/
    PortletDriverRegistry getPortletDriverRegistry();

    /**
     * Get the producer registry of the consumer.
     * 
     * @return The consumer specific producer registry
     **/
    ProducerRegistry getProducerRegistry();

    /**
     * Get the user registry of the consumer.
     * 
     * @return The consumer specific user registry
     **/
    UserRegistry getUserRegistry();

    /**
     * Get the url template composer for template proccessing
     * 
     * @return Interface to the consumer specific template composer
     **/
    URLTemplateComposer getTemplateComposer();

    /**
     * Get the url rewriter for consumer url-rewriting
     * 
     * @return The consumer specific url rewriter
     **/
    URLRewriter getURLRewriter();

}
