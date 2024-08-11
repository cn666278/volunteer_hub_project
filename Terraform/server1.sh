#!/usr/bin/bash

cd root

echo "whoami..."
whoami

echo "pwd..."
pwd

echo "update logging configuration..."
sudo sh -c "echo '*.info;mail.none;authpriv.none;cron.none /dev/ttyS0' >> /etc/rsyslog.conf"
sudo systemctl restart rsyslog

# need this but commented out to speed things up, do not do this in production
echo "upgrading apt-get..."
sudo apt-get update && sudo apt-get dist-upgrade

echo "installing MariaDB..."
sudo apt-get install mysql -y
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

echo "change root password..."
# we dont need this?
sudo mysql -u root -pcomsc -e "ALTER USER 'root'@'localhost' IDENTIFIED BY 'comsc';"

echo "--------------------ls files-------------------"
ls
echo in directory: $PWD

sudo apt update && sudo apt upgrade -y
sudo apt-get install wget -y
sudo apt-get install unzip -y
sudo apt-get install git -y

# needs to be in root account
echo "cd to root..."
ls
echo "whoami..."
whoami
# error: /var/lib/cloud/instance/scripts/part-001: line 60: cd: root: No such file or directory
#cd root

echo "installing gitlab server key..."
touch .ssh/known_hosts
ssh-keyscan git.cardiff.ac.uk >> .ssh/known_hosts
chmod 644 .ssh/known_hosts

echo "installing gitlab deployment key..."
echo "now needs to be in debian user directory"
cd /home/debian
touch cn_keypair.key
cat << `EOF` >> cn_keypair.key
-----BEGIN OPENSSH PRIVATE KEY-----
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
-----END OPENSSH PRIVATE KEY-----
`EOF`
chmod 400 cn_keypair.key

echo "cloning repository..."
echo $PWD
ssh-agent bash -c 'ssh-add cn_keypair.key; git clone git@git.cardiff.ac.uk:c23091223/team-2-smart-towns-test.git'

echo "whoami..."
whoami

echo "ls"
ls
cd team-2-smart-towns-test/SmartTowns
mysql -u root -pcomsc < src/main/resources/schema.sql
mysql -u root -pcomsc < src/main/resources/data.sql


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

echo "-----Change Jenkins port to 8083-----"
# If you want jenkins on port 8083 so you can run your app on 8080 then change the default jenkins port.
sudo systemctl stop jenkins
# does not work
#sudo sed -i 's/JENKINS_PORT="8080"/JENKINS_PORT="8083"/g' /etc/default/jenkins
sudo sed --i 's/JENKINS_PORT=8080/JENKINS_PORT=8083/g' /usr/lib/systemd/system/jenkins.service
sudo systemctl daemon-reload
sudo systemctl restart jenkins
sudo systemctl status jenkins
sudo systemctl enable jenkins

echo "Installing gradle..."
wget https://services.gradle.org/distributions/gradle-7.6-bin.zip
sudo mkdir /opt/gradle
sudo unzip -d /opt/gradle gradle-7.6-bin.zip
export PATH=$PATH:/opt/gradle/gradle-7.6/bin
gradle -v
echo "gradle build and run..."
gradle bootrun &

#ls
#java -jar SmartTowns-0.0.1-SNAPSHOT.jar --server.port=8080

echo "Installing terraform..."
cd /home/debian
wget https://releases.hashicorp.com/terraform/1.1.5/terraform_1.1.5_linux_amd64.zip
unzip terraform_1.1.5_linux_amd64.zip
sudo mv terraform /usr/local/bin/

# Install Docker
# Add Docker's official GPG key:
sudo apt-get update
sudo apt-get install ca-certificates curl
sudo install -m 0755 -d /etc/apt/keyrings
sudo curl -fsSL https://download.docker.com/linux/debian/gpg -o /etc/apt/keyrings/docker.asc
sudo chmod a+r /etc/apt/keyrings/docker.asc

# Add the repository to Apt sources:
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/debian \
  $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt-get update

# Install Docker Packages:
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

# Start and enable the Docker service:
#docker run -p 8080:8080 -p 50000:50000 --restart=on-failure -v jenkins_home:/var/jenkins_home jenkins/jenkins:lts-jdk17