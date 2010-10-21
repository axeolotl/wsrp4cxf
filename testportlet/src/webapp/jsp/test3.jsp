<%--
Copyright 2003-2005 The Apache Software Foundation
Licensed  under the  Apache License,  Version 2.0  (the "License");
you may not use  this file  except in  compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed  under the  License is distributed on an "AS IS" BASIS,
WITHOUT  WARRANTIES OR CONDITIONS  OF ANY KIND, either  express  or
implied.

See the License for the specific language governing permissions and
limitations under the License.
--%>
<%@ page session="true" %>
<%@ taglib uri='http://java.sun.com/portlet' prefix='portlet'%>
<%@ page import="javax.portlet.*"%>
<%@ page import="java.util.*"%>

<portlet:defineObjects/>

<h2>Window States Test</h2>
Time:
<%=Calendar.getInstance().getTime().toString()%>
<BR/>Current Mode:
<%=renderRequest.getPortletMode()%>
<BR/>Current Window State:
<%=renderRequest.getWindowState()%>
<P/>
<FONT SIZE="-1"><BR/>

<B>Normal State: </B>
<%
PortletURL url = renderResponse.createRenderURL();
url.setWindowState(WindowState.NORMAL);
%>
<A HREF="<%=url.toString()%>">Click</A> to change into NORMAL state.
<BR/><BR/>

<B>Maximized State: </B>
<%
url = renderResponse.createRenderURL();
url.setWindowState(WindowState.MAXIMIZED);
%>
<A HREF="<%=url.toString()%>">Click</A> to change into MAXIMIZED state.
<BR/><BR/>

<B>Minimized Mode: </B>
<%
url = renderResponse.createRenderURL();
url.setWindowState(WindowState.MINIMIZED);
%>
<A HREF="<%=url.toString()%>">Click</A> to change into MINIMIZED state.
<BR/><BR/>

<HR/>
<B>Navigation</B><BR/>
Page 3<BR/>
<%
url = renderResponse.createRenderURL();
url.setParameter("jspName","test2.jsp");
%>
<A HREF="<%=url.toString()%>">&lt;&lt; Prev </A>
&nbsp;
<%
url = renderResponse.createRenderURL();
url.setParameter("jspName","test4.jsp");
%>
<A HREF="<%=url.toString()%>">Next &gt;&gt;</A>
</FONT>
