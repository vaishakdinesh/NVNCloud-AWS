
StackName=$1
echo "Terminating $StackName network setup"

aws cloudformation delete-stack --stack-name $StackName
