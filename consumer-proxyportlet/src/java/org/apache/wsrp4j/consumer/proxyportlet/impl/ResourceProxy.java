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

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.wsrp4j.commons.util.Constants;
import org.apache.wsrp4j.commons.util.Utility;

/**
 * This servlet acts as proxy for WSRP resources.
 * 
 * A WSRP resource link should point to this servlet.
 * The URL of the actual resource is taken from the request parameter
 * and an URL connection is opened to the target URL. The input stream
 * of this connection is read and copied to the servlet response stream.
 * @version $Id: ResourceProxy.java 374672 2006-02-03 14:10:58Z cziegeler $
 */
public class ResourceProxy extends HttpServlet {
    
    public void service(HttpServletRequest request, 
            HttpServletResponse response) {
        
        String targetURL = request.getParameter(Constants.URL);
        
        if (targetURL != null) {
            
            try {
                URL url = new URL(Utility.decode(targetURL, 
                        Constants.UTF_8));
                URLConnection conn = url.openConnection();
                
                conn.connect();
                
                response.setContentType(conn.getContentType());
                response.setContentLength(conn.getContentLength());
                
                InputStream in = conn.getInputStream();
                OutputStream out = response.getOutputStream();
                
                int next;
                while ((next = in.read()) != -1) {
                    out.write(next);
                }
                
                out.flush();
                out.close();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
}
