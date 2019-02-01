#!/bin/bash

bucketName='clei'
jenkinsDataFolder='121'

####################################################################################

wget https://raw.githubusercontent.com/byznass/jenkinsci-byznass/master/Dockerfile

sudo docker build -t jenkinsci/byznass -f Dockerfile .

sudo docker run -d --rm --name jenkinsci/byznass -u root -p 8080:8080 -p 50000:50000 -v /home/byznass_group/byznass/TiolkTrack/jenkinsci-byznass/data:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock jenkinsci/byznass

####################################################################################

wget -O jenkinsBackUp.sh https://raw.githubusercontent.com/byznass/TiolkTrack/master/scripts/jenkinsBackUp.sh

printf "#!/bin/bash\n\nbucketName=$bucketName\n\n" | cat - jenkinsBackUp.sh > temp && mv temp jenkinsBackUp

chmod +x jenkinsBackup

sudo cp jenkinsBackup /etc/cron.daily/
