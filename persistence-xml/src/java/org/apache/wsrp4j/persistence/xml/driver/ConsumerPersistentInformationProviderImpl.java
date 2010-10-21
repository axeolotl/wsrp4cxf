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
import java.io.IOException;
import java.io.InputStream;

import org.apache.wsrp4j.commons.consumer.driver.ConsumerPortletContext;
import org.apache.wsrp4j.commons.consumer.driver.PageImpl;
import org.apache.wsrp4j.commons.consumer.driver.ProducerImpl;
import org.apache.wsrp4j.commons.consumer.driver.UserImpl;
import org.apache.wsrp4j.commons.consumer.driver.WSRPPortletImpl;

import org.apache.wsrp4j.commons.persistence.PersistentInformation;

import org.apache.wsrp4j.persistence.xml.PersistentInformationXML;
import org.apache.wsrp4j.persistence.xml.ConsumerPersistentInformationProvider;


/**
 * Consumer:
 *
 * This class holds the information for persistent file handling centrally. It
 * creates the persistent file store directory on demand and returns
 * PersistentFileInformation objects, filled with directory and file name
 * information, based on the request.
 *
 * The file system structure looks like:
 *
 * 1) the CONSUMER case:
 *
 * ./persistence/*.xml              (contains the mapping XML files)
 * ./persistence/portlets/*.xml     (contains object XML files)
 * ./persistence/pages/*.xml        (contains object XML files)
 * ./persistence/users/*.xml        (contains object XML files)
 * ./persistence/producers/*.xml    (contains object XML files)
 *
 * @version $Id: ConsumerPersistentInformationProviderImpl.java 381851 2006-03-01 00:42:29Z jmacna $
 */
public class ConsumerPersistentInformationProviderImpl 
        implements ConsumerPersistentInformationProvider {
    
    protected static final String CONSUMER_PERSISTENTSTORE = 
            "consumer.persistentstore";
    
    // sub directory for entites
    protected static final String PORTLETS = "portlets";
    
    // sub directory for users
    protected static final String USERS = "users";
    
    // sub directory for pages
    protected static final String PAGES = "pages";
    
    // sub directory for producers
    protected static final String PRODUCERS = "producers";
    
    // portlet mapping file name
    protected static final String PORTLET_MAPPING = "PortletMapping";
    
    // portlet mapping file name
    protected static final String PAGE_MAPPING = "PageMapping";
    
    // user mapping file name
    protected static final String USER_MAPPING = "UserMapping";
    
    // producer mapping file name
    protected static final String PRODUCER_MAPPING = "ProducerMapping";
    

    
    // File extension of the portlet persistent file store files
    protected static String FILE_EXTENSION = ".xml";
    
    // sub directory, sub-root of the persistent file store
    protected static String PERSISTENT_DIR = "persistence";
    
    // Separator in the filename of the portlet persistent file store
    protected static String SEPARATOR = "@";
    
    // This will be the root of all the persistent files
    // it should end up looking soemthing like this:
    // c:/Program Files/Tomcat 4.0/webapps/WEB-INF/persistence
    // depending on the installation
    protected  String root = null;
    /**
     * Static construction of the PersistentInformationProvider
     *
     */
    public static ConsumerPersistentInformationProvider create() {
    	ConsumerPersistentInformationProviderImpl cpip = new ConsumerPersistentInformationProviderImpl();
    	cpip.init();
        
        return cpip;
    }
    
    /**
     * protected constructor
     *
     * Creates the persistent file store directories dependent on the 
     * configured persistent store directory in the consumer properties file. 
     * If no persistent store directory was specified, the current runtime 
     * directory will be used as the root directory.
     */
    protected ConsumerPersistentInformationProviderImpl() {
        
    }
    
    protected void init() {
        File file = null;
        String StoreDir = null;
        
        try {
            
            InputStream is = this.getClass().getClassLoader().
                    getResourceAsStream("SwingConsumer.properties");
            java.util.Properties props = new java.util.Properties();
            if (is != null) {
                props.load(is);
                StoreDir = (String)props.get(CONSUMER_PERSISTENTSTORE);
                is.close();
            }
            
        } catch (IOException e) {
        }
        
        // store directroy in properties file found?
        if (StoreDir != null) {
            
            // check on trailing file separator in file name
            if (!StoreDir.endsWith(File.separator)) {
                StoreDir = StoreDir.concat(File.separator);
            }
            
            // build root directory plus persistent dir
            setRoot(StoreDir + PERSISTENT_DIR);
            
        } else {
            
            // No configuration or properties file found, use default value
            file = new File("");
            setRoot(file.getAbsolutePath());
            setRoot(getRoot() + File.separator + PERSISTENT_DIR);
        }
        
        // create persistent directory
        file = new File(getRoot());
        file.mkdir();
        
    }
    
    
    /**
     * Returns the store directory with the given sub directory
     *
     * @param  subDirectory String sub directory
     *
     * @return String store directory
     */
    protected String getStoreDirectory(String subDirectory) {
        
        return getRoot() + File.separator + subDirectory;
    }
    
    /**
     * Creates the store directory with the given sub directory name
     *
     * @param subDirectory String sub directory
     *
     */
    protected void makeStoreSubDir(String subDirectory) {
        
        // make sure the store directory exists
        File file = new File(getRoot() + File.separator + subDirectory);
        
        if (!file.exists()) {
            file.mkdir();
        }
    }
    
    /**
     * Compute the path to the mapping file from the root plus the name of the 
     * mapping file
     *
     * @param mappingBaseName String mapping file base name without extension
     *
     * @return String fully qualified mapping file name with extension
     */
    protected String getMappingFile(String mappingBaseName) {
        
        String mappingFile = new String(getRoot() + File.separator + 
                mappingBaseName + FILE_EXTENSION);
        return mappingFile;
    }
    
    
    /**
     * Returns the persistent file information for the Portlet
     *
     * @param portletList
     *
     * @return PersistentInformation
     */
    public PersistentInformation getPersistentInformation(
            PortletList portletList) {
        
        PersistentInformationXML persistentInfo = null;
        
        if (portletList != null) {
            persistentInfo = new PersistentInformationImpl();
            persistentInfo.setStoreDirectory(getStoreDirectory(PORTLETS));
            persistentInfo.setMappingFileName(getMappingFile(PORTLET_MAPPING));
            WSRPPortletImpl portlet = new WSRPPortletImpl();
            persistentInfo.setFilenameStub(portlet.getClass().getName());
            persistentInfo.setFilename(null);
            persistentInfo.setExtension(FILE_EXTENSION);
            persistentInfo.setSeparator(SEPARATOR);
            portletList.setPersistentInformation(persistentInfo);
            makeStoreSubDir(PORTLETS);
        }
        
        return persistentInfo;
    }
    
    /**
     * Returns the persistent file information for the Page
     *
     * @param pageList
     *
     * @return PersistentInformation
     */
    public PersistentInformation getPersistentInformation(PageList pageList) {
        
        PersistentInformationXML persistentInfo = null;
        
        if (pageList != null) {
            persistentInfo = new PersistentInformationImpl();
            persistentInfo.setStoreDirectory(getStoreDirectory(PAGES));
            persistentInfo.setMappingFileName(getMappingFile(PAGE_MAPPING));
            PageImpl page = new PageImpl();
            persistentInfo.setFilenameStub(page.getClass().getName());
            persistentInfo.setFilename(null);
            persistentInfo.setExtension(FILE_EXTENSION);
            persistentInfo.setSeparator(SEPARATOR);
            pageList.setPersistentInformation(persistentInfo);
            makeStoreSubDir(PAGES);
        }
        
        return persistentInfo;
    }
    
    /**
     * Returns the persistent file information for the user
     *
     * @param userList
     *
     * @return PersistentInformation
     */
    public PersistentInformation getPersistentInformation(UserList userList) {
        
        PersistentInformationXML persistentInfo = null;
        
        if (userList != null) {
            persistentInfo = new PersistentInformationImpl();
            persistentInfo.setStoreDirectory(getStoreDirectory(USERS));
            persistentInfo.setMappingFileName(getMappingFile(USER_MAPPING));
            UserImpl user = new UserImpl();
            persistentInfo.setFilenameStub(user.getClass().getName());
            persistentInfo.setFilename(null);
            persistentInfo.setExtension(FILE_EXTENSION);
            persistentInfo.setSeparator(SEPARATOR);
            userList.setPersistentInformation(persistentInfo);
            makeStoreSubDir(USERS);
        }
        
        return persistentInfo;
    }
    
    /**
     * Returns the persistent file information for the producers
     *
     * @param producerList
     *
     * @return PersistentInformation
     */
    public PersistentInformation getPersistentInformation(
            ProducerList producerList) {
        
        PersistentInformationXML persistentInfo = null;
        
        if (producerList != null) {
            persistentInfo = new PersistentInformationImpl();
            persistentInfo.setStoreDirectory(getStoreDirectory(PRODUCERS));
            persistentInfo.setMappingFileName(getMappingFile(PRODUCER_MAPPING));
            ProducerImpl producer = new ProducerImpl();
            persistentInfo.setFilenameStub(producer.getClass().getName());
            persistentInfo.setFilename(null);
            persistentInfo.setExtension(FILE_EXTENSION);
            persistentInfo.setSeparator(SEPARATOR);
            producerList.setPersistentInformation(persistentInfo);
            makeStoreSubDir(PRODUCERS);
        }
        
        return persistentInfo;
    }
    
    /**
     * Returns the persistent file information for the ConsumerPortletContext
     *
     * @param consumerPortletContextList ConsumerPortletContextList
     *
     * @return PersistentInformation
     */
    public PersistentInformation getPersistentInformation(
            ConsumerPortletContextList consumerPortletContextList) {
        
        PersistentInformationXML persistentInfo = null;
        
        if (consumerPortletContextList != null) {
            persistentInfo = new PersistentInformationImpl();
            persistentInfo.setStoreDirectory(getStoreDirectory(PORTLETS));
            persistentInfo.setMappingFileName(null);
            ConsumerPortletContext consumerPortletContext = 
                    new ConsumerPortletContext();
            persistentInfo.setFilenameStub(
                    consumerPortletContext.getClass().getName());
            persistentInfo.setFilename(null);
            persistentInfo.setExtension(FILE_EXTENSION);
            persistentInfo.setSeparator(SEPARATOR);
            consumerPortletContextList.setPersistentInformation(persistentInfo);
            makeStoreSubDir(PORTLETS);
        }
        
        return persistentInfo;
    }
    protected String getRoot() {
    	return root;
    }
    
    protected void setRoot(String newRoot) {
    	root = newRoot;
    }
}
