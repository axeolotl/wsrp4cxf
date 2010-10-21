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

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import javax.portlet.PortletConfig;
import javax.portlet.PortletMode;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import org.apache.wsrp4j.commons.consumer.interfaces.urlgenerator.URLGenerator;
import org.apache.wsrp4j.commons.util.Constants;
import org.apache.wsrp4j.commons.util.Modes;
import org.apache.wsrp4j.commons.util.WindowStates;

/**
 * This class implements the URLGenerator-interface providing methods
 * to query the consumer's urls.
 * @version $Id: URLGeneratorImpl.java 374672 2006-02-03 14:10:58Z cziegeler $
 */
public class URLGeneratorImpl implements URLGenerator {
    
    private RenderResponse renderResponse;
    private Map consumerParameters;
    
    // URL to resource proxy
    private static String rpURL;
    
    private static String RESOURCE_PROXY = "/WSRP4JResourceProxy";
    
    public static URLGenerator getInstance(RenderResponse response, 
            PortletConfig config) {
        
        if (response == null) {
            throw new IllegalArgumentException("response must not be null");
        }
        if (config == null) {
            throw new IllegalArgumentException("config must not be null");
        }
        
      if(rpURL == null)
        setResourceProxyURL(config);
        
      return new URLGeneratorImpl(response);

    }
    
    /**
     * @param config
     */
    private static void setResourceProxyURL(PortletConfig config) {
        String fullPath = config.getPortletContext().getRealPath("");
        int i = fullPath.lastIndexOf(File.separatorChar);
        rpURL = "/";
        rpURL = rpURL.concat(fullPath.substring(i+1));
        rpURL = rpURL.concat(RESOURCE_PROXY);
    }
    
    private URLGeneratorImpl(RenderResponse response) {
        this.renderResponse = response;
    }
    
    public void setConsumerParameters(Map consumerParameters) {
        if (consumerParameters != null) {
            this.consumerParameters = consumerParameters;
        }
    }

    public String getBlockingActionURL(Map params) {
        PortletURL url = renderResponse.createActionURL();
        
        if (params != null) {
            
            Iterator iter = params.keySet().iterator();
            
            String paramName = "";
            String paramValue = "";
            
            while (iter.hasNext()) {
                paramName = (String)iter.next();
                
                if (paramName.equalsIgnoreCase(Constants.WINDOW_STATE)) {
                    if ((paramValue = (String)params.get(paramName)) != null) {
                        
                        setWindowState(url, paramValue);
                        
                    }
                } else if (paramName.equalsIgnoreCase(Constants.PORTLET_MODE)) {
                    if ((paramValue = (String)params.get(paramName)) != null) {
                        
                        setPortletMode(url, paramValue);
                        
                    }
                } else {
                    if ((paramValue = (String)params.get(paramName)) != null) {
                        
                        url.setParameter(paramName, paramValue);
                        
                    }
                }
            }
        }
        
        if (consumerParameters != null) {
            
            Iterator iter2 = consumerParameters.keySet().iterator();
            String name = null;
            String value = null;
            
            while (iter2.hasNext()) {
                
                name = (String) iter2.next();
                if ((value = (String)consumerParameters.get(name)) != null) {
                    url.setParameter(name, value);
                }
            }
        }
        
        return url.toString();
        
    }
    
    public String getRenderURL(Map params) {
        
        PortletURL url = renderResponse.createRenderURL();
        
        if (params != null) {
            
            Iterator iter = params.keySet().iterator();
            
            String paramName = "";
            String paramValue = "";
            
            while (iter.hasNext()) {
                paramName = (String)iter.next();
                
                if (paramName.equalsIgnoreCase(Constants.WINDOW_STATE)) {
                    if ((paramValue = (String)params.get(paramName)) != null) {
                        
                        setWindowState(url, paramValue);
                        
                    }
                } else if (paramName.equalsIgnoreCase(Constants.PORTLET_MODE)) {
                    if ((paramValue = (String)params.get(paramName)) != null) {
                        
                        setPortletMode(url, paramValue);
                        
                    }
                } else {
                    if (!paramName.equalsIgnoreCase(Constants.URL_TYPE)
                    && (paramValue = (String)params.get(paramName)) != null) {
                        
                        url.setParameter(paramName, paramValue);
                        
                    }
                }
            }
        }
        
        if (consumerParameters != null) {
            
            Iterator iter2 = consumerParameters.keySet().iterator();
            String name = null;
            String value = null;
            
            while (iter2.hasNext()) {
                
                name = (String) iter2.next();
                if ((value = (String)consumerParameters.get(name)) != null) {
                    url.setParameter(name, value);
                }
            }
        }
        
        return url.toString();
        
    }
    
    public String getResourceURL(Map params) {
        
        String resourceUrl = this.renderResponse.encodeURL(rpURL);
        
        // TODO
        if (resourceUrl == null)
            return "Error";
        
        StringBuffer url = new StringBuffer(resourceUrl);
        
        if (params != null) {
            
            String paramValue = null;
            
            if ((paramValue = (String)params.get(Constants.URL)) != null) {
                
                url.append(Constants.PARAMS_START);
                url.append(Constants.URL);
                url.append(Constants.EQUALS);
                url.append(paramValue);
                
            }
        }
        
        return url.toString();
    }
    
    public String getNamespacedToken(String token) {
        return renderResponse.getNamespace();
    }
    
    /**
     * Maps wsrp-windowStates to pluto-windowStates.
     */
    private void setWindowState(PortletURL url, String windowState) {
        
        try {
            if (windowState.equalsIgnoreCase(WindowStates._maximized)) {
                
                url.setWindowState(WindowState.MAXIMIZED);
                
            } else if (windowState.equalsIgnoreCase(WindowStates._minimized)) {
                
                url.setWindowState(WindowState.MINIMIZED);
                
            } else if (windowState.equalsIgnoreCase(WindowStates._normal)) {
                
                url.setWindowState(WindowState.NORMAL);
                
            } else if (windowState.equalsIgnoreCase(WindowStates._solo)) {
                
                url.setWindowState(WindowState.MAXIMIZED);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * Maps wsrp-portletModes to pluto-portletModes.
     **/
    private void setPortletMode(PortletURL url, String mode) {
        
        try {
            if (mode.equalsIgnoreCase(Modes._edit)) {
                
                url.setPortletMode(PortletMode.EDIT);
                
            } else if (mode.equalsIgnoreCase(Modes._view)) {
                
                url.setPortletMode(PortletMode.VIEW);
                
            } else if (mode.equalsIgnoreCase(Modes._help)) {
                
                url.setPortletMode(PortletMode.HELP);
                
            } else if (mode.equalsIgnoreCase(Modes._preview)) {
                
                url.setPortletMode(PortletMode.VIEW);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
