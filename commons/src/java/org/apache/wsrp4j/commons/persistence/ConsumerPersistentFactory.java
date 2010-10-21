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
package org.apache.wsrp4j.commons.persistence;

import org.apache.wsrp4j.commons.exception.WSRPException;

/**
 * This class is the interface definition for the client persistence
 * support factory class. E.g. a consumer uses client persistent 
 * support. 
 *
 * @version $Id: ConsumerPersistentFactory.java 381851 2006-03-01 00:42:29Z jmacna $
 */
public interface ConsumerPersistentFactory extends PersistentFactory {

    /**
     * Returns a PageList
     *
     * @return PersistentDataObject
     * @throws WSRPException
     */
    PersistentDataObject getPageList() throws WSRPException;


     /**
     * Returns the ConsumerPortletContextList
     * 
     * @return PersistentDataObject
     * @throws WSRPException
     */
    PersistentDataObject getConsumerPortletContextList() 
    throws WSRPException;


	/**
	 * Returns the PortletList
	 * 
	 * @return PersistentDataObject
	 */
	PersistentDataObject getPortletList() throws WSRPException;


	/**
	 * Returns the ProducerList
	 * 
	 * @return PersistentDataObject
	 */
	PersistentDataObject getProducerList() throws WSRPException;


	/**
	 * Returns the UserList
	 * 
	 * @return PersistentDataObject
	 */
	PersistentDataObject getUserList() throws WSRPException;
}
