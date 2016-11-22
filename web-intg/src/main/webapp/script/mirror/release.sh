#!/bin/bash

if [ $# -lt 2  ];then
  echo "environment name and project name must be defined."
  echo "example(web-passport and web-ms are project name, develop and test are environment name):"
  echo "  $0 web-passport develop"
  echo "  $0 web-ms test"
  exit 1
fi

if [ "$2" != "develop" -a "$2" != "test" -a "$2" != "pre" -a "$2" != "huidu" -a "$2" != "master" ];then
  echo "the third param should be the one of develop、test、pre、huidu、master"
  exit 2
fi

source `dirname $0`/common.sh

PROJ_NAME=$1
ENV_NAME=$2

SERVER_FILE=$WORK_BASE_DIR/project_config/${PROJ_NAME}_${ENV_NAME}
if [ ! -f "$SERVER_FILE" ];then
  SERVER_FILE=$WORK_BASE_DIR/project_config/${PROJ_NAME}
  if [ ! -f "$SERVER_FILE" ];then
    echo "neither '$SERVER_FILE' nor $WORK_BASE_DIR/project_config/${PROJ_NAME}_${ENV_NAME} defined, please defined one of it."
    exit 4
  fi
fi

REPO_NAME=`cat $SERVER_FILE | grep ^repo= | awk -F"=" '{print $2}'`
REL_POM_PATH=`cat $SERVER_FILE | grep ^pom= | awk -F"=" '{print $2}'`
SLEEP_SECOND=`cat $SERVER_FILE | grep ^sleep= | awk -F"=" '{print $2}'`
SERVER=`cat $SERVER_FILE | grep ^servers= | awk -F"=" '{print $2}'`
MVN_CMD=`cat $SERVER_FILE | grep ^cmd= | awk -F"=" '{print $2}'`
PARENT_POM=`cat $SERVER_FILE | grep ^parent_pom= | awk -F"=" '{print $2}'`
SERVERS=${SERVER//,/ }

#update mirror repo 
updateMirrorRepo $WORK_BASE_DIR $MIRROR_URL $REPO_URL $REPO_NAME

REPO_PATH=$WORK_BASE_DIR/${REPO_NAME}_${ENV_NAME}
POM_PATH=$REPO_PATH/$REL_POM_PATH
PARENT_POM_PATH=$REPO_PATH/$PARENT_POM
if [ ! -d "$REPO_PATH" ];then
  git clone $MIRROR_URL/$REPO_NAME.git $REPO_PATH
  cd $REPO_PATH

  REMOTE_BRANCH=`git branch -a | grep /$ENV_NAME$`
  if [ ! -z "$REMOTE_BRANCH" ];then
    git branch $ENV_NAME origin/master
    git push $REPO_URL/$REPO_NAME.git $ENV_NAME  
  fi
  git checkout $ENV_NAME
else
  cd $REPO_PATH
  LOCAL_EXIST=`git branch | grep -E "^* ${ENV_NAME}$|^ ${ENV_NAME}$"`
  if [ ! -z "$LOCAL_EXIST" ];then
    if [ "test" == "$ENV_NAME" -o "pre" == "$ENV_NAME" ];then
      git reset --hard HEAD
    fi
    
    if [ "master" != "$ENV_NAME" ];then 
      git checkout master
      git branch -D $ENV_NAME
    fi
  fi
  git fetch
  if [ "master" != "$ENV_NAME" ];then
    git checkout $ENV_NAME
  else
    git rebase origin/master
  fi
fi

if [ ! -f "$POM_PATH" ];then
  echo "$POM_PATH does not exist."
  exit 1
fi


if [ "test" == "$ENV_NAME" -o "pre" == "$ENV_NAME" ];then
  if [ ! -f "$PARENT_POM_PATH" ];then
    echo "parent_pom has not defined in $SERVER_FILE, then use $POM_PATH as parent pom."
    PARENT_POM_PATH=$POM_PATH
  fi
  echo "$WORK_BASE_DIR/pre_build.sh $PARENT_POM_PATH $ENV_NAME"
  $WORK_BASE_DIR/pre_build.sh $PARENT_POM_PATH $ENV_NAME
fi

mvn clean $MVN_CMD -Dmaven.test.skip=true -f $POM_PATH
if [ $? != 0 ];then
  echo "maven build failure."
  exit 5
fi

if [ ! -z "$SERVERS" ];then
  WS_DIR=`dirname $POM_PATH`
  WAR_PATH=`ls $WS_DIR/target/${PROJ_NAME}*war`
  if [ ! -f "$WAR_PATH" ];then
    echo "compile failure."
    exit 3
  fi

  for SERVER_IP in $SERVERS
    do
      scp $WAR_PATH deployer@$SERVER_IP:/tmp/${PROJ_NAME}.war
      ssh deployer@$SERVER_IP  "/home/deployer/scripts/restart.sh ${PROJ_NAME} $SLEEP_SECOND"
    done
fi

echo "release success."
