#!/bin/bash
InstanceName=$1
InstanceId=`aws ec2 run-instances --cli-input-json file://csye6225-cf-application.json --query Instances[*][InstanceId] --output text`
aws ec2 create-tags --resources $InstanceId --tags Key=Name,Value=$InstanceName

InstanceState=""

until [ "$InstanceState" = "running" ]; do
  InstanceState=`aws ec2 describe-instance-status --query 'InstanceStatuses[?InstanceId==\`'$InstanceId'\`][InstanceState][*][Name]' --output text`
  echo $InstanceState
  sleep 20
done

echo "$InstanceName ec2 instance is up & running!!!!"
