#!/bin/bash

# update the permission and ownership of WAR file in the tomcat webapps directory
sudo service tomcat8 stop

cd /var/lib
sudo chown tomcat8:tomcat8 tomcat8
sudo chmod u+wrx tomcat8
sudo /sbin/iptables -t nat -I PREROUTING -p tcp --dport 80 -j REDIRECT --to-port 8080
