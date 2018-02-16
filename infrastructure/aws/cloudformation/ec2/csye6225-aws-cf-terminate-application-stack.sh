#!/bin/bash
StackName=$1
if [ -z "$StackName" ]; then
  echo "ERROR: Stackname expected....."
  exit 1
fi

echo "Terminating $StackName network setup"
aws cloudformation delete-stack --stack-name $StackName

stackid=`aws cloudformation describe-stacks --stack-name $StackName --query 'Stacks[*][StackId]' --output text`
stackstatus=""
if [ -z "$stackid" ]; then
  exit 1
fi
until [ "$stackstatus" = 'DELETE_COMPLETE' ]; do
  stackstatus=`aws cloudformation list-stacks --query 'StackSummaries[?StackId==\`'$stackid'\`][StackStatus]' --output text`

done
if [ "$stackstatus" = 'DELETE_COMPLETE' ]; then
  echo "$StackName terminated sucessfully!!"
fi

# Assignment 4
# ec2name=$1
# instanceid=`aws ec2 describe-instances --filters "Name=tag:Name,Values='$ec2name'" --query Reservations[*].Instances[*].InstanceId --output text`
# echo "instance-id: $instanceid"
# aws ec2 modify-instance-attribute --instance-id $instanceid --block-device-mappings "[{\"DeviceName\": \"/dev/sda1\",\"Ebs\":{\"DeleteOnTermination\":true}}]"
# echo "DeleteOnTermination set to TRUE"
# aws ec2 terminate-instances --instance-ids $instanceid
#
# echo "ec2 instance $ec2name($instanceid) terminated"
