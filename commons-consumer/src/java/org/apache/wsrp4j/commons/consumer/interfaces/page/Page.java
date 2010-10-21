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
package org.apache.wsrp4j.commons.consumer.interfaces.page;

import java.util.Iterator;
import java.util.Map;

import org.apache.wsrp4j.commons.consumer.interfaces.portlet.PortletKey;

/**
 * Interface defines a page with portlets.
 *
 * @version $Id: Page.java 374672 2006-02-03 14:10:58Z cziegeler $
 */
public interface Page {
    
    /**
     * Get the keys of all portlet instances on the page.
     *
     * @return Iterator with the instance keys of all portlets on the page
     **/
    Iterator getPortletInstanceKeys();
    
    /**
     * Get the portlet key to the given portlet instance key.
     *
     * @param portletInstanceKey Key of a portlet instance on the page
     * @return The portlet key
     **/
    PortletKey getPortletKey(String portletInstanceKey);
    
    /**
     * Get the ID of this page
     *
     * @return The page ID
     **/
    String getPageID();
    
    /**
     * Add an portlet to the page. The ID of this portlet on the page
     * is generated.
     *
     * @param portletKey The portlet key
     **/
    void addPortlet(PortletKey portletKey);
    
    /**
     * Add an portlet to the page and set the ID of this portlet on
     * the page to the given value. If any other portlet exists on the page
     * with the same ID the old portlet is removed.
     *
     * @param portletKey The portlet key
     * @param instanceKey ID of the portlet on the page
     **/
    void addPortlet(PortletKey portletKey, String instanceKey);
    
    /**
     * Remove an portlet instance from the page
     *
     * @param portletInstanceKey The instance key of the portlet instance to be 
     * removed
     **/
    void removePortlet(String portletInstanceKey);
    
    /**
     * Set the page ID
     *
     * @param pageID ID of this page
     **/
    void setPageID(String pageID);
    
    /**
     * Get the title of the page
     *
     * @return Title of this page
     **/
    String getTitle();
    
    /**
     * Set the title of the page
     *
     * @param title Page title
     **/
    void setTitle(String title);
    
    /**
     * Get the instance key of the portlet on this page which is in maximized 
     * window state or null if no portlet is in that state.
     *
     * @return portlet instance key of the portlet or null if no portlet is in 
     * max window state
     **/
    String getMaximizedPortletInstanceKey();
    
    /**
     * Set the portlet which is currently in maximized window state
     *
     * @param portletInstanceKey instance ID
     **/
    void setMaximizedPortletInstanceKey(String portletInstanceKey);
    
    /**
     * Set the portlets which should appear on the page. The map must contain
     * mappings of portlet instance keys to portlet keys.
     **/
    void setPortlets(Map map);
    
    /**
     * Get map with portlet instance keys map to portlet keys with all portlet 
     * instances on this page.
     **/
    Map getPortlets();
    
}
