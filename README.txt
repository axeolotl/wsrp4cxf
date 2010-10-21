# Copyright 2003-2005 The Apache Software Foundation.
# 
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#      http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.


Preliminary mavenized version of the repository. Based on Jetspeed2
mavenized build.

- Copy build.properties.sample to build.properties
- Edit build.properties to fit your environment
- Run Maven:
    $ maven <goal>

Goal description:

$ maven
or
$ maven build
    Build jars and wars

$ maven deploy-producer
    Deploys producer and testportlet

$ maven run-swingconsumer
    Run swing consumer

$ maven doc
    Build site documentation

$ maven all
    Build jars, wars and site, deploy producer

$ maven clean
    Remove all generated artifacts