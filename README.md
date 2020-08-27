# alexa-order Service
> Write service description here
---
## Prerequisites for Development
Prior to developing / maintaining this service, you will need to configure your local environment for 
building and running the service locally.  Much of the information listed in the succeeding sections
are duplicated in the on boarding document located in 
[Confluence](https://advanceautoparts.atlassian.net/wiki/spaces/TECH/pages/866189395/Getting+Started#GettingStarted-Softwaresetup)


## Building
Use Maven to build the service from the command line by running the following command at the root directory
of the project:

`mvn clean package`

Once the build is complete, the runtime application will be available in the target directory.

## Running
### As Spring boot jar
If you want to run the service locally, simply run the following at the command line from the root directory
of the project:

`mvn spring-boot:run -Dspring-boot.run.profiles=h2,actuators`

Once the service is running, you may access the Swagger UI API [documentation](http://localhost:8080/swagger-ui.html)
page and interact with the service directly from that page.  Alternatively you may submit requests from Postman, 
or even command line using curl (examples are self contained in the Swagger documentation! 😃)

If you'd like to interact directly with the data the service is running over top of, the embedded H2 database contains
a web based [console](http://localhost:8080/h2-console) that you can use:
- JDBC URL: jdbc:h2:mem:test;DB_CLOSE_ON_EXIT=FALSE;DB_CLOSE_DELAY=-1
- User / Password: sa / sa

You can also view health and metrics through actuator sidecar functionality:
- [Health](http://localhost:8080/actuator/health)
- [Info](http://localhost:8080/actuator/info)

###### In JBOSS EAP 6
AAP uses EAP 6 which limits JDK to 8. A maven profile "jboss" is created to package the app as a Java 8 WAR file
that can be deployed to EAP 6.    
- Create EAP 6 compatible WAR: `mvn clean package -P jboss`
- Add following system properties to connect to Buyers guide Oracle database:
    - alexa-order.datasource.url
    - alexa-order.datasource.user-name
    - alexa-order.datasource.password
- Review _WEB-INF/jboss-deployment-structure.xml_ that disables EAP features:    
    - JPA
    - Hibernate
    - Validator
- Deploy WAR to EAP
- Context root is set to __/alexa-order/api__ in _WEB-INF/jboss-web.xml_ 
- Access [Dev Swagger UI](http://sdecsvcdev03:8080/alexa-order/api/swagger-ui.html) 

### Test WAR file locally in JBOSS EAP 6.4
> You need to have Docker installed locally
- Added Oracle connection info in application-oracle.yml
- Review Dockerfile.jboss and if required, update the war file name
```
mvn clean package -P jboss
docker build -f Dockerfile.jboss -t aap/alexa-order:jboss .
docker run -it --rm --name bg -p 8080:8080 aap/alexa-order:jboss
```
- Verify logs
- [Swagger UI](http://localhost:8080/alexa-order/api/swagger-ui.html)
- [Health check](http://localhost:8080/alexa-order/api/actuator/health)

## Kafka integration
- Review _application-kafka.yml_
- All kafka components are set to run on "kafka" spring profile in this archetype
- Sample configuration, producer, consumer, message filter, error handling components are added
- Embdedded kafka unit testing is added that tests the producer and consumer using an in-memory broker

### On Openshift
- Request Devops to create a secrets file with:
    - Bootstrap servers
    - Security protocol
    - SASL mechanism
    - JAAS config
- Refer ocp files with above mentioned variables

### Locally
- Start local Kafka broker (Native or on docker)
- Create "occ.hackathon" topic
- Identify the advertised broker host and port
- Set protocol as PLAINTEXT
- Set eclipse/intellij "Run configuration" variables
    - KAFKA_BOOTSTRAP_SERVERS=[broker]
- `mvn spring-boot:run -Dspring-boot.run.profiles=h2,actuators,kafka`
- Run the provided swagger endpoint and a message will be published to the topic "occ.hackathon" and consumed

## Generate Site
`mvn clean site`

## Release pipeline
Use Enterprise Jenkins to build and deploy spring-boot app to Openshift. Contact Amplify/Devops team for more info.
- [Release pipeline](https://advanceautoparts.atlassian.net/wiki/spaces/OCC/pages/1260454896/Architecture+-+Build+and+Deploy#Build-and-deploy-pipeline---Openshift)


## References
- [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Openshift Spring boot reference](https://advanceautoparts.atlassian.net/wiki/x/CwaMPg)
- [Migrating to Helm](https://advanceautoparts.atlassian.net/wiki/x/IA5SSQ)
- [XFF with Openshift](https://advanceautoparts.atlassian.net/wiki/x/hQOrW)