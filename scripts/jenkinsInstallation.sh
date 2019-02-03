#!/bin/bash

bucketName='tioltrack-backup'
jenkinsDataFolder='/home/byznass_group/byznass/TiolkTrack/jenkinsci-byznass'

###################################################################################

backupName='jenkinsbackupLatest.tar.gz'

mkdir -p "$jenkinsDataFolder"
cd "$jenkinsDataFolder"

gsutil cp $(gsutil ls gs://"$bucketName"/Jenkins-* | tail -1) "$backupName"
tar -xf "$backupName"
rm "$backupName"

####################################################################################

wget https://raw.githubusercontent.com/byznass/jenkinsci-byznass/master/Dockerfile
sudo docker build -t jenkinsci/byznass -f Dockerfile .
sudo docker run -d --name jenkinsci-byznass -u root -p 8080:8080 -p 50000:50000 -v "$jenkinsDataFolder"/data:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock jenkinsci/byznass

####################################################################################

wget -O jenkinsBackUp.sh https://raw.githubusercontent.com/byznass/TiolkTrack/master/scripts/jenkinsBackUp.sh
printf "#!/bin/bash\n\nbucketName='$bucketName'\njenkinsDataFolder='$jenkinsDataFolder'\n\n" | cat - jenkinsBackUp.sh > temp && mv temp jenkinsBackUp
chmod +x jenkinsBackUp
sudo mv jenkinsBackUp /etc/cron.daily/
rm jenkinsBackUp.sh
