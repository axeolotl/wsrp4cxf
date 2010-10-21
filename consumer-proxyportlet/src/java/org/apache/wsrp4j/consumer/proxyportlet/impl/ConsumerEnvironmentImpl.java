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

import oasis.names.tc.wsrp.v1.types.StateChange;

import org.apache.wsrp4j.commons.consumer.interfaces.producer.ProducerRegistry;
import org.apache.wsrp4j.commons.consumer.driver.GenericConsumerEnvironment;
import org.apache.wsrp4j.commons.consumer.driver.PortletDriverRegistryImpl;
import org.apache.wsrp4j.commons.consumer.driver.URLRewriterImpl;
import org.apache.wsrp4j.commons.consumer.util.ConsumerConstants;
import org.apache.wsrp4j.commons.util.Constants;
import org.apache.wsrp4j.commons.util.Modes;
import org.apache.wsrp4j.commons.util.WindowStates;

/**
 * Class implements the consumer environment interface for the proxy portlet
 * consumer.
 * 
 * @version $Id: ConsumerEnvironmentImpl.java 374677 2006-02-03 14:28:31Z cziegeler $
 */
public class ConsumerEnvironmentImpl extends GenericConsumerEnvironment {

    private static String CONSUMER_AGENT = "WSRP4J proxy portlet consumer v0.1";

    private String registryPath;

    public ConsumerEnvironmentImpl() {

        // set the name of the consumer agent
        setConsumerAgent(CONSUMER_AGENT);

        // define the locales the consumer supports
        String[] supportedLocales = new String[2];
        supportedLocales[0] = Constants.LOCALE_EN_US;
        supportedLocales[1] = Constants.LOCALE_DE_DE;
        setSupportedLocales(supportedLocales);

        // define the modes the consumer supports
        String[] supportedModes = new String[3];
        supportedModes[0] = Modes._view;
        supportedModes[1] = Modes._help;
        supportedModes[2] = Modes._edit;
        setSupportedModes(supportedModes);

        // define the window states the consumer supports
        String[] supportedWindowStates = new String[3];
        supportedWindowStates[0] = WindowStates._normal;
        supportedWindowStates[1] = WindowStates._maximized;
        supportedWindowStates[2] = WindowStates._minimized;
        setSupportedWindowStates(supportedWindowStates);

        // define portlet state change behaviour
        setPortletStateChange(StateChange.READ_WRITE);

        // define the mime types the consumer supports
        setMimeTypes(new String[] { Constants.MIME_TYPE_HTML });

        // define the character sets the consumer supports
        setCharacterEncodingSet(new String[] { Constants.UTF_8 });

        // set the authentication method the consumer uses
        setUserAuthentication(ConsumerConstants.NONE);

        // set consumer components
        setUserRegistry(UserRegistryImpl.getInstance());
        setSessionHandler(SessionHandlerImpl.getInstance(this));
        // setProducerRegistry(ProducerRegistryImpl.getInstance());
        setPortletRegistry(PortletRegistryImpl.getInstance());
        setTemplateComposer(URLTemplateComposerImpl.getInstance());
        setURLRewriter(URLRewriterImpl.getInstance());
        setPortletDriverRegistry(PortletDriverRegistryImpl.getInstance(this));
    }

    public void setRegistryPath(String path) {
        this.registryPath = path;
    }

    public ProducerRegistry getProducerRegistry() {
        if (super.getProducerRegistry() == null) {
            setProducerRegistry(ProducerRegistryImpl.getInstance(registryPath));
        }

        return super.getProducerRegistry();
    }
}
