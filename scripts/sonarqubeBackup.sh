#!/bin/bash

# !!! in /etc/cron.daily this script need to be without ".sh" extension 

sonarqubeDataFolder=''
bucketName=''

cd "$sonarqubeDataFolder"

dateString=`date +%Y-%m-%d-%H:%M`

sudo tar c TiolkTrack/ | gzip --best > /tmp/SonarQube-"$dateString".tar.gz

gsutil cp  /tmp/SonarQube-"$dateString".tar.gz gs://"$bucketName"

gsutil ls gs://"$bucketName"/SonarQube-* | tac | tail +6 | xargs gsutil rm
#gsutil ls -l gs://"$bucketName"/SonarQube-* | tac | tail +2 | sort -k 2 | awk '{print $3}' | tail +6 | xargs gsutil rm

rm  /tmp/SonarQube-"$dateString".tar.gz
