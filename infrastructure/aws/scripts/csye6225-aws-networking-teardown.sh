#!/bin/bash

stackname=$1

vpcid=`aws ec2 describe-vpcs --filters "Name=tag:Name,Values='$stackname'" --query Vpcs[*].VpcId --output text`
