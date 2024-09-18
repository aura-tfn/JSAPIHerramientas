#!/bin/bash
cd lib
mvn install:install-file -Dfile=cgjsapi.jar -DgroupId=com.cloudsgarden -DartifactId=cgjsapi -Dversion=1.70 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=cgjsapi170_x64.dll -DgroupId=com.cloudsgarden -DartifactId=cgjsapi -Dversion=1.70 -Dpackaging=dll -DgeneratePom=true

cd ..
