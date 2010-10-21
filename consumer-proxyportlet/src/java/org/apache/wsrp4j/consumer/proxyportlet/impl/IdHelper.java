package org.apache.wsrp4j.consumer.proxyportlet.impl;

import javax.portlet.PortletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by IntelliJ IDEA.
 * User: axel
 * Date: 08.08.2008
 * Time: 23:03:09
 * To change this template use File | Settings | File Templates.
 */
public class IdHelper {
  private static Method getWindowID = null;

  static String getWindowID(PortletRequest request) {
    if(getWindowID == null)
      try {
        getWindowID = request.getClass().getMethod("getWindowID", new Class[]{});
      } catch (Exception e) {
        // dann eben nicht.
        throw new RuntimeException("JSR-286 portlet container required (for getWindowID)", e);
    }
    String windowID = null;
    try {
      windowID = (String) getWindowID.invoke(request, new Object[]{});
    } catch (IllegalAccessException e) {
      throw new RuntimeException("JSR-286 portlet container required (for getWindowID)", e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException("JSR-286 portlet container required (for getWindowID)", e);
    }
    return windowID;
  }

  static  String getPortletEntityID(PortletRequest request) {
    // ### no better standard-compliant approximation possible?
    return getWindowID(request);
  }

}
