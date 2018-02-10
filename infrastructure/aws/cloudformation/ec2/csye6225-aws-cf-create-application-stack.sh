#!/bin/bash
InstanceName=$1
InstanceId=`aws ec2 run-instances --cli-input-json file://csye6225-cf-application.json --query Instances[*][InstanceId] --output text`
echo "name $InstanceId"


aws ec2 create-tags --resources $InstanceId --tags Key=Name,Value=$InstanceName


#aws ec2 modify-instance-attribute --instance-id $InstanceId --tags "[{\"Key\":\"Name\",\"Value\":\`'$InstanceName'\`'}]"
