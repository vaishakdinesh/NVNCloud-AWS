#!/bin/bash
StackName=$1
stackstatus=""
createStackStatus=""
createFlag=true
DomainName=$2
AccessKeyId=$3
SecretAccessKey=$4
MySqlClientPass=$5


if [ -z "$StackName" ]; then
  echo "No stack name provided. Script exiting.."
  exit 1
fi
if [ -z "$DomainName" ]; then
  echo "No domain name provided. Script exiting.."
  exit 1
fi
BucketName=web-app.$DomainName.me
DomainName=csye6225-spring2018-$DomainName.me

echo "Starting $StackName autoscaling setup"

echo "Starting to create the stack......"

createStackStatus=`aws cloudformation create-stack --stack-name $StackName \
  --template-body file://csye6225-aws-cf-autoscaling.json \
  --parameters ParameterKey=DBName,ParameterValue=csye6225 \
  ParameterKey=DBUser,ParameterValue=csye6225master \
  ParameterKey=DBPassword,ParameterValue=csye6225password \
  ParameterKey=EC2ImageId,ParameterValue=ami-66506c1c \
  ParameterKey=EC2InstanceType,ParameterValue=t2.micro \
  ParameterKey=EbsDeviceName,ParameterValue=/dev/sda1 \
  ParameterKey=EbsVolumeType,ParameterValue=gp2 \
  ParameterKey=EbsVolumeSize,ParameterValue=16 \
  ParameterKey=KeyPairName,ParameterValue=keypair \
  ParameterKey=AccessKeyId,ParameterValue=$AccessKeyId \
  ParameterKey=SecretAccessKey,ParameterValue=$SecretAccessKey \
  ParameterKey=bucketName,ParameterValue=$BucketName \
  ParameterKey=MySqlClientPass,ParameterValue=$MySqlClientPass \
  ParameterKey=HostedZoneResource,ParameterValue=$DomainName \
  --capabilities CAPABILITY_NAMED_IAM`

  if [ -z "$createStackStatus" ]; then
    echo "Failed to create stack"
    exit 1
  fi

  until [ "$stackstatus" = "CREATE_COMPLETE" ]; do
    echo "Adding resources to the stack......"

    #ADD function to check resources
    myresources(){
      resourceStatus=`aws cloudformation describe-stack-events --stack-name $StackName --query 'StackEvents[?(ResourceType=='$@' && ResourceStatus==\`CREATE_FAILED\`)][ResourceStatus]' --output text`
      if [ "$resourceStatus" = "CREATE_FAILED" ]; then
        createFlag=false
        echo "$@ creation failed! "
        aws cloudformation describe-stack-events --stack-name $StackName --query 'StackEvents[?(ResourceType=='$@' && ResourceStatus==`CREATE_FAILED`)]'
        echo "deleting stack..... "
        bash ./csye6225-aws-cf-terminate-autoscaling-stack.sh $StackName
        break
      fi
    }

    # myresources '`AWS::EC2::Instance`'
    # myresources '`AWS::DynamoDB::Table`'
    # myresources '`AWS::S3::Bucket`'
    # myresources '`AWS::RDS::DBInstance`'

    stackstatus=`aws cloudformation describe-stacks --stack-name $StackName --query 'Stacks[*][StackStatus]' --output text`
    sleep 60
  done

  if [ "$createFlag" = true ]; then
    echo "Stack resources created successfully"
    aws cloudformation list-stack-resources --stack-name $StackName
  fi
  exit 0
