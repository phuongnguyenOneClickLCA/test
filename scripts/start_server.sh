#!/bin/bash
echo "deploying"
sudo chown -R yllapito:yllapito /home/maintenance/app.war
/home/maintenance/JenkinsDeployOptimi.sh