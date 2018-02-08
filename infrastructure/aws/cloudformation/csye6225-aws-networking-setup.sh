#!/bin/bash

StackName=$1
echo "Starting $StackName network setup"

aws cloudformation create-stack --stack-name $StackName --template-body file://csye6225-cf-networking.json

#Comment everything below jsut to create the stack
#need to make this block run in every n secs
#timer block start
stackstatus= ``aws cloudformation describe-stacks --stack-name $StackName --query 'Stacks[*][StackStatus]' --output text``
#run just the AWS command within `` `` in the terminal after a sample stack is created to know the out put.
#if --output text parameter is removed u will get the output as list.
echo "$stackstatus"

#this if condition is not working its not comparing the values
if [ "$stackstatus" == "CREATE_COMPLETE" ]; then
  echo "if output--- $stackstatus"
  echo "$StackName network setup done!!!!"

#this else block is to get all the stack events and the print if any resourse failed.
#try runing the command in the terminal(better without --output text)
# else
#   resource = ``aws cloudformation describe-stack-events --stack-name $StackName --query 'StackEvents[*][ResourceStatus,ResourceType]' --output text``
#   if [="CREATE_FAILED"]; then
#     echo "$resource creation failed! "
#   fi

fi
#timer block end

#usefull links
#https://docs.aws.amazon.com/cli/latest/userguide/controlling-output.html
#https://docs.aws.amazon.com/cli/latest/reference/cloudformation/index.html#cli-aws-cloudformation


#aws cloudformation describe-stack-events --stack-name stackapp --query 'StackEvents[?ResourceStatus==`CREATE_COMPLETE`].[ResourceType,ResourceStatus,Timestamp]'
