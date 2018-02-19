# csye6225-spring2018
This the official private repository for the csye6225 network structures and cloud computing class. The repo contains the web application which will be deployed to a cloud service like AWS.

## Getting Started
Clone the repository to get a local copy of the project. This is a spring boot application built using maven. To get a local copy running, clone the repo and then use an IDE (preferrably Intellij) to import the project. The imported project can be just run as a java project as spring boot (/IDE) comes with a tomcat container.

### Prerequisites

1. You will require git (/git GUI) installed in your local machine.
2. You will require java installed in your local machine. Either open JDK or the distribution from Oracle.
3. MySql server has to be installed in your machine. The workbench for mysql is optional.
4. An IDE such as Intellij
5. Mysql workbench (optional). The workbench gives your a GUI to access your databases.
6. Install apache tomcat.

### Installing

1. Install git.
```
sudo apt-get update
sudo apt-get upgrade
sudo apt-get install git
```
Run the command ```git --version``` after the installation to check whether the installation was successful. You will an output similar to the following:
```
git version 2.7.4
```

2. Install Java. The following are the commands to install open jdk, although you can install the Oracle distribution if you wish.
```
sudo apt-get install default jdk
```
To check whether the installation was successful, enter this command: ```java -version``` The following is a sample output displaying the version of OpenJDK Java that was installed:
```
openjdk version "1.8.0_151"
OpenJDK Runtime Environment (build 1.8.0_151-8u151-b12-0ubuntu0.16.04.2-b12)
OpenJDK 64-Bit Server VM (build 25.151-b12, mixed mode)
```
3. Install the MySQL server by using the Ubuntu package manager:
```
sudo apt-get update 
sudo apt-get install mysql-server
```
After the installation process is complete, run the following command to set up MySQL:
```
/usr/bin/mysql_secure_installation
```
The secure installer goes through the process of setting up MySQL including creating a root user password. It will prompt you for some security options, including removing remote access to the root user and setting the root password.

To check whether the installation was successful, enter this command: ```mysql --version``` The following is a sample output displaying the version of mysql that was installed:
```
mysql  Ver 14.14 Distrib 5.7.20, for Linux (x86_64) using  EditLine wrapper
```
4. Installing Intellij
Download the tar for intellij from https://www.jetbrains.com/idea/download/index.html#section=linux. After download the tar file execute the following commands
```
sudo tar -xvf <yourintellijtarfile.tar> -C /opt/
```
switch to the bin folder.
```
cd /opt/idea-IU-173.4301.25/bin
```
Run idea.sh from the bin subdirectory
```
sh ./idea.sh
```
If the installation was successful, idea intellij will start up.

5. mysql Workbench.
```
sudo apt update && sudo apt upgrade
sudo apt install mysql-workbench
```
A successful installation will result in the following output after running the command ```mysql-workbench --version``` :
```
MYSQL Workbench CE (GPL) 6.3.6 CE build 511
```

6. Apache Tomcat
First, create a new tomcat group:
```
sudo groupadd tomcat
```
Next, create a new tomcat user. We'll make this user a member of the tomcat group, with a home directory of /opt/tomcat (where we will install Tomcat), and with a shell of /bin/false (so nobody can log into the account):
```
sudo useradd -s /bin/false -g tomcat -d /opt/tomcat tomcat
```
Now that our tomcat user is set up, let's download and install Tomcat. The best way to install Tomcat 8 is to download the latest binary release then configure it manually.
Next, change to the /tmp directory on your server. This is a good directory to download ephemeral items, like the Tomcat tarball, which we won't need after extracting the Tomcat contents:
```
cd /tmp
```
Download the latest version from the apache site to the /tmp directory, then:
```
sudo mkdir /opt/tomcat
sudo tar xzvf <apache.your.version.tar.gz> -C /opt/tomcat --strip-components=1
```
The tomcat user that we set up needs to have access to the Tomcat installation. We'll set that up now.
```
cd /opt/tomcat
```
Give the tomcat group ownership over the entire installation directory:
```
sudo chmod -R g+r conf
sudo chmod g+x conf
```
Make the tomcat user the owner of the webapps, work, temp, and logs directories:
```
sudo chown -R tomcat webapps/ work/ temp/ logs/
```
We want to be able to run Tomcat as a service, so we will set up systemd service file.

Tomcat needs to know where Java is installed. This path is commonly referred to as "JAVA_HOME". The easiest way to look up that location is by running this command:
```
sudo update-java-alternatives -l
```
```
Output
java-1.8.0-openjdk-amd64       1081       /usr/lib/jvm/java-1.8.0-openjdk-amd64
```
The correct ```JAVA_HOME``` variable can be constructed by taking the output from the last column (highlighted in red) and appending /jre to the end. Given the example above, the correct ```JAVA_HOME``` for this server would be:

```
JAVA_HOME
/usr/lib/jvm/java-1.8.0-openjdk-amd64/jre
```
Your ```JAVA_HOME``` may be different.


With this piece of information, we can create the systemd service file. Open a file called tomcat.service in the /etc/systemd/system directory by typing:

```
sudo nano /etc/systemd/system/tomcat.service
```
Paste the following contents into your service file. Modify the value of JAVA_HOME if necessary to match the value you found on your system. You may also want to modify the memory allocation settings that are specified in CATALINA_OPTS:
```
/etc/systemd/system/tomcat.service
[Unit]
Description=Apache Tomcat Web Application Container
After=network.target

[Service]
Type=forking

Environment=JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-amd64/jre
Environment=CATALINA_PID=/opt/tomcat/temp/tomcat.pid
Environment=CATALINA_HOME=/opt/tomcat
Environment=CATALINA_BASE=/opt/tomcat
Environment='CATALINA_OPTS=-Xms512M -Xmx1024M -server -XX:+UseParallelGC'
Environment='JAVA_OPTS=-Djava.awt.headless=true -Djava.security.egd=file:/dev/./urandom'

ExecStart=/opt/tomcat/bin/startup.sh
ExecStop=/opt/tomcat/bin/shutdown.sh

User=tomcat
Group=tomcat
UMask=0007
RestartSec=10
Restart=always

[Install]
WantedBy=multi-user.target
```
When you are finished, save and close the file.

Next, reload the systemd daemon so that it knows about our service file:
```
sudo systemctl daemon-reload
```
Start the Tomcat service by typing:
```
sudo systemctl start tomcat
```

Double check that it started without errors by typing:
```
sudo systemctl status tomcat
```



## Running the tests

The unit tests can be found under the test package. The unit tests test various controller actions and methods present in the classes. The unit test were developed using Junit 4.

The unit test can be run using the command:
```
mvn test
```
A successful completion of the test suite will result in the following output:
```
Results :

Tests run: 6, Failures: 0, Errors: 0, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 25.748 s
[INFO] Finished at: 2018-02-19T09:43:56-05:00
[INFO] Final Memory: 38M/294M
[INFO] ------------------------------------------------------------------------
```

## Deployment

Has not been deployed as of now.

## Built With

* [Spring Boot](https://projects.spring.io/spring-boot) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Angular](https://angular.io/) - Frontend Framework


## Authors

* **Vaishak P Dinesh** - dinesh.v@husky.neu.edu
* **Nithin Kartha** - kartha.n@husky.neu.edu
* **Nandeep Nellagondanahalli** - nellagondanahalli.n@husky.neu.edu

## Acknowledgments

* We wanna thank Tejas Parikh, our professor, whose lectures, notes and insight have helped us greatly.
* We also tip our hats to the TA's who have helped us with issues, bugs and whose guidance have paved the way 
* for a smooth completion of the project.

