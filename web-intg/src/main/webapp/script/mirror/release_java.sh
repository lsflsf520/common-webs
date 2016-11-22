#!/bin/bash

if [ $# -lt 3  ];then
  echo "environment name and project name and redirect output file must be defined."
  echo "example(web-passport and web-ms are project name, develop and test are environment name, /tmp/console.log and /tmp/ms-console.log are redirect output file):"
  echo "  $0 web-passport develop /tmp/console.log"
  echo "  $0 web-ms test /tmp/ms-console.log"
  exit 1
fi

source `dirname $0`/common.sh

$WORK_BASE_DIR/release.sh $1 $2 > $3


