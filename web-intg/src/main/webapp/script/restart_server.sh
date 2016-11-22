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

SLEEP_SECOND=`cat $SERVER_FILE | grep ^sleep= | awk -F"=" '{print $2}'`
SERVER=`cat $SERVER_FILE | grep ^servers= | awk -F"=" '{print $2}'`
SERVERS=${SERVER//,/ }

if [ ! -z "$SERVERS" ];then
  for SERVER_IP in $SERVERS
    do
      ssh deployer@$SERVER_IP  "/home/deployer/scripts/restart.sh ${PROJ_NAME} $SLEEP_SECOND"
    done
  echo "restart success."
else
  echo "server ip not defined in $SERVER_FILE, then do nothing."
fi

