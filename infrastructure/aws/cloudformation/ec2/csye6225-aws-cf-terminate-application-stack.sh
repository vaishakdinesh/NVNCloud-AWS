#!/bin/bash

ec2name=$1
instanceid=`aws ec2 describe-instances --filters "Name=tag:Name,Values='$ec2name'" --query Reservations[*].Instances[*].InstanceId --output text`
echo "instance-id: $instanceid"
aws ec2 modify-instance-attribute --instance-id $instanceid --block-device-mappings "[{\"DeviceName\": \"/dev/sda1\",\"Ebs\":{\"DeleteOnTermination\":true}}]"
echo "DeleteOnTermination set to TRUE"
aws ec2 terminate-instances --instance-ids $instanceid

echo "ec2 instance $ec2name($instanceid) terminated"
