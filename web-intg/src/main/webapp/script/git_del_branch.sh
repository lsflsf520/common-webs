#!/bin/bash

if [ $# -lt 2  ];then
  echo "repo name and new branch name must be defined."
  echo "example(stiku-tmp is repo name and busi_1、busi_2 need delete branches):"
  echo "  $0 stiku-tmp busi_1,busi_2"
  exit 1
fi

source `dirname $0`/common.sh

cd $WORK_BASE_DIR

WK_DIR=${REPO_NAME}

#enter local repo directory
cd $WORK_BASE_DIR/$WK_DIR
if [ ! -d "$WK_DIR" ];then
  git clone $REPO_URL/$REPO_NAME $WK_DIR
else 
  git fetch 
  git checkout master
  git rebase origin/master
fi

BCH_S=${2//,/ }
for BCH in $BCH_S
  do
    EXIST=`git branch | grep  -E "^* $BCH$|^ $BCH$"`
    if [ ! -z "$EXIST" ];then
      git branch -D $BCH
    fi

    REMOTE_BRANCH=`git branch -a | grep /$BCH$`
    if [ ! -z "$REMOTE_BRANCH" ];then
      echo "git push origin :$BCH"
      git push origin :$BCH
    fi

    #if [ -d "$WORK_BASE_DIR/${REPO_NAME}_${BCH}" ];then
    #  rm -fr $WORK_BASE_DIR/${REPO_NAME}_${BCH}
    #fi

  done

echo "$BCH_S are deleted success."
