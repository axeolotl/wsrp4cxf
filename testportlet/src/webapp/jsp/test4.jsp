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

<h2>Form Support Test</h2>
Time:
<%=Calendar.getInstance().getTime().toString()%>
<BR/>Current Mode:
<%=renderRequest.getPortletMode()%>
<BR/>Current Window State:
<%=renderRequest.getWindowState()%>
<P/>
<FONT SIZE="-1"><BR/>

<B>Hello
<%
String value = renderRequest.getParameter("name");
if (value != null)
  out.print(value);
else
  out.print("stranger ;-)");
%>
</B>
<P/>
<FONT SIZE="-1"><BR/>
Please enter your name.<BR/>
<BR/>
<FORM ACTION="<%=renderResponse.createActionURL()%>" method="post">
    <TABLE>
        <TR>
            <TD>Your Name :</TD>
            <TD>
                <INPUT type="text" name="name" size="25"/>
            </TD>
        </TR>
     </TABLE>
     <INPUT type="submit" value="Submit"/>
     <INPUT type="reset" value="Reset"/>
</FORM><BR/>
<HR/>
<B>Navigation</B><BR/>
Page 4<BR/>
<%
PortletURL url = renderResponse.createRenderURL();
url.setParameter("jspName","test3.jsp");
%>
<A HREF="<%=url.toString()%>">&lt;&lt; Prev </A> &nbsp;
