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
package org.apache.wsrp4j.persistence.xml;

import org.apache.wsrp4j.commons.persistence.PersistentInformation;
import org.apache.wsrp4j.commons.persistence.PersistentInformationProvider;
import org.apache.wsrp4j.persistence.xml.driver.ConsumerPortletContextList;

import org.apache.wsrp4j.persistence.xml.driver.PageList;
import org.apache.wsrp4j.persistence.xml.driver.PortletList;
import org.apache.wsrp4j.persistence.xml.driver.ProducerList;
import org.apache.wsrp4j.persistence.xml.driver.UserList;


/**
 * Contains the persistent file store information for the castor
 * xml file support used by client applications e.g. consumers.
 *
 * @version $Id: ConsumerPersistentInformationProvider.java 381851 2006-03-01 00:42:29Z jmacna $
 */
public interface ConsumerPersistentInformationProvider 
    extends PersistentInformationProvider {
    
    /**
     * Returns the persistent file information for the Portlet
     *
     * @param portletList
     *
     * @return PersistentInformation
     */
    PersistentInformation getPersistentInformation(
            PortletList portletList);
    
    /**
     * Returns the persistent file information for the Page
     *
     * @param pageList
     *
     * @return PersistentInformation
     */
    PersistentInformation getPersistentInformation(PageList pageList);
    
    /**
     * Returns the persistent file information for the User
     *
     * @param userList
     *
     * @return PersistentInformation
     */
    PersistentInformation getPersistentInformation(UserList userList);
    
    /**
     * Returns the persistent file information for the Producer
     *
     * @param producerList
     *
     * @return PersistentInformation
     */
    PersistentInformation getPersistentInformation(
            ProducerList producerList);
    
    /**
     * Returns the persistent file information for the ConsumerPortletContext
     *
     * @param consumerPortletContextList ConsumerPortletContextList
     *
     * @return PersistentInformation
     */
    PersistentInformation getPersistentInformation(
            ConsumerPortletContextList consumerPortletContextList);
    
}
