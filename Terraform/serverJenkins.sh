#!/usr/bin/bash

echo "update logging configuration..."
sudo sh -c "echo '*.info;mail.none;authpriv.none;cron.none /dev/ttyS0' >> /etc/rsyslog.conf"
sudo systemctl restart rsyslog

cd /home/debian
echo in directory $PWD

echo "installing MariaDB..."
sudo apt-get install mariadb-server -y
sudo systemctl start mariadb
sudo systemctl status mariadb
sudo systemctl enable mariadb

echo "creating mysql_secure_installation.txt..."
touch mysql_secure_installation.txt
cat << `EOF` >> mysql_secure_installation.txt

n
Y
comsc
comsc
Y
Y
Y
Y
Y
`EOF`

echo "running mysql_secure_installation..."
sudo mysql_secure_installation < mysql_secure_installation.txt

sudo apt update && sudo apt upgrade -y
sudo apt-get install wget -y
sudo apt-get install unzip -y
sudo apt-get install git -y


echo "installing Java 17..."
sudo apt update
sudo apt install openjdk-17-jdk -y
echo java --version


echo "Link to Jenkins repository"
sudo wget -O /usr/share/keyrings/jenkins-keyring.asc \
  https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key
echo "deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc]" \
  https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
  /etc/apt/sources.list.d/jenkins.list > /dev/null

echo "install Jenkins"
sudo apt-get update
sudo apt-get install jenkins -y

echo "installing gitlab server key... has to be added to the jenkins user home (~) dir "
mkdir /var/lib/jenkins/.ssh
sudo touch /var/lib/jenkins/.ssh/known_hosts
sudo ssh-keyscan git.cardiff.ac.uk >> /var/lib/jenkins/.ssh/known_hosts
sudo chmod 644 /var/lib/jenkins/.ssh/known_hosts

sudo systemctl start jenkins

# echo "-----Change Jenkins port to 8083-----"
# # If you want jenkins on port 8083 so you can run your app on 8080 then change the default jenkins port.
# sudo systemctl stop jenkins
# # does not work
# #sudo sed -i 's/JENKINS_PORT="8080"/JENKINS_PORT="8083"/g' /etc/default/jenkins
# sudo sed --i 's/JENKINS_PORT=8080/JENKINS_PORT=8083/g' /usr/lib/systemd/system/jenkins.service
# sudo systemctl daemon-reload
# sudo systemctl restart jenkins
# sudo systemctl status jenkins
# sudo systemctl enable jenkins
