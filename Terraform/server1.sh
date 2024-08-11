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
sudo mysql -u root -pcomsc -e "ALTER USER 'root'@'localhost' IDENTIFIED BY 'comsc';"

echo "--------------------ls files-------------------"
ls
echo in directory: $PWD

# sudo apt update && sudo apt upgrade -y
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
touch wsa_keypair.key
cat << `EOF` >> wsa_keypair.key
-----BEGIN OPENSSH PRIVATE KEY-----
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
-----END OPENSSH PRIVATE KEY-----
`EOF`
chmod 400 wsa_keypair.key

echo "cloning repository..."
echo $PWD
ssh-agent bash -c 'ssh-add wsa_keypair.key; git clone git@git.cardiff.ac.uk:c23031770/wsa_volunteer_hub.git'

echo "whoami..."
whoami
echo "ls"
ls
cd wsa_volunteer_hub

echo "link to database..."
mysql -u root -pcomsc < src/main/resources/schema.sql


echo "installing Java 17..."
sudo apt update
sudo apt install openjdk-17-jdk -y
echo java --version

echo "Installing Node.js and npm..."
curl -fsSL https://deb.nodesource.com/setup_18.x | sudo -E bash -
sudo apt-get install -y nodejs
sudo apt-get install -y npm
node -v
npm -v
 
echo "Installing maven..."
sudo apt-get install maven -y
mvn -v

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
# sudo sed --i 's/JENKINS_PORT=8080/JENKINS_PORT=8083/g' /usr/lib/systemd/system/jenkins.service
# sudo systemctl daemon-reload
# sudo systemctl restart jenkins
# sudo systemctl status jenkins
# sudo systemctl enable jenkins
# 获取实例的当前IP地址
INSTANCE_IP=$(hostname -I | awk '{print $1}')
# build
#back-end
ls
./mvnw install
nohup ./mvnw spring-boot:run &
 
#front-end
cd front-end
npm install
# 设置 VITE_API_BASE_URL 环境变量并启动前端开发服务器
VITE_API_BASE_URL="http://$INSTANCE_IP:8081" npm run dev -- --host 0.0.0.0

#ls
#java -jar SmartTowns-0.0.1-SNAPSHOT.jar --server.port=8080

echo "Installing terraform..."
cd /home/debian
wget https://releases.hashicorp.com/terraform/1.1.5/terraform_1.1.5_linux_amd64.zip
unzip terraform_1.1.5_linux_amd64.zip
sudo mv terraform /usr/local/bin/
