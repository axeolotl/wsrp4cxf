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


/**
 * This class defines the interface for persistent information needed
 * to store and retrieve PersistentDataObjects with castor XML support.
 *
 * @version $Id: PersistentInformationXML.java 374672 2006-02-03 14:10:58Z cziegeler $
 */
public interface PersistentInformationXML extends PersistentInformation {
    
    /**
     * Set the Store directory for the persistent XML files
     *
     * @param storeDirectory String name of the store
     */
    void setStoreDirectory(String storeDirectory);
    
    /**
     * Returns the directory for the persistent XML files
     *
     * @return String nanme of the store
     */
    String getStoreDirectory();
    
    /**
     * Set the Castor XML mapping file name, fully qualified
     *
     * @param mappingFileName String fully qualified filename
     */
    void setMappingFileName(String mappingFileName);
    
    /**
     * Returns the XML mapping file name, fully qualified
     *
     * @return String fully qualified filename
     */
    String getMappingFileName();
    
    /**
     * Set the file name stub for persistent XML files. The name contains the
     * store directory followed by a file separator and the class name of the
     * object to be restored.
     *
     * @param stub String file name stub
     */
    void setFilenameStub(String stub);
    
    /**
     * Returns the file name stub for persistent XML files. @see setFilenameStub
     *
     * @return String file name stub
     */
    String getFilenameStub();
    
    /**
     * Returns a fully qualified file name for a persistent XML file.
     *
     * @return String file name
     */
    String getFilename();
    
    /**
     *  Set the fully qualified file name for a persistent XML file.
     *
     * @param filename String file name
     */
    void setFilename(String filename);
    
    
    /**
     * Updates the file name, enhanced by a string token, like a handle to
     * idportlet a unique persistent XML file. If a groupID is set, the
     * groupID is used instead of the token to build the filename.
     *
     * @param token String token, like a handle
     */
    void updateFileName(String token);
    
    /**
     * Returns the file extension used for persistent XML files
     */
    String getExtension();
    
    /**
     * Set the file extension for persistent XML files.
     *
     * @param extension String file extension
     */
    void setExtension(String extension);
    
    /**
     * Set the Separator, to be used in a fully qualified file name.
     *
     * @return String Separator character
     */
    String getSeparator();
    
    /**
     * Set the separator character. (e.g. '@')
     *
     * @param separator String Separator character
     */
    void setSeparator(String separator);
    
    /**
     * @return this object as String
     */
    String toString();
    
}
