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

<h2>Modes Test</h2>
Time:
<%=Calendar.getInstance().getTime().toString()%>
<BR/>Current Mode:
<%=renderRequest.getPortletMode()%>
<BR/>Current Window State:
<%=renderRequest.getWindowState()%>
<P/>
<FONT SIZE="-1"><BR/>

<B>View Mode: </B>
<%
PortletURL url = renderResponse.createRenderURL();
url.setPortletMode(PortletMode.VIEW);
%>
<A HREF="<%=url.toString()%>">Click</A> to change into VIEW mode.
<BR/><BR/>

<B>Help Mode: </B>
<%
url = renderResponse.createRenderURL();
url.setPortletMode(PortletMode.HELP);
%>
<A HREF="<%=url.toString()%>">Click</A> to change into HELP mode.
<BR/><BR/>

<B>Edit Mode: </B>
<%
url = renderResponse.createRenderURL();
url.setPortletMode(PortletMode.EDIT);
%>
<A HREF="<%=url.toString()%>">Click</A> to change into EDIT mode.
<BR/><BR/>

<HR/>
<B>Navigation</B><BR/>
Page 2<BR/>
<%
url = renderResponse.createRenderURL();
url.setParameter("jspName","test1.jsp");
%>
<A HREF="<%=url.toString()%>">&lt;&lt; Prev </A>
&nbsp;
<%
url = renderResponse.createRenderURL();
url.setParameter("jspName","test3.jsp");
%>
<A HREF="<%=url.toString()%>">Next &gt;&gt;</A>
</FONT>
