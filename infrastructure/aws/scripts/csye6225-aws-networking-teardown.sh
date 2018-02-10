#!/bin/bash

stackname=$1

vpcid=`aws ec2 describe-vpcs --filters "Name=tag:Name,Values='$stackname-csye6225-vpc'" --query Vpcs[*].VpcId --output text`
echo "Vpcid: $vpcid"

internetgatewayid=`aws ec2 describe-internet-gateways --filters "Name=tag:Name,Values='$stackname-csye6225-internetGatewayId'" --query InternetGateways[*].InternetGatewayId --output text`
aws ec2 detach-internet-gateway --internet-gateway-id $internetgatewayid --vpc-id $vpcid
echo "Internet gateway detached"
aws ec2 delete-internet-gateway --internet-gateway-id $internetgatewayid
echo "Internet gateway deleted"

routetableid=`aws ec2 describe-route-tables --filters "Name=tag:Name,Values='$stackname-csye6225-public-route-table'" --query RouteTables[*].RouteTableId --output text`
aws ec2 delete-route-table --route-table-id $routetableid
echo "Route table deleted"
aws ec2 delete-vpc --vpc-id $vpcid
echo "Vcp $stackname -- $vpcid deleted"
