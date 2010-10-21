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
package org.apache.wsrp4j.testportlet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import java.io.IOException;

/**
 * @version $Id: TestPortlet.java 389865 2006-03-29 18:56:44Z jmacna $
 */
public class TestPortlet extends GenericPortlet {

    /**
     * @see javax.portlet.GenericPortlet#processAction(javax.portlet.ActionRequest, javax.portlet.ActionResponse)
     */
    public void processAction(ActionRequest request,
                              ActionResponse actionResponse)
    throws PortletException, IOException {
        String action = request.getParameter("ACTION");
        if (action != null && action.equals("1")) {
            handleCounter(request);
        }
        String nameValue = request.getParameter("name");
        if (nameValue != null) {
            actionResponse.setRenderParameter("name", nameValue);
        }
    }

    private void handleCounter(ActionRequest request) {
        String counter1 = (String) request.getPortletSession().
                getAttribute("counter1", PortletSession.APPLICATION_SCOPE);
        if (counter1 != null) {
            counter1 = Integer.toString(Integer.parseInt(counter1)+1);
        } else {
            counter1 = "1";
        }

        request.getPortletSession().setAttribute(
                "counter1",
                counter1,
                PortletSession.APPLICATION_SCOPE);

        String counter2 = (String) request.getPortletSession().
                getAttribute("counter2", PortletSession.PORTLET_SCOPE);
        if (counter2 != null) {
            counter2 = Integer.toString(Integer.parseInt(counter2)+1);
        } else {
            counter2 = "1";
        }

        request.getPortletSession().setAttribute(
                "counter2",
                counter2,
                PortletSession.PORTLET_SCOPE);
    }

    /**
     * @see javax.portlet.GenericPortlet#doDispatch(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
     */
    public void render(RenderRequest request, RenderResponse response)
    throws PortletException, IOException {

        WindowState state = request.getWindowState();
        if ( ! state.equals(WindowState.MINIMIZED)) {
            String jspName = request.getParameter("jspName");
            if (jspName==null) {
                PortletSession session = request.getPortletSession(false);
                if (session!=null) {
                    jspName = (String) session.getAttribute("jspName", 
                            PortletSession.PORTLET_SCOPE);
                }
                if (jspName==null) jspName = "test1.jsp";
            } else {
                PortletSession session = request.getPortletSession(false);
                if (session!=null) {
                    session.setAttribute("jspName", jspName, 
                            PortletSession.PORTLET_SCOPE);
                }
            }

            PortletContext context = getPortletContext();
            PortletRequestDispatcher rd = 
                    context.getRequestDispatcher("/jsp/"+jspName);
            rd.include(request,response);
        }
    }

	public void init(PortletConfig arg0) throws PortletException {
		super.init(arg0);
	}
}
