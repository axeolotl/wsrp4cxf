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

import org.apache.wsrp4j.commons.util.HandleGenerator;
import org.apache.wsrp4j.commons.util.HandleGeneratorFactory;

/**
 * @version $Id: HandleGeneratorFactoryImpl.java 374648 2006-02-03 12:06:44Z cziegeler $
 */
public class HandleGeneratorFactoryImpl implements HandleGeneratorFactory {

    private static HandleGeneratorImpl generator;

    /**
     * Returns an new handle and returns it as a String.
     *
     * @return String representing the new handle.
     */
    public HandleGenerator getHandleGenerator() {
        if (generator == null) {
            generator = HandleGeneratorImpl.create();
        }

        return generator;
    }

}
