@echo off
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
call .\setcpath.bat

echo running java org.apache.axis.wsdl.WSDL2Java         
java -DproxySet -DproxyHost=localhost -DproxyPort=80 org.apache.axis.wsdl.WSDL2Java -v -t -O -1 --server-side --deployScope "Application" -W -o .\gen "WSRP_Service.wsdl"                   
