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
package org.apache.wsrp4j.commons.util.impl;

import java.net.InetAddress;
import java.util.Random;

import org.apache.wsrp4j.commons.util.HandleGenerator;

/**
 * This class implements the HandleGenerator interface.
 * 
 * @see     HandleGenerator 
 * @version $Id: HandleGeneratorImpl.java 374672 2006-02-03 14:10:58Z cziegeler $
 */
public class HandleGeneratorImpl implements HandleGenerator {

    private static int counter = 0;

    /**
     * Private constructor of HandleGeneratorImpl.
     */
    private HandleGeneratorImpl() {
    }

    /**
     * Returns an instance of HandleGeneratorImpl.
     *
     * @return String representing the new handle.
     */
    public static HandleGeneratorImpl create() {
        return new HandleGeneratorImpl();
    }

    /**
     * Generates a new handle and returns it as a String.
     *
     * @return String representing the new handle.
     */
    synchronized public String generateHandle() {

        String host = null;
        try {

            host = InetAddress.getLocalHost().getHostAddress().toString();

        } catch (Exception e) {

            host = Double.toString(
                    new Random(new Random().nextLong()).nextDouble());

        }

        StringBuffer handle = new StringBuffer(host);
        handle.append("_");
        handle.append(Long.toString(System.currentTimeMillis()));
        handle.append("_");
        handle.append(Integer.toString(counter++));

        return handle.toString();
    }

}
