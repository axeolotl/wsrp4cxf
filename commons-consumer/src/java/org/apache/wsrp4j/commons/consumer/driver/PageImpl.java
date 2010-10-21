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

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.apache.wsrp4j.commons.util.HandleGenerator;
import org.apache.wsrp4j.commons.util.impl.HandleGeneratorFactoryImpl;
import org.apache.wsrp4j.commons.util.impl.StateChangedServiceImpl;

import org.apache.wsrp4j.commons.consumer.interfaces.portlet.PortletKey;

import org.apache.wsrp4j.commons.consumer.interfaces.page.Page;


/**
 * A simple page with portlets on it.
 *
 * @version $Id: PageImpl.java 374672 2006-02-03 14:10:58Z cziegeler $
 **/
public class PageImpl extends StateChangedServiceImpl implements Page {
    
    private String pageID;
    private String title;
    private Hashtable portlets = new Hashtable();
    private HandleGenerator handleGenerator;
    
    private String maximizedPortletInstanceKey;
    
    /**
     *  Default constructor, for CASTOR persistence use only
     **/
    public PageImpl() {
        
        this.handleGenerator = 
                new HandleGeneratorFactoryImpl().getHandleGenerator();
    }
    
    /**
     * This constructor should be use to create a new page.
     *
     * @param pageID The ID of the page
     * @param portlets A map with portlet instance id's and portlet keys
     **/
    public PageImpl(String pageID, Map portlets) {
        this();
        this.pageID = pageID;
        this.portlets = (Hashtable)portlets;
    }
    
    /**
     * Get the keys of all portlet instances on the page.
     *
     * @return Iterator with the instance keys of all portlets on the page
     **/
    public Iterator getPortletInstanceKeys() {
        return portlets.keySet().iterator();
    }
    
    /**
     * Add an portlet to the page
     *
     * @param portletKey The portlet key
     **/
    public void addPortlet(PortletKey portletKey) {
        if (portletKey != null) {
            
            String instanceKey = handleGenerator.generateHandle();
            portlets.put(instanceKey, portletKey);
            stateChanged();
            
        }
    }
    
    /**
     * Add an portlet to the page and set the ID of this portlet on
     * the page to the given value. If any other portlet exists on the page
     * with the same ID the old portlet is removed.
     *
     * @param portletKey The portlet key
     * @param instanceKey ID of the portlet on the page
     **/
    public void addPortlet(PortletKey portletKey, String instanceKey) {
        
        if (portletKey != null && instanceKey != null) {
            
            portlets.put(instanceKey, portletKey);
            stateChanged();
            
        }
    }
    
    /**
     * Remove an portlet instance from the page
     *
     * @param portletInstanceKey The instance key of the portlet instance to be 
     * removed
     **/
    public void removePortlet(String portletInstanceKey) {
        if (portletInstanceKey != null) {
            portlets.remove(portletInstanceKey);
            stateChanged();
            
            // check if the removed portlet was in maximized window state
            String maxPortletInstanceKey = getMaximizedPortletInstanceKey();
            if (maxPortletInstanceKey != null) {
                
                // lets reset the maximized portlet property of the page
                if (maxPortletInstanceKey.equals(portletInstanceKey)) {
                    setMaximizedPortletInstanceKey(null);
                }
                
            }
        }
    }
    
    /**
     * Get the portlet key to the given portlet instance key.
     *
     * @param portletInstanceKey Key of a portlet instance on the page
     * @return The portlet key
     **/
    public PortletKey getPortletKey(String portletInstanceKey) {
        if (portletInstanceKey == null)
            return null;
        
        return (PortletKey)portlets.get(portletInstanceKey);
    }
    
    /**
     * Get the ID of this page
     *
     * @return The page ID
     **/
    public String getPageID() {
        return pageID;
    }
    
    /**
     * Set the page ID
     *
     * @param pageID ID of this page
     **/
    public void setPageID(String pageID) {
        if (pageID != null) {
            this.pageID = pageID;
            stateChanged();
        }
    }
    
    /**
     * Get the title of the page
     *
     * @return Title of this page
     **/
    public String getTitle() {
        return title;
    }
    
    /**
     * Set the title of the page
     *
     * @param title Page title
     **/
    public void setTitle(String title) {
        if (title != null) {
            this.title = title;
            stateChanged();
        }
    }
    
    /**
     * Get the instance key of the portlet on this page which is in maximized
     * window state or null if no portlet is in that state.
     *
     * @return portlet instance key of the portlet or null if no portlet is 
     * in max window state
     **/
    public String getMaximizedPortletInstanceKey() {
        return maximizedPortletInstanceKey;
    }
    
    /**
     * Set the portlet which is currently in maximized window state
     *
     * @param portletInstanceKey instance ID
     **/
    public void setMaximizedPortletInstanceKey(String portletInstanceKey) {
        maximizedPortletInstanceKey = portletInstanceKey;
    }
    
    /**
     * Set the portlets which should appear on the page. The map must contain
     * mappings of portlet instance id's to portlet keys.
     **/
    public void setPortlets(Map map) {
        
        this.portlets = (Hashtable)map;
        stateChanged();
    }
    
    /**
     * Get map with portlet instance id's map to portlet keys with all portlet 
     * instances on this page.
     **/
    public Map getPortlets() {
        
        return portlets;
    }
    
}
