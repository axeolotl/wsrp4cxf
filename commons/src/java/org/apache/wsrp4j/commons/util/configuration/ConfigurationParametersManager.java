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
package org.apache.wsrp4j.commons.util.configuration;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public final class ConfigurationParametersManager {
    
    public static Log log = LogFactory.
            getLog(ConfigurationParametersManager.class);
    
    private final static String CONFIGURATION_FILE =
            "wsrp4j-config.properties";
    
    private static Map parameters;
    
    static {
        
        try {
            
            /* Read property file (if exists).*/
            Class configurationParametersManagerClass =
                    ConfigurationParametersManager.class;
            ClassLoader classLoader =
                    configurationParametersManagerClass.getClassLoader();
            InputStream inputStream =
                    classLoader.getResourceAsStream(CONFIGURATION_FILE);
            Properties properties = new Properties();
            properties.load(inputStream);
            inputStream.close();
            
            if (log.isInfoEnabled()) {
                log.info("*** Using '" + CONFIGURATION_FILE +
                        "' file for configuration ***");
            }
            
            parameters = new HashMap(properties);
            
        } catch (Exception e) {
            if (log.isFatalEnabled()) {
                log.fatal("*** Could not find '" + CONFIGURATION_FILE +
                        "' file for configuration ***");
            }
        }
        
    }
    
    private ConfigurationParametersManager() {}
    
    public static String getParameter(String name)
    throws MissingConfigurationParameterException {
        
        String value = (String) parameters.get(name);
        
        if (value == null) {
            
            if (log.isInfoEnabled()) {
                log.info("Missing configuration parameter '" + name +
                        "' from '" + CONFIGURATION_FILE +
                        "' configuration file");
            }
            throw new MissingConfigurationParameterException(name);
        }
        
        return value;
        
    }
    
}