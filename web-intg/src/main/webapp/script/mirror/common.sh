#!/bin/bash

WORK_BASE_DIR=/home/deployer/wstest
REPO_URL=gerrit@115.28.177.177:/home/gerrit/GerritResource
MIRROR_URL=$WORK_BASE_DIR/mirror
MERGE_REPO_NAME=merge-repo
CURR_TIME=`date +%Y%m%d%H%M%S`
CURR_DATE=`date +%Y%m%d`


REPO_NAME=$1

function updateMirrorRepo(){
  WORK_BASE_DIR=$1
  MIRROR_URL=$2
  REPO_URL=$3
  REPO_NAME=$4
  echo "WORK_BASE_DIR is $WORK_BASE_DIR, MIRROR_URL is $MIRROR_URL, REPO_URL is $REPO_URL, REPO_NAME is $REPO_NAME"

  if [ ! -d "$WORK_BASE_DIR" ];then
    mkdir -p $WORK_BASE_DIR
  fi

  if [ ! -d "$MIRROR_URL" ];then
   mkdir -p $MIRROR_URL
  fi

  if [ ! -d "$MIRROR_URL/$REPO_NAME.git" ];then
    cd $MIRROR_URL
    git clone --mirror $REPO_URL/$REPO_NAME.git
  else
    cd $MIRROR_URL/$REPO_NAME.git
    git remote update
  fi
}
