#! /bin/bash

#######################################################################################################################                  
#
# Name:     install.sh 
# Author:   matthew.hunter@advance-auto.com
# Created:  05/15/2015
#
# Modifications 
# Date        Author                 Description
# ----------- ---------------------- --------------------------------------------------------------------------- 
# 06/23/2016  matthew.hunter		  Added restart JBoss flag to allow for installing multiple services
#			  joel.barfield			  without having the server restart between each.
#
# Purpose: This script is used to install a web artifact to a JBoss container.
#          
# Parameters:
# -h JBoss home that will contain the artifact
# -n JBoss node that will contain the artifact
# -d JBoss deployment directory to which the artifact will be copied.
# -s JBoss start / stop script name (with path to the script)
# -r Restart JBoss flag y/n. (optional, defaults to no restart.) 
#
#######################################################################################################################

USAGE="Usage: $0 -h [jboss host] -n [jboss node] -d [jboss deploy directory] -s [start / stop script] -r [restart jboss y/n] file to deploy."

if [[ $# < 9 ]] ; then
  echo "Invalid number of arguments."
  echo ${USAGE}
  #exit 2
fi

while getopts ":h:n:d:s:r:" opt; do
  case $opt in
  h)
    JBOSS_HOME=${OPTARG}
    echo "JBoss Home is: ${JBOSS_HOME}"
	;;
  n)
    JBOSS_NODE=${OPTARG}
    echo "JBoss Node is: ${JBOSS_NODE}"
	;;
  d)
    JBOSS_DEPLOY_DIR=${OPTARG}
    echo "JBoss Deploy Dir is: ${JBOSS_DEPLOY_DIR}"
	;;
  s)
    JBOSS_START_STOP=${OPTARG}
    echo "JBoss Script is: ${JBOSS_START_STOP}"
    ;;
  r) 
    RESTART=${OPTARG}
    echo "Restart JBoss: ${RESTART}"
    ;;	
  \?)
    echo "Invalid option.: -$OPTARG" >&2
    echo ${USAGE}
    exit 2
    ;;
  esac
done

args=$[$#-1]
shift $args 

echo $@

# Stop JBoss
if [[ $RESTART == "y" ]] ; then
  echo "Stopping JBoss"
  ${JBOSS_START_STOP} stop
fi 

# Copy file to deployments
cp $@ $JBOSS_HOME/$JBOSS_DEPLOY_DIR

# Start JBoss
if [[ $RESTART == "y" ]] ; then
  echo "Starting JBoss"
  ${JBOSS_START_STOP} start
fi

