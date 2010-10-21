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
package org.apache.wsrp4j.commons.exception;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.StringTokenizer;

import oasis.names.tc.wsrp.v1.intf.*;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Handles the throwing of exceptions.
 * Instead of calling <code> throw(new WSRPException("exception text")) </code>
 * simply type <code>ExceptionHelper throwX(1234)</code>
 * So the appropriate messages can be handled in a central place.
 * Also the Exceptions will be logged
 *
 * @version $Id: WSRPXHelper.java 405794 2006-05-10 16:46:24Z dlouzan $
 */
public class WSRPXHelper {
    
    private static final Log log = LogFactory.getLog(WSRPXHelper.class);
    
    // the name of the .properties file which contains exception mappings
    private static String EXCEPTION_MAPPING_FILE =
            "ExceptionMapping.properties";
    private static final String MSG_EXCEPTION_MAPPING_ON_LOAD =
            "Error while loading exception mappings from " +
            EXCEPTION_MAPPING_FILE + ".";
    // the content of the .properties file
    private static HashMap exceptionMapping;
    
    private static final String EMPTY_STRING = "";
    
    static {
        //load properties file
        try {
            exceptionMapping = new HashMap();
            
            InputStream in = WSRPXHelper.class.getClassLoader().
                    getResourceAsStream(EXCEPTION_MAPPING_FILE);
            Properties props = new Properties();
            props.load(in);
            
            // convert read Properties to a more useful representation
            Enumeration keys = props.propertyNames();
            StringTokenizer tokenizer = null;
            
            while (keys.hasMoreElements()) {
                
                String value = null;
                String currentKey = (String) keys.nextElement();
                exceptionMapping.put(currentKey, new Integer(currentKey));
                
                value = props.getProperty(currentKey);
                if (value != null && value.length() > 0) {
                    tokenizer = new StringTokenizer(value, ",");
                    String token = null;
                    
                    while (tokenizer.hasMoreTokens()) {
                        token = tokenizer.nextToken();
                        
                        if (token != null) {
                            exceptionMapping.put(
                                    token, new Integer(currentKey));
                        }
                        
                    }
                    
                }
            }
            if (log.isInfoEnabled()) {
                log.info("Loaded exception mappings from file: " +
                        EXCEPTION_MAPPING_FILE);
            }
            
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(MSG_EXCEPTION_MAPPING_ON_LOAD, e);
            }
        }
    }
    
    public static void throwX(int errorCode)
    throws WSRPException {
        
        WSRPException e = new WSRPException(errorCode);
        throw e;
        
    }
    
    public static void throwX(Log extLog, int errorCode)
    throws WSRPException {
        
        WSRPException e = new WSRPException(errorCode);
        
        extLog.error(EMPTY_STRING, e);
        
        throw e;
        
    }
    
    public static void throwX(Log extLog, int errorCode, Throwable t)
    throws WSRPException {
        
        WSRPException e = new WSRPException(errorCode, t);
        
        if (t instanceof WSRPException) {
            extLog.error(e.getMessage());
        } else {
            extLog.error(EMPTY_STRING, e);
        }
        
        throw e;
        
    }
    
    
    /**
     * This method translates a WSRP fault into a WSRP
     * exception containing a corresponding error code
     * and logs the occurence of the exception
     *
     * @param extLog the logger to be used
     * @param wsrpFault the fault to be translated
     * @exception WSRPException this is the translated exception
     */
    public static void handleWSRPFault(Log extLog, Exception wsrpFault)
    throws WSRPException {
        
        int errorCode = 0;
        
        if (wsrpFault instanceof AccessDenied) {
            
            errorCode = ErrorCodes.ACCESS_DENIED;
            
        } else if (wsrpFault instanceof InconsistentParameters) {
            
            errorCode = ErrorCodes.INCONSISTENT_PARAMETERS;
            
        } else if (wsrpFault instanceof InvalidRegistration) {
            
            errorCode = ErrorCodes.INVALID_REGISTRATION;
            
        } else if (wsrpFault instanceof InvalidCookie) {
            
            errorCode = ErrorCodes.INVALID_COOKIE;
            
        } else if (wsrpFault instanceof InvalidHandle) {
            
            errorCode = ErrorCodes.INVALID_HANDLE;
            
        } else if (wsrpFault instanceof InvalidSession) {
            
            errorCode = ErrorCodes.INVALID_SESSION;
            
        } else if (wsrpFault instanceof InvalidUserCategory) {
            
            errorCode = ErrorCodes.INVALID_USER_CATEGORY;
            
        } else if (wsrpFault instanceof MissingParameters) {
            
            errorCode = ErrorCodes.MISSING_PARAMETERS;
            
        } else if (wsrpFault instanceof OperationFailed) {
            
            errorCode = ErrorCodes.OPERATION_FAILED;
            
        } else if (wsrpFault instanceof PortletStateChangeRequired) {
            
            errorCode = ErrorCodes.PORTLET_STATE_CHANGE_REQUIRED;
            
        } else if (wsrpFault instanceof UnsupportedLocale) {
            
            errorCode = ErrorCodes.UNSUPPORTED_LOCALE;
            
        } else if (wsrpFault instanceof UnsupportedMimeType) {
            
            errorCode = ErrorCodes.UNSUPPORTED_MIME_TYPE;
            
        } else if (wsrpFault instanceof UnsupportedMode) {
            
            errorCode = ErrorCodes.UNSUPPORTED_MODE;
            
        } else if (wsrpFault instanceof UnsupportedWindowState) {
            
            errorCode = ErrorCodes.UNSUPPORTED_WINDOW_STATE;
            
        } else {
            
            errorCode = ErrorCodes.INTERNAL_ERROR;
            
        }
        
        WSRPXHelper.throwX(extLog, errorCode, wsrpFault);
        
    }
    
}
