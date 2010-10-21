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
package org.apache.wsrp4j.commons.util;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Properties;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.wsrp4j.commons.exception.ErrorCodes;
import org.apache.wsrp4j.commons.exception.WSRPException;
import org.apache.wsrp4j.commons.exception.WSRPXHelper;

/**
 * @version $Id: Utility.java 405794 2006-05-10 16:46:24Z dlouzan $
 */
public class Utility {
    
    private static Log log = LogFactory.getLog(Utility.class);
    
    /**
     * Loads a property file with the given name using the class loader.
     * 
     * @return A <code>Properties</code> object containing the properties in
     * the file
     * @throws WSRPException if any error occured
     **/
    public static Properties loadPropertiesFromFile(String fileName)
    throws WSRPException {
        
        try {
            // read in .properties-file
            InputStream in = Utility.class.getClassLoader().
                    getResourceAsStream(fileName);
            Properties properties = new Properties();
            properties.load(in);

            return properties;

        } catch (Exception e) {
            WSRPXHelper.throwX(log, ErrorCodes.PROPERTY_FILE_NOT_FOUND, e);
        }
        
        return null;
        
    }
    
    /**
     * Creates a string of the form "Entering method 'MN'.", where MN is a 
     * method name.
     *
     * @param MN A method name.
     *
     * @return "Entering method '" + MN + "'."
     */
    public static String strEnter(String MN) {
       return new String("Entering method '" + MN + "'."); 
    }
    
    /**
     * Creates a string of the form "Exiting method 'MN'.", where MN is a 
     * method name.
     *
     * @param MN A method name.
     *
     * @return "Exiting method '" + MN + "'."
     */
    public static String strExit(String MN) {
       return new String("Exiting method '" + MN + "'."); 
    }
    

    // FIXME Remove when JDK1.3 support is removed.
    private static Method urlEncode = null;
    private static Method urlDecode = null;

    static {
        if (SystemUtils.isJavaVersionAtLeast(140)) {
            try {
                urlEncode = URLEncoder.class.getMethod("encode", new Class[]{String.class, String.class});
                urlDecode = URLDecoder.class.getMethod("decode", new Class[]{String.class, String.class});
            } catch (NoSuchMethodException e) {
                // EMPTY
            }
        }
    } 

    /**
     * Pass through to the {@link java.net.URLEncoder}. If running under JDK &lt; 1.4,
     * default encoding will always be used.
     */
    public static String encode(String s, String enc) throws UnsupportedEncodingException {
        if (urlEncode != null) {
            try {
                return (String)urlEncode.invoke(s, new Object[]{ s, enc } );
            } catch (IllegalAccessException e) {
                // EMPTY
            } catch (InvocationTargetException e) {
                if (e.getTargetException() instanceof UnsupportedEncodingException) {
                    throw (UnsupportedEncodingException)e.getTargetException();
                } else if (e.getTargetException() instanceof RuntimeException) {
                    throw (RuntimeException)e.getTargetException();
                }
            }
        }
        return URLEncoder.encode(s);
    }

    /**
     * Pass through to the {@link java.net.URLDecoder}. If running under JDK &lt; 1.4,
     * default encoding will always be used.
     */
    public static String decode(String s, String enc) throws UnsupportedEncodingException {
        if (urlDecode != null) {
            try {
                return (String)urlDecode.invoke(s, new Object[]{ s, enc } );
            } catch (IllegalAccessException e) {
                // EMPTY
            } catch (InvocationTargetException e) {
                if (e.getTargetException() instanceof UnsupportedEncodingException) {
                    throw (UnsupportedEncodingException)e.getTargetException();
                } else if (e.getTargetException() instanceof RuntimeException) {
                    throw (RuntimeException)e.getTargetException();
                }
            }
        }
        return URLDecoder.decode(s);
    }
}
