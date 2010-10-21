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
package org.apache.wsrp4j.commons.consumer.driver;

import java.util.Iterator;

import org.apache.wsrp4j.commons.consumer.interfaces.producer.Producer;
import org.apache.wsrp4j.commons.exception.WSRPException;

import org.apache.wsrp4j.commons.persistence.PersistentDataObject;
import org.apache.wsrp4j.commons.persistence.PersistentHandler;
import org.apache.wsrp4j.commons.util.StateChangedEvent;
import org.apache.wsrp4j.commons.util.StateChangedListener;
import org.apache.wsrp4j.commons.util.StateChangedService;

/**
 * A producer registry with persistence.
 * 
 * @version $Id: GenericPersistentProducerRegistryImpl.java 327497 2005-08-28
 *          21:35:05Z dlouzan $
 */
public class GenericPersistentProducerRegistryImpl extends
        GenericProducerRegistryImpl implements StateChangedListener {

    // persistence
    protected PersistentDataObject persistentDataObject;

    protected PersistentHandler persistentHandler;

    /**
     * Add a producer to the registry
     * 
     * @param producer
     *            The producer to add
     */
    public void addProducer(Producer producer) {
        if (producer != null) {

            super.addProducer(producer);

            try {

                store(producer);

            } catch (WSRPException e) {

                // cleanup on error
                super.removeProducer(producer.getID());

                // TODO: throw exception
            }

            if (producer instanceof StateChangedService) {
                ((StateChangedService) producer).addListener(this);
            }
        }
    }

    /**
     * Get the producer for the given URL
     * 
     * @param id
     *            The ID of the producer
     * 
     * @return The producer which had been mapped to this id or null if no
     *         producer was found with this id
     */
    public Producer removeProducer(String id) {
        if (id == null)
            return null;

        Producer producer = super.removeProducer(id);
        delete(producer);

        return producer;
    }

    public void removeAllProducers() {
        Iterator producers = getAllProducers();
        while (producers.hasNext()) {
            Producer producer = (Producer) producers.next();
            delete(producer);
        }

        super.removeAllProducers();
    }

    public void stateChanged(StateChangedEvent event) {

        try {

            Producer producer = (Producer) event.getSource();
            store(producer);

        } catch (Throwable t) {

            if (t instanceof ClassCastException) {

                // TODO: throw exception
            }

            if (t instanceof WSRPException) {

                // TODO: throw exception
            }
        }
    }

    /**
     * Restores all available producers from persistent file store
     */
    public void restore() throws WSRPException {

        super.removeAllProducers();

        try {

            persistentDataObject = persistentHandler
                    .restoreMultiple(persistentDataObject);

            Iterator iterator = persistentDataObject.getObjects();

            while (iterator.hasNext()) {

                Producer producer = (Producer) iterator.next();

                if (producer instanceof StateChangedService) {
                    ((StateChangedService) producer).addListener(this);
                }

                super.addProducer(producer);
            }
        } catch (WSRPException e) {

            // cleanup on error
            super.removeAllProducers();

            throw e;
        }
    }

    /**
     * Store a new producer to persistent store
     * 
     * @param producer
     * 
     * @throws WSRPException
     */
    public void store(Producer producer) throws WSRPException {

        if (producer != null) {

            // store new clone to persistent file
            persistentDataObject.clear();
            persistentDataObject.addObject(producer);
            persistentHandler.store(persistentDataObject);
        }
    }

    /**
     * Delete the given producer from persistent store
     * 
     * @param producer
     */
    private void delete(Producer producer) {

        if (producer != null) {

            // delete the persistentFileStore for this portlet
            persistentDataObject.clear();
            persistentDataObject.addObject(producer);
            persistentHandler.delete(persistentDataObject);
        }
    }

}
