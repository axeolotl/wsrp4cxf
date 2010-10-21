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

import org.apache.wsrp4j.commons.consumer.interfaces.urlgenerator.URLGenerator;
import org.apache.wsrp4j.commons.consumer.interfaces.urlgenerator.URLTemplateComposer;

/**
 * <p>
 * This class implements the URLTemplateComposer interface providing methods to
 * generate URL templates.
 * </p>
 * <p>
 * The generated templates will be transmitted to Producers (or respectively
 * portlets) that are willing to properly write URLs for a Consumer. (With
 * templates the Consumer indicates how it needs URLs formatted in order to
 * process them properly.)
 * </p>
 * 
 * @version $Id: URLTemplateComposerImpl.java 327457 2005-08-23 19:28:12Z
 *          dlouzan $
 */
public class URLTemplateComposerImpl implements URLTemplateComposer {

    private static URLTemplateComposer instance;

    private URLGenerator urlGenerator;

    public static URLTemplateComposer getInstance() {
        if (instance == null) {
            instance = new URLTemplateComposerImpl();
        }

        return instance;
    }

    private URLTemplateComposerImpl() {
    }

    /**
     * Sets the url generator to be used.
     */
    public void setURLGenerator(URLGenerator urlGenerator) {
        this.urlGenerator = urlGenerator;
    }

    public String createBlockingActionTemplate(boolean includePortletHandle,
            boolean includeUserContextKey, boolean includePortletInstanceKey,
            boolean includeSessionID) {

        String template = null;

        // TODO: Template processing Pluto case

        return template;
    }

    public String createSecureBlockingActionTemplate(boolean includePortletHandle,
            boolean includeUserContextKey, boolean includePortletInstanceKey,
            boolean includeSessionID) {

        String template = null;

        // TODO: Template processing Pluto case
        /*
         * 
         * if (this.urlGenerator != null) { template = createTemplate(
         * urlGenerator.getBlockingActionURL(null), false, true, true, true,
         * true, true, false, false, includePortletHandle,
         * includeUserContextKey, includePortletInstanceKey, includeSessionID); }
         */
        return template;
    }

    public String createRenderTemplate(boolean includePortletHandle, boolean includeUserContextKey,
            boolean includePortletInstanceKey, boolean includeSessionID) {

        String template = null;

        // TODO: Template processing Pluto case

        return template;
    }

    public String createSecureRenderTemplate(boolean includePortletHandle,
            boolean includeUserContextKey, boolean includePortletInstanceKey,
            boolean includeSessionID) {

        String template = null;

        // TODO: Template processing Pluto case

        return template;
    }

    public String createResourceTemplate(boolean includePortletHandle,
            boolean includeUserContextKey, boolean includePortletInstanceKey,
            boolean includeSessionID) {

        String template = null;

        // TODO: Template processing Pluto case

        return template;
    }

    public String createSecureResourceTemplate(boolean includePortletHandle,
            boolean includeUserContextKey, boolean includePortletInstanceKey,
            boolean includeSessionID) {

        String template = null;

        // TODO: Template processing Pluto case

        return template;
    }

    public String createDefaultTemplate(boolean includePortletHandle,
            boolean includeUserContextKey, boolean includePortletInstanceKey,
            boolean includeSessionID) {

        String template = null;

        // TODO: Template processing Pluto case

        return template;
    }

    public String createSecureDefaultTemplate(boolean includePortletHandle,
            boolean includeUserContextKey, boolean includePortletInstanceKey,
            boolean includeSessionID) {

        String template = null;

        // TODO: Template processing Pluto case

        return template;
    }

    public String getNamespacePrefix() {
        return urlGenerator.getNamespacedToken("");
    }

}
