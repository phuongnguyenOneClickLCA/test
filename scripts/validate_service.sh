#!/bin/bash

# verify we can access our webpage successfully
ps -ef | grep java
/home/maintenance/restartSoftware.sh
curl -v --silent http://localhost:8080/app/ 2>&1 | grep "HTTP/1.1 200"
