#!/bin/bash

StackName=$1
echo "Strating $StackName network setup"

aws cloudformation create-stack --stack-name $StackName --template-body file://csye6225-cf-networking.json
