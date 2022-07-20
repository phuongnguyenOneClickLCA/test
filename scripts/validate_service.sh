#!/bin/bash

# verify we can access our webpage successfully
pkill -9 -f tomcat
sleep 15
/opt/tomcat/bin/startup.sh
sleep 10
curl -v --silent http://localhost:8080/app/ 2>&1 | grep "HTTP/1.1 200"
