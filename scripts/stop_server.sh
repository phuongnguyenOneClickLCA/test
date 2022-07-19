#!/bin/bash
echo "stop tomcat"
sudo /home/maintenance/killTomcat.sh
sudo chown -R yllapito:yllapito /home/maintenance/app.war