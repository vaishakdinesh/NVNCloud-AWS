#!/bin/bash
StackName=$1
stackstatus=""
createStackStatus=""
createFlag=true
DomainName=$2

if [ -z "$StackName" ]; then
  echo "No stack name provided. Script exiting.."
  exit 1
fi
if [ -z "$DomainName" ]; then
  echo "No domain name provided. Script exiting.."
  exit 1
fi
#DomainName=code-deploy.$DomainName.me

echo "Starting $StackName autoscaling setup"

echo "Starting to create the stack......"
