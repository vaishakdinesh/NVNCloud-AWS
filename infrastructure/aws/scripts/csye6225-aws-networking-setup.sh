#!/bin/bash
stackname=$1
echo "Creating VPC"

vpcId=`aws ec2 create-vpc --cidr-block 10.0.0.0/16 --query 'Vpc.VpcId' --output text`
echo "created VPC with Id = $vpcId"

aws ec2 create-tags --resources $vpcId --tags Key="Name",Value="$stackname-csye6225-vpc"

echo "Creating and attaching the internet gateways "
internetGatewayId=`aws ec2 create-internet-gateway --query 'InternetGateway.InternetGatewayId' --output text`
aws ec2 create-tags --resources $internetGatewayId --tags Key="Name",Value="$stackname-csye6225-internetGatewayId"
aws ec2 attach-internet-gateway --internet-gateway-id $internetGatewayId --vpc-id $vpcId

echo "Creating RouteTables and Routes"
routeTableId=`aws ec2 create-route-table --vpc-id $vpcId --query 'RouteTable.RouteTableId' --output text`
aws ec2 create-tags --resources $routeTableId --tags Key="Name",Value="$stackname-csye6225-public-route-table"
aws ec2 create-route --route-table-id $routeTableId --destination-cidr-block 0.0.0.0/0 --gateway-id $internetGatewayId

exit 0
