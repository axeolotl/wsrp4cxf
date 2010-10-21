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

<h2>URL Types Test</h2>
Time:
<%=Calendar.getInstance().getTime().toString()%>
<BR/>Current Mode:
<%=renderRequest.getPortletMode()%>
<BR/>Current Window State:
<%=renderRequest.getWindowState()%>
<P/>
<FONT SIZE="-1"><BR/>

<B>Render: </B>
<%
PortletURL url = renderResponse.createRenderURL();
%>
Click here on <A HREF="<%=url.toString()%>">Render</A> URL.
<BR/><BR/>

<B>Action: </B>
<%
url = renderResponse.createActionURL();
url.setParameter("ACTION","1");
%>
Click here on <A HREF="<%=url.toString()%>">Action</A> URL.
<BR/>
<I>Group Scope :

<%
PortletSession psession = renderRequest.getPortletSession();
Object group =
        psession.getAttribute("counter1",PortletSession.APPLICATION_SCOPE);
if(group != null) {
    out.print((String)group);
} else {
    out.print("0");
}
%>

</I><BR/>
<I>Portlet Scope :
<%
Object portlet = psession.getAttribute("counter2",PortletSession.PORTLET_SCOPE);
if(portlet != null) {
    out.print((String)portlet);
} else {
    out.print("0");
}
%>
</I><BR/><BR/>

<B>Resource: </B>
<BR/>
<IMG SRC="<%=renderResponse.encodeURL(renderRequest.getContextPath() + 
        "/images/project-logo.jpg")%>" align="TOP"/>
<BR/><BR/>

<B>Namespace: </B>
<%=renderResponse.getNamespace()%>someFunctionHere()<BR/>

<HR/>
<B>Navigation</B><BR/>
Page 1<BR/>
<%
url = renderResponse.createRenderURL();
url.setParameter("jspName","test2.jsp");
%>
<A HREF="<%=url.toString()%>">Next &gt;&gt;</A>
</FONT>
