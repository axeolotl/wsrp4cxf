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
package org.apache.wsrp4j.commons.exception;


/**
 * @version $Id: ErrorCodes.java 405794 2006-05-10 16:46:24Z dlouzan $
 */
public interface ErrorCodes {
    
    // Persistence
    int STORE_OBJECT_ERROR                   = 1000;
    int RESTORE_OBJECT_ERROR                 = 1001;
    int DELETE_OBJECT_ERROR                  = 1002;
    int UNMARSHAL_ERROR                      = 1003;
    int MARSHAL_ERROR                        = 1004;
    
    // Faults defined by the WSRP Specification
    int ACCESS_DENIED                        = 1100;
    int INCONSISTENT_PARAMETERS              = 1101;
    int INVALID_REGISTRATION                 = 1102;
    int INVALID_COOKIE                       = 1103;
    int INVALID_HANDLE                       = 1104;
    int INVALID_SESSION                      = 1105;
    int INVALID_USER_CATEGORY                = 1106;
    int MISSING_PARAMETERS                   = 1107;
    int OPERATION_FAILED                     = 1108;
    int PORTLET_STATE_CHANGE_REQUIRED        = 1109;
    int UNSUPPORTED_LOCALE                   = 1110;
    int UNSUPPORTED_MIME_TYPE                = 1111;
    int UNSUPPORTED_MODE                     = 1112;
    int UNSUPPORTED_WINDOW_STATE             = 1113;
    
    // Unspecified Error
    int INTERNAL_ERROR                       = 1200;
    
    
    /************ Error Codes Producer ************/
    
    // Access to factories
    int PRODUCER_ERROR                       = 2000;
    int PROVIDER_FACTORY_NOT_FOUND           = 2001;
    int PROPERTY_FILE_NOT_FOUND              = 2002;
    int CONSUMER_REGISTRY_FACTORY_NOT_FOUND  = 2003;
    int PERSISTENT_FACTORY_NOT_FOUND         = 2004;
    
    // WS Factory
    int WS_FACTORY_NOT_FOUND                 = 2005;
    
    
    /************ Error Codes Provider ************/
    
    // Description Handler
    int SAVE_SERVICEDESCRIPTION_FAILED       = 3000;
    int LOAD_SERVICEDESCRIPTION_FAILED       = 3001;
    int MISSING_DEFAULT_LOCALE               = 3002;
    int MISSING_DEFAULT_LANGUAGE             = 3003;
    int NO_PORTLETDESCRIPTIONS_FOUND         = 3004;
    int MISSING_PORTLET_DEFINITION           = 3005;
    int PORTLET_PORTLET_NOT_FOUND            = 3006;
    
    // Portlet Pool
    int GET_PORTLET_FAILED                   = 3020;
    
    // Portlet Invoker
    int SERVICE_CLASS_NOT_FOUND              = 3030;
    int INSTANTIATION_OF_SERVICE_FAILED      = 3031;
    
    // URL Composer
    int UNKNOWN_TOKEN_IN_TEMPLATE            = 3040;
    int SYNTAX_ERROR_IN_TEMPLATE             = 3041;
    
    
    /************ Error Codes Consumer ************/
    
    int CONSUMER_PROPERTY_FILE_NOT_FOUND     = 6000;
    int INSTANTIATION_OF_CONSUMER_ENV_FAILED = 6001;
    int COULD_NOT_ADD_REQUEST_PARAM          = 6002;
    int MISSING_SERVICE_DESC_PORT            = 6003;
    int INIT_OF_SERVICE_DESC_PORT_FAILED     = 6004;
    int INVALID_URL_OF_SERVICE_DESC_PORT     = 6005;
    int INIT_OF_REGISTRATION_PORT_FAILED     = 6006;
    int INVALID_URL_OF_REGISTRATION_PORT     = 6007;
    int INVALID_LINK_WITHIN_MARKUP           = 6008;
    int NO_VALID_URL_TYPE_PARAM_FOUND        = 6009;
    int INVALID_PORTLET_HANDLE               = 6010;
    int INVALID_SERVICE_DESCRIPTION          = 6011;
    int COULD_NOT_FOLLOW_REDIRECT            = 6012;
    int PRODUCER_DOES_NOT_EXIST              = 6013;
    int INIT_OF_PORTLET_MGMT_PORT_FAILED     = 6014;
    int INVALID_URL_OF_PORTLET_MGMT_PORT     = 6015;
    int MISSING_MARKUP_PORT                  = 6016;
    int INIT_OF_MARKUP_PORT_FAILED           = 6017;
    int INVALID_URL_OF_MARKUP_PORT           = 6018;
    int PORTLET_DESC_NOT_FOUND               = 6019;
    int USER_SESSION_NOT_FOUND               = 6020;
    int GROUP_SESSION_NOT_FOUND              = 6021;
    int PORTLET_SESSION_NOT_FOUND            = 6022;
    int WINDOWS_SESSION_NOT_FOUND            = 6023;
    
}
