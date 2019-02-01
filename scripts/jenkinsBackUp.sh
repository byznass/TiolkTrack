#!/bin/bash

# !!! in /etc/cron.daily this script need to be without ".sh" extension 

cd {Jenkins_data_folder}

dateString=`date +%Y-%m-%d-%H:%M`

sudo tar c data/ | gzip --best > /tmp/Jenkins-"$dateString".tar.gz

gsutil cp  /tmp/Jenkins-"$dateString".tar.gz gs://{buket_name}

gsutil ls gs://{buket_name}/Jenkins-* | tac | tail +6 | xargs gsutil rm
#gsutil ls -l gs://{buket_name}/Jenkins-* | tac | tail +2 | sort -k 2 | awk '{print $3}' | tail +6 | xargs gsutil rm

rm  /tmp/Jenkins-"$dateString".tar.gz
