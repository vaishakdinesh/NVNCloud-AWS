
StackName=$1
echo "Terminating $StackName network setup"

aws cloudformation delete-stack --stack-name $StackName


echo "$StackName network terminated!!!!"
#aws cloudformation describe-stack-events --stack-name stackapp --query 'StackEvents[?ResourceStatus==`DELETE_COMPLETE`].[ResourceType,ResourceStatus,Timestamp]'
