#!/bin/bash

if [ $# -lt 2  ];then
  echo "repo name and checking branch name must be defined."
  echo "example(stiku-tmp is repo name and develop is checking branch name):"
  echo "  $0 stiku-tmp develop"
  exit 1
fi

source `dirname $0`/common.sh

#update mirror repo 
updateMirrorRepo $WORK_BASE_DIR $MIRROR_URL $REPO_URL $REPO_NAME

WK_BRANCH=$2

cd $WORK_BASE_DIR

WK_DIR=${REPO_NAME}_${WK_BRANCH}
if [ ! -d "$WK_DIR" ];then
  exit 0
fi

if [ -f $WK_DIR/.git/CFLC_BRANCH ];then
  CFCL_BCH=`cat $WK_DIR/.git/CFLC_BRANCH`
  if [ ! -z "$CFCL_BCH" ];then
    if [ ! -d "$CFCL_BCH" ];then
      git clone $REPO_URL/$MERGE_REPO_NAME $CFCL_BCH
      git checkout $CFCL_BCH
    fi
    
    cd $WORK_BASE_DIR/$WK_DIR
    if [ -f .git/TREE_CFLC_FILE ];then
      for F in `cat .git/TREE_CFLC_FILE`
        do
          git rm -f $F
        done
    fi

    cd $WORK_BASE_DIR/$CFCL_BCH
    git fetch
    git rebase origin/$CFCL_BCH
    cp -r * $WORK_BASE_DIR/$WK_DIR
    git checkout master
    git branch -D $CFCL_BCH
    git push origin :$CFCL_BCH


    cd $WORK_BASE_DIR/$WK_DIR
    if [ -f tree_conflict.txt ];then
      rm -f tree_conflict.txt
    fi
    git add .
    git commit -m "resolve conflict in branch $CFCL_BCH"
    git push $REPO_URL/$REPO_NAME.git $WK_BRANCH

    rm -f .git/CFLC_FILE
    rm -f .git/TREE_CFLC_FILE
    rm -f .git/CFLC_BRANCH
    rm -fr $WORK_BASE_DIR/$CFCL_BCH

  fi
fi

cd $WORK_BASE_DIR
if [ -f $WK_DIR/.git/LEFT_BRANCH ];then
  LEFT_BCH=`cat $WK_DIR/.git/LEFT_BRANCH`
  rm -f $WK_DIR/.git/LEFT_BRANCH
  if [ ! -z "$LEFT_BCH" ];then
    ./git_merge.sh $REPO_NAME $WK_BRANCH $LEFT_BCH 

    exit $?
  fi
  
fi

exit 0
