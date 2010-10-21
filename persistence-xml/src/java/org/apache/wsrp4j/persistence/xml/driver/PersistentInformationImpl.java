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
package org.apache.wsrp4j.persistence.xml.driver;

import java.io.File;

import org.apache.wsrp4j.persistence.xml.PersistentInformationXML;


/**
 * Contains the persistent file store information for the castor
 * xml file support. It's filled and returned initially by the class
 * PersistentInformationProvider and used by the PersistentHandler
 *
 * @version $Id: PersistentInformationImpl.java 374672 2006-02-03 14:10:58Z cziegeler $
 */
public class PersistentInformationImpl implements PersistentInformationXML {
    
    // mapping file name
    private String _mappingFileName;
    
    // store directory
    private String _storeDirectory;
    
    // stub of the filename
    private String _filenameStub;
    
    // filename
    private String _filename;
    
    // grouping identifier
    private String _groupID;
    
    // file extension
    private String _extension;
    
    // file separator
    private String _separator;
    
    /**
     * Constructor
     */
    public PersistentInformationImpl() {
        
    }
    
    /**
     * Set the Store directory for the persistent XML files
     *
     * @param storeDirectory String name of the store
     */
    public void setStoreDirectory(String storeDirectory) {
        _storeDirectory = storeDirectory;
    }
    
    /**
     * Returns the directory for the persistent XML files
     *
     * @return String nanme of the store
     *
     */
    public String getStoreDirectory() {
        return _storeDirectory;
    }
    
    /**
     * Set the Castor XML mapping file name, fully qualified
     *
     * @param mappingFileName String fully qualified filename
     *
     */
    public void setMappingFileName(String mappingFileName) {
        _mappingFileName = mappingFileName;
    }
    
    /**
     * Returns the XML mapping file name, fully qualified
     *
     * @return String fully qualified filename
     *
     */
    public String getMappingFileName() {
        return _mappingFileName;
    }
    
    /**
     * Set the file name stub for persistent XML files. The name contains the
     * store directory followed by a file separator and the class name of the
     * object to be restored.
     *
     * @param stub String file name stub
     *
     */
    public void setFilenameStub(String stub) {
        _filenameStub = stub;
    }
    
    /**
     * Returns the file name stub for persistent XML files. @see setFilenameStub
     *
     * @return String file name stub
     *
     */
    public String getFilenameStub() {
        return _filenameStub;
    }
    
    /**
     * Returns a fully qualified file name for a persistent XML file.
     *
     * @return String file name
     *
     */
    public String getFilename() {
        return _filename;
    }
    
    /**
     *  Set the fully qualified file name for a persistent XML file.
     *
     * @param filename String file name
     *
     */
    public void setFilename(String filename) {
        _filename = filename;
    }
    
    /**
     * Returns the group identifier, used in the filenames
     *
     * @return groupID as String
     *
     */
    public String getGroupID() {
        return _groupID;
    }
    
    /**
     *  Set the group identifier, used in the filenames.
     *
     * @param groupID as String
     *
     */
    public void setGroupID(String groupID) {
        _groupID = groupID;
    }
    
    /**
     * Updates the file name, enhanced by a string token, like a handle to
     * idportlet a unique persistent XML file. If a groupID is set, the
     * groupID is used instead of the token to build the filename.
     *
     * @param token String token, like a handle
     *
     */
    public void updateFileName(String token) {
        if (_groupID == null) {
            _filename = getStoreDirectory() + File.separator + 
                    getFilenameStub() + getSeparator() + token + getExtension();
            
        } else {
            _filename =
                    getStoreDirectory()
                    + File.separator
                    + getFilenameStub()
                    + getSeparator()
                    + getGroupID()
                    + getExtension();
        }
    }
    
    /**
     * Returns the file extension used for persistent XML files
     *
     * @return String file extension
     *
     */
    public String getExtension() {
        return _extension;
    }
    
    /**
     * Set the file extension for persistent XML files.
     *
     * @param extension String file extension
     *
     */
    public void setExtension(String extension) {
        _extension = extension;
    }
    
    /**
     * Set the Separator, to be used in a fully qualified file name.
     *
     * @return String Separator character
     *
     */
    public String getSeparator() {
        return _separator;
    }
    
    /**
     * Set the separator character. (e.g. '@')
     *
     * @param separator String Separator character
     *
     */
    public void setSeparator(String separator) {
        _separator = separator;
    }
    
    /**
     * @return object as String
     */
    public String toString() {
        
        String lineSeparator = System.getProperty("line.separator");
        
        return lineSeparator
                + "Mapping filename: "
                + _mappingFileName
                + lineSeparator
                + "Store directory : "
                + _storeDirectory
                + lineSeparator
                + "Filename stub   : "
                + _filenameStub
                + lineSeparator
                + "Filename        : "
                + _filename
                + lineSeparator
                + "Group ID        : "
                + _groupID
                + lineSeparator
                + "Extension       : "
                + _extension
                + lineSeparator
                + "Separator       : "
                + _separator
                + lineSeparator;
    }
    
}
