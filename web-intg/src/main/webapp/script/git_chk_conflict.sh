#!/bin/bash

if [ $# -lt 2  ];then
  echo "repo name and checking branch name must be defined."
  echo "example(stiku-tmp is repo name and develop is checking branch name):"
  echo "  $0 stiku-tmp develop"
  exit 1
fi

source `dirname $0`/common.sh

WK_BRANCH=$2

cd $WORK_BASE_DIR

WK_DIR=${REPO_NAME}_${WK_BRANCH}
if [ ! -d "$WK_DIR" ];then
  exit 0
fi

cd $WK_DIR
if [ -f .git/MERGE_MSG ];then
  exit 1
fi

exit 0
