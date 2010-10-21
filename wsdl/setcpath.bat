echo off
rem  Copyright 1999-2004 The Apache Software Foundation
rem
rem  Licensed under the Apache License, Version 2.0 (the "License");
rem  you may not use this file except in compliance with the License.
rem  You may obtain a copy of the License at
rem
rem      http://www.apache.org/licenses/LICENSE-2.0
rem
rem  Unless required by applicable law or agreed to in writing, software
rem  distributed under the License is distributed on an "AS IS" BASIS,
rem  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
rem  See the License for the specific language governing permissions and
rem  limitations under the License.
rem
set CLASSPATH=.
set CLASSPATH=%CLASSPATH%;..\lib\axis-1.3.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis-jaxrpc-1.3.jar
set CLASSPATH=%CLASSPATH%;..\lib\axis-saaj-1.3.jar
set CLASSPATH=%CLASSPATH%;..\lib\commons-discovery-0.2.jar
set CLASSPATH=%CLASSPATH%;..\lib\consumer\commons-logging-1.0.4.jar
set CLASSPATH=%CLASSPATH%;..\lib\wsdl4j-1.5.1.jar
set CLASSPATH=%CLASSPATH%;..\lib\consumer\xml-apis-2.5.0.jar
set CLASSPATH=%CLASSPATH%;..\lib\consumer\xercesImpl-2.5.0.jar