#!/bin/bash
sudo service tomcat8 start
sudo systemctl start awslogs.service
sudo systemctl stop awslogs.service
sudo systemctl restart awslogs.service
