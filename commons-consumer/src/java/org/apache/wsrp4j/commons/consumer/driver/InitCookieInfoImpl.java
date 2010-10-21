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
package org.apache.wsrp4j.commons.consumer.driver;

import java.net.MalformedURLException;
import java.net.URL;

import oasis.names.tc.wsrp.v1.intf.WSRPV1MarkupPortType;
import oasis.names.tc.wsrp.v1.wsdl.WSRPService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.wsrp4j.commons.consumer.interfaces.session.InitCookieInfo;
import org.apache.wsrp4j.commons.exception.ErrorCodes;
import org.apache.wsrp4j.commons.exception.WSRPException;
import org.apache.wsrp4j.commons.exception.WSRPXHelper;

import javax.xml.ws.BindingProvider;


/**
 * @version $Id: InitCookieInfoImpl.java 405796 2006-05-10 16:48:00Z dlouzan $
 */
public class InitCookieInfoImpl implements InitCookieInfo {
    
    private static final Log log = LogFactory.getLog(InitCookieInfo.class);

    private String markupInterfaceURL;

    private boolean initCookieRequired;

    private boolean initCookieDone;

    private WSRPV1MarkupPortType markupInterface;

    public InitCookieInfoImpl(String markupURL) throws WSRPException {
        final String MN = "init";

        this.initCookieDone = false;
        this.initCookieRequired = false;
        this.markupInterfaceURL = markupURL;

        try {
            WSRPService service = new WSRPService(new URL(markupURL));
          markupInterface = service.getWSRPBaseService();
          // ### does this work?
          ((BindingProvider)markupInterface).getRequestContext().put("javax.xml.ws.session.maintain", Boolean.TRUE);

        } catch (MalformedURLException urlEx) {
            WSRPXHelper.throwX(
                    log,
                    ErrorCodes.INVALID_URL_OF_MARKUP_PORT,
                    urlEx);
        }
    }

    public boolean isInitCookieRequired() {
        return initCookieRequired;
    }

    public void setInitCookieRequired(boolean initCookieRequired) {
        this.initCookieRequired = initCookieRequired;
    }

    public boolean isInitCookieDone() {
        return initCookieDone;
    }

    public void setInitCookieDone(boolean initCookieDone) {
        this.initCookieDone = initCookieDone;
    }

    public String getMarkupInterfaceURL() {
        return markupInterfaceURL;
    }

    public WSRPV1MarkupPortType getWSRPBaseService() {
        return markupInterface;
    }

    public void setWSRPBaseService(WSRPV1MarkupPortType markupPortType) {
        if (markupPortType != null) {
            this.markupInterface = markupPortType;
        }
    }

}
