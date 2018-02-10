#!/bin/bash
InstanceName=$1
aws ec2 run-instances --cli-input-json file://csye6225-cf-application.json
awc ec2 create-tags --tags Key=InstanceName, Value=$InstanceName
