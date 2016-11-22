#!/bin/bash

if [ $# -lt 3  ];then
  echo "repo name、 branch name and merge branch name  must be defined."
  echo "example(stiku-tmp is repo name and develop is branch name and busi_1、busi_2 are merge branch name):"
  echo "  $0 stiku-tmp develop busi_1,busi_2"
  echo "  $0 stiku-tmp develop busi_1,busi_2 -f (-f means checkout branch develop again anyway)"
  exit 1
fi

source `dirname $0`/common.sh

#update mirror repo 
updateMirrorRepo $WORK_BASE_DIR $MIRROR_URL $REPO_URL $REPO_NAME

WK_BRANCH=$2
BRANCH_NAME=${3//,/ }

cd $WORK_BASE_DIR

WK_DIR=${REPO_NAME}_${WK_BRANCH}
if [ "-f" == "$4" -a "master" != "$WK_BRANCH" ];then
  if [ -d "$WK_DIR" ];then
    cd $WK_DIR

    git reset --hard HEAD
    git checkout master

    LOCAL_EXIST=`git branch | grep -E "^* $WK_BRANCH$|^ $WK_BRANCH$"`
    if [ ! -z "$LOCAL_EXIST" ];then
      git branch -D $WK_BRANCH
    fi
    git push $REPO_URL/$REPO_NAME.git :$WK_BRANCH
    git push origin :$WK_BRANCH
  else
    git clone $MIRROR_URL/$REPO_NAME.git $WK_DIR
    cd $WK_DIR 
    if [ ! -z `git branch -a | grep /$WK_BRANCH$` ];then
      git push $REPO_URL/$REPO_NAME.git :$WK_BRANCH
      git push origin :$WK_BRANCH
    fi
  fi

  #update mirror repo
  cd $MIRROR_URL/$REPO_NAME.git
  git remote update
fi

if [ ! -d "$WK_DIR" ];then
  git clone $MIRROR_URL/$REPO_NAME.git $WK_DIR
  cd $WORK_BASE_DIR/$WK_DIR
else
  cd $WORK_BASE_DIR/$WK_DIR
  git reset --hard HEAD 
  git fetch
  git checkout master
  git rebase origin/master
fi

LOCAL_EXIST=`git branch | grep -E "^* $WK_BRANCH$|^ $WK_BRANCH$"`
if [ "master" != "$WK_BRANCH" -a ! -z "$LOCAL_EXIST" ];then
   git branch -D $WK_BRANCH
fi

REMOTE_EXIST=`git branch -a | grep /$WK_BRANCH$`
if [ "master" != "$WK_BRANCH" -a -z "$REMOTE_EXIST" ];then
   git branch $WK_BRANCH origin/master
   git push $REPO_URL/$REPO_NAME.git $WK_BRANCH

   #update mirror repo
   cd $MIRROR_URL/$REPO_NAME.git
   git remote update
  
   cd $WORK_BASE_DIR/$WK_DIR
   git fetch
fi

git checkout $WK_BRANCH

for BRANCH in $BRANCH_NAME
 do
  echo "will merge origin/$BRANCH to $WK_BRANCH"

  MERGE_OUTPUT_FILE=merge_${WK_DIR}_cfcl_$CURR_TIME
  git merge origin/$BRANCH $WK_BRANCH > /tmp/$MERGE_OUTPUT_FILE

  CONFLICT_FILES=`cat /tmp/$MERGE_OUTPUT_FILE | grep "ERROR: content conflict in" | awk -F"ERROR: content conflict in " '{print $2}'`
  TREE_CFCL_FILES=`cat /tmp/$MERGE_OUTPUT_FILE | grep ": Not handling case" | awk -F": Not handling case" '{print $1}' | awk -F"ERROR:" '{print $2}'`
 
  rm -f /tmp/$MERGE_OUTPUT_FILE
  
  if [ ! -z "$CONFLICT_FILES" -o ! -z "$TREE_CFCL_FILES" ];then
    echo "$3" | awk -F"$BRANCH" '{print $2}' > .git/LEFT_BRANCH    

    MERGE_DIR=${REPO_NAME}_${WK_BRANCH}_${BRANCH}_CFCL
    echo "$MERGE_DIR" > .git/CFLC_BRANCH

    cd $WORK_BASE_DIR
    if [ -d "$MERGE_DIR" ];then
      rm -fr $MERGE_DIR
    fi

    git clone $REPO_URL/$MERGE_REPO_NAME.git $MERGE_DIR
    cd $MERGE_DIR

    if [ ! -z `git branch -a | grep /$MERGE_DIR$` ];then
      #delete the branch first
      git push origin :$MERGE_DIR
    fi
    git branch $MERGE_DIR
    git checkout $MERGE_DIR

    if [ ! -z "$CONFLICT_FILES" ];then
      echo $CONFLICT_FILES > $WORK_BASE_DIR/$WK_DIR/.git/CFLC_FILE
      for F in $CONFLICT_FILES
        do
          DIR_NAME=`dirname $F`
          if [ "." != "$DIR_NAME" ];then
            mkdir -p $DIR_NAME
          fi

          cp $WORK_BASE_DIR/$WK_DIR/$F $DIR_NAME
        done
    fi

    if [ ! -z "$TREE_CFCL_FILES" ];then
      echo $TREE_CFCL_FILES > $WORK_BASE_DIR/$WK_DIR/.git/TREE_CFLC_FILE
 
      echo "#current branch is $BRANCH" > tree_conflict.txt
      ALREADY_MERGED_BRANCH=`echo "$3" | awk -F"$BRANCH" '{print $1}'`
      echo "#branches '$ALREADY_MERGED_BRANCH' have already merged to $WK_BRANCH" >> tree_conflict.txt
      LEFT_BCH=`cat $WORK_BASE_DIR/$WK_DIR/.git/LEFT_BRANCH`
      echo "#branches '$LEFT_BCH' have not exec merge yet." >> tree_conflict.txt
      echo "#when you see this file, it means that tree conflict ocurred in following files." >> tree_conflict.txt
      echo "#you can discuss with the branches '$ALREADY_MERGED_BRANCH' developers to resolve the conflict." >> tree_conflict.txt
      echo "" >> tree_conflict.txt
      echo $TREE_CFCL_FILES >> tree_conflict.txt
  
    fi

    git add .
    git commit -m "conflict files in branch $BRANCH and $WK_BRANCH"
    git push origin $MERGE_DIR

    echo "busi_branch=$BRANCH, to_resolve_branch=$MERGE_DIR"
    exit 2
  fi
 done

git push $REPO_URL/$REPO_NAME.git $WK_BRANCH

#update mirror repo
cd $MIRROR_URL/$REPO_NAME.git
git remote update

cd $WORK_BASE_DIR/$WK_DIR
git fetch
