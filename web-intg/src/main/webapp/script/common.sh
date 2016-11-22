#!/bin/bash

WORK_BASE_DIR=/home/deployer/wstest
REPO_URL=gerrit@115.28.177.177:/home/gerrit/GerritResource
MERGE_REPO_NAME=merge-repo
CURR_TIME=`date +%Y%m%d%H%M%S`
CURR_DATE=`date +%Y%m%d`


REPO_NAME=$1

if [ ! -d "$WORK_BASE_DIR" ];then
 mkdir -p $WORK_BASE_DIR
fi






