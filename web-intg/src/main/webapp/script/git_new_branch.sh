#!/bin/bash

if [ $# -lt 2  ];then
  echo "repo name and new branch name must be defined."
  echo "example(stiku-tmp is repo name and busi_1、busi_2 are new branch name):"
  echo "  $0 stiku-tmp busi_1"
  echo "  $0 stiku-tmp busi_2 -r (-r mean if busi_2 exists ,then delete it.)"
  exit 1
fi

source `dirname $0`/common.sh

BRANCH_NAME=$2

cd $WORK_BASE_DIR

WK_DIR=${REPO_NAME}
if [ ! -d "$WK_DIR" ];then
  git clone $REPO_URL/$REPO_NAME $WK_DIR
fi

#enter local repo directory
cd $WORK_BASE_DIR/$WK_DIR

git fetch 
git checkout master
git rebase origin/master

REMOTE_BRANCH=`git branch -a | grep /$BRANCH_NAME$`
if [ ! -z "$REMOTE_BRANCH" ];then
  if [ "-r" == "$3" ];then
    EXIST=`git branch | grep -E "^* $BRANCH_NAME$|^ $BRANCH_NAME$"`
    if [ ! -z "$EXIST" ];then
      git branch -D $BRANCH_NAME
    fi

    git push origin :$BRANCH_NAME
    echo "remote branch $REMOTE_BRANCH has deleted."
  
  else
    echo "exist remote branch $REMOTE_BRANCH, then exit."
    exit 
  fi
fi

EXIST=`git branch | grep  -E "^* $BRANCH_NAME$|^ $BRANCH_NAME$"`
if [ ! -z "$EXIST" ];then
  git branch -D $BRANCH_NAME
fi

git branch $BRANCH_NAME origin/master
git push origin $BRANCH_NAME

echo "$BRANCH_NAME created success."

