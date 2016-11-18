# myosbb
Project “OSBB” – a web-application which is  a godsend for condominium head, managers and residents. It offers a very easy way to manage accounting and residents, events and organizational issues as well as represents a simple design and great functionality that is needed  for managing. In this project you'll see the following technologies: 
- Spring Data JPA
- Spring Boot
- Spring Oauth2.0 (Jwt based)
- Hibernate
- Spring MVC
- Liquibase
- RESTful resource server
- Angular2 

***

HOW TO RUN PROJECT

You will need:
- Maven 
- Tomcat 8
- MariaDB (set user and password to "root", set port to 1100)
- Node.js
- Eclipse IDE (you can use another one, but this instruction describes some specific steps for Eclipse)
- Spring Tool Suite (STS) for Eclipse
- Maven plugin for Eclipse

After you have installed all necessary tools:
- clone this repo to your workspace
- import sorce code as existing Maven project
- open CLI
- navigate to myosbb/web/src/main/resources/public/ and run "npm install", then run "npm run tsc"
- in Eclipse package explorer navigate to project "web", right click on it and choose Run as -> Spring Boot Application; set profile to "mariadb"
- OR in Eclipse package explorer navigate to /myosbb/web/src/main/java/com/softserve/osbb/config/Application.java and right click on it, choose Run as -> Spring Boot Application;  set profile to "mariadb"
- observe console log, when compilation is finished - populate database with test data (you can find some in osbb.sql)
- navigate to http://localhost:8080/myosbb/ in your browser

How to run project on Tomcat8:
- open CLI
- navigate to your project folder
- run "mvn clean package -Pmariadb"
- deploy project to Tomcat: copy myosbb.war file from myosbb/web/target to your Tomcat's webapps folder and start or reload Tomcat; deployment will take a few minutes
- navigate to http://localhost:8080/myosbb/ in your browser

***

How to use external properties:
- you should add "-D" and full path to property file in deployment package without space
- for example: "mvn clean install -Dspring.config.location=/home/nataliia/myosbb1/deployment/external.properties"

You can add this command to "Edit Configuration" in Intellij IDEA to field "VM Options",
then you environment will run them every time.
