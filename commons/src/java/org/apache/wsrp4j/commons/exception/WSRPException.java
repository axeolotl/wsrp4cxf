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

import org.apache.commons.lang.exception.NestableException;

/**
 * Common Exception class within the WSRP environment 
 *
 * @version $Id: WSRPException.java 405794 2006-05-10 16:46:24Z dlouzan $
 */
public class WSRPException extends NestableException {

    /**
     * Holds the message identifier - enables a  more comfortable error handling
     */
    private int errorCode = 0;


    /**
     * Creates a new common exception. The message to be passed will be ignored
     * @param errorCode integer representing an error code
     */
    public WSRPException(int errorCode) {
        this(errorCode, null);
    }

    /**
     * Creates a new common excpetion. The message to be passed will be ignored
     * @param errorCode integer representing an error code
     * @param t Throwable to be wrapped
     */
    public WSRPException(int errorCode, Throwable t) {
        super(Messages.get(errorCode), t);
        this.errorCode = errorCode;
    }

    /**
     * Returns an error code
     * @return integer representing an error code
     */
    public int getErrorCode() {
        return errorCode;
    }
    
    /**
     * Returns the Exception in the HTML string format. Nested
     * exceptions are included in the report.
     *
     * @ returns exception message formatted as HTML string
     */
    public String toHTMLString() {
        
        StringBuffer s = new StringBuffer();
        
        s.append("<h2>Exception occured!</h2><br>");
        s.append("<b>" + this.getClass().getName() + "</b><br>");
        s.append("    Message = " + getMessage() + "<br>");
        
        if (getCause() != null) {
            
            Throwable t = getCause();
            s.append("<h3>Exception stack:</h3>");
            
            while (t != null) {
                s.append("<br><b>" + t.getClass().getName() +
                        "</b><br>");
                if (t instanceof WSRPException) {
                    s.append("    Message = " +
                            ((WSRPException) t).getMessage() + "<br>");
                    t = ((WSRPException) t).getCause();
                } else {
                    s.append("    Message = " + t.getMessage() + "<br>");
                    t = null;
                }
            }
        }
        
        return s.toString();
        
    }

}
