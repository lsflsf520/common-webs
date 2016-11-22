#!/bin/bash

POM_PATH="$1"
ENV_NAME="$2"
CURR_TIME=`date +%Y%m%d%H%M%S`
VER_TMP_FILE=/tmp/SNAPSHOT_version_$CURR_TIME
NID_TMP_FILE=/tmp/SNAPSHOT_nid_$CURR_TIME
ALL_TMP_FILE=/tmp/SNAPSHOT_all_$CURR_TIME
if [ ! -f "$POM_PATH" ];then
  echo "pom file $POM_PATH not exists."
  exit 1
fi


if [ -z "$ENV_NAME" ];then
  echo "ENV_NAME cannot be null."
  exit 2
fi

if [[ "$POM_PATH" =~ "/stiku-parent/pom.xml" ]];then
   sed -i "s/<artifactId>parent.*<\/artifactId>/<artifactId>parent-$ENV_NAME<\/artifactId>/g" $POM_PATH
   echo "artifactId is change!"
   cat  $POM_PATH |grep "SNAPSHOT</"|awk -F- '{print $1}'|awk -F">" '{print $2}' > $VER_TMP_FILE
   cat  $POM_PATH |grep -n "SNAPSHOT</"|awk -F: '{print $1}' > $NID_TMP_FILE
   paste -d# $NID_TMP_FILE $VER_TMP_FILE > $ALL_TMP_FILE
   typeset -i SNAPSHOT_c=`cat $ALL_TMP_FILE|wc -l`
   #echo $SNAPSHOT_c;
   for ((i=1;i<=$SNAPSHOT_c;i++))
     do
     #echo $i
     nid=`sed -n "$i{p}"  $ALL_TMP_FILE|awk -F"#" '{print $1}'`
     #echo $nid
     vid=`sed -n "$i{p}"  $ALL_TMP_FILE|awk -F"#" '{print $2}'`
     #echo $vid
     sed -i "$nid{s/[!0-9].*-SNAPSHOT/$vid-$ENV_NAME-SNAPSHOT/g}" $POM_PATH
   done
    
   rm -f $VER_TMP_FILE $NID_TMP_FILE $ALL_TMP_FILE
   echo "内部jar包的版本号 is change!"
else
   sed -i "s/<artifactId>parent.*<\/artifactId>/<artifactId>parent-$ENV_NAME<\/artifactId>/g" $POM_PATH
   echo "artifactId is change!"
   properties_id=`cat $POM_PATH|grep properties|wc -l`
   if [ $properties_id -gt 1 ];then
      properties_start=`cat $POM_PATH |grep -n "<properties>"|awk -F: '{print $1}'`
      properties_end=`cat $POM_PATH |grep -n "</properties>"|awk -F: '{print $1}'`
      sed -i "$properties_start,$properties_end d" $POM_PATH
      echo "properties tag is delete!"
   fi
   dependencyManagement_id=`cat $POM_PATH|grep "dependencyManagement"|wc -l`
   if [ $dependencyManagement_id -gt 1 ];then
      dependencyManagement_start=`cat $POM_PATH |grep -n "<dependencyManagement>"|awk -F: '{print $1}'`
      dependencyManagement_end=`cat $POM_PATH |grep -n "</dependencyManagement>"|awk -F: '{print $1}'`
      sed -i "$dependencyManagement_start,$dependencyManagement_end d" $POM_PATH
      echo "dependencyManagement tag is delete!"
   fi 
      
fi


version_id=`cat $POM_PATH |grep "SNAPSHOT</version>"|awk -F- '{print $1}'|awk -F">" '{print $2}'`
sed -i "s/<version>$version_id.*-SNAPSHOT<\/version>/<version>$version_id-CSNAPSHOTC<\/version>/g" $POM_PATH

relativePath_id=`cat $POM_PATH|grep "relativePath"|wc -l`
if [ $relativePath_id -gt 1 ];then
      relativePath_start=`cat $POM_PATH |grep -n "<relativePath>"|awk -F: '{print $1}'`
      relativePath_end=`cat $POM_PATH |grep -n "</relativePath>"|awk -F: '{print $1}'`
      sed -i "$relativePath_start,$relativePath_endd" $POM_PATH
      echo "relativePath tag is delete!"
fi 

sed -i "s/<version>$version_id-CSNAPSHOTC<\/version>/<version>$version_id-SNAPSHOT<\/version>/g" $POM_PATH
