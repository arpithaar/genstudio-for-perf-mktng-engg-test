# genstudio-for-perf-mktng-engg-test

### Pre-requisites

* Java 17
* Apache Maven 3.6.3 or above
* Git

The following commands can be used to install the above softwares on a linux based machine.
  ```
  yum install java-17-openjdk
  yum install maven
  yum install git
  ```
Validate by executing the following commands

* java -version
 
  ```
  openjdk version "17.0.13" 2024-10-15 LTS
  OpenJDK Runtime Environment (Red_Hat-17.0.13.0.11-1) (build 17.0.13+11-LTS)
  OpenJDK 64-Bit Server VM (Red_Hat-17.0.13.0.11-1) (build 17.0.13+11-LTS, mixed mode, sharing)
  ```

* mvn -version
  ```
  Maven home: /usr/share/maven
  Java version: 17.0.13, vendor: Red Hat, Inc., runtime: /usr/lib/jvm/java-17-openjdk-17.0.13.0.11-4.el9.x86_64
  Default locale: en_US, platform encoding: UTF-8
  OS name: "linux", version: "5.14.0-503.15.1.el9_5.x86_64", arch: "amd64", family: "unix"
  ```
  
*  git -v
  ```
  git version 2.43.5
```

### Clone the git repository
  ```
  git clone https://github.com/arpithaar/genstudio-for-perf-mktng-engg-test.git
  ```

### Project installation

* Navigate to the folder genstudio-for-perf-mktng-engg-test from a terminal and execute the below commands to run the Spring boot application
    ```
  mvn clean install
  mvn spring-boot:run
    ```

* Below logs can be seen in the terminal
![spring_boot_run_logs.png](misc%2Fspring_boot_run_logs.png)

* Above command will initialize Tomcat server on port 8080 and deploy the spring boot application
* Navigate to http://localhost:8080/ to ensure server is running
  ![localhost_8080.png](misc%2Flocalhost_8080.png)
* Access the REST endpoint in the following format - http://localhost:8080/romannumeral?query=3432

* Navigate to the folder react-frontend in a different terminal. Install npm using the following command
  ```
  yum install npm -y
  ```
* Validate npm using the following command
  ```
  npm -v
  ```
* Install libraries needed for the react app
  ```
  npm install
  ```
  
* Once the above command is successful, node_modules folder will be created for all the downloaded libraries.
* Run the react frontend using the below command
  ```
  PORT=3002 npm start
  ```
* The frontend application will now be accessible via http://localhost:3002/
* Light Theme:
  ![light_theme.png](misc%2Flight_theme.png)
* Dark Theme:
  ![dark_theme.png](misc%2Fdark_theme.png)

### Monitoring

* View health status of the Spring Boot app by navigating to http://localhost:8080/actuator/health
* View metrics such as request count, active threads,memory usage,etc by navigating to http://localhost:8080/actuator/metrics
Note : This endpoint gives back all names of the metrics in response. To access specific metric, append the name in the above url.
Example : http://localhost:8080/actuator/metrics/http.server.requests.active


* Let's set up Prometheus to collect metrics from the Spring Boot application
* Install Homebrew using the following command in a terminal
  ```
  /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
  ```
* Use the below command to ensure its successfully installed. It will display the version
  ```
  brew -v
  ```
* Now install prometheus
  ```
  brew install prometheus
  ```
* Verify the installation
  ```
  prometheus --version
  ```
* Run Prometheus in the background using the below command
  ```
  brew services start prometheus
  ```
* Prometheus will start on port 9090. To access its UI, type http://localhost:9090 in your web browser
* Stop the service
  ```
  brew services stop prometheus
  ```
* Find prometheus.yml to update scrape configs. For Macs on ARM architecture , the yaml file will be found under /opt/homebrew/etc
if installed via homebrew. If not found here, search for the file using the command :
  ```
  find / -name prometheus.yml
  ```
Note: If above command is executed, it will take a while to execute and find the location. Narrow down the search if you atleast
know the parent path beforehand

* Once the file is found, open the file and replace its content with the content from
[prometheus.yml](misc%2Fprometheus.yml). Or, just replace the file with [prometheus.yml](misc%2Fprometheus.yml) . Make sure the file is saved

* Start Prometheus again
  ```
  brew services start prometheus
  ```
* Navigate to http://localhost:9090/targets to check the target health
* This will display the prometheus endpoint of Spring Boot app http://localhost:8080/actuator/prometheus that is being scraped
![Prometheus_UI.png](misc%2FPrometheus_UI.png)
* Install Grafana for visualization using the below command
  ```
  brew install grafana
  ```
* To ensure successful installation, run the below command to see its version
  ```
  grafana -v
  ```
* Start the service in the background
  ```
  brew services start grafana
  ```
* This will start Grafana in port 3000. Should be accessible at http://localhost:3000 in your web browser. 
* Username/password = admin/admin . You can skip resetting password for now when it prompts 
* Set up Datasource as Prometheus in Grafana . Follow the steps in the below video to configure a basic 
dashboard for the spring boot app
[Grafana_demo.mov](misc%2FGrafana_demo.mov)
* React app performance will be logged in browser console. Check the browser console for the metrics around core
web vitals
![web_vitals_react.png](misc%2Fweb_vitals_react.png)


### Framework Dependencies
* Using Spring Boot 3 as the Java backend to create the REST endpoint as it comes with embedded Tomcat server
* Spring Boot 3 is only supported from Java 17 onwards to support optimization benefits
* Created spring boot project using Spring Initializr https://start.spring.io/
* Added Spring Web dependency in pom.xml for creating REST endpoints
```
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
```
* Added ThymeLeaf dependency for embedding dynamic data in HTML
  ```
    <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf</artifactId>
		</dependency>
  ```
* Added spring-boot-starter-test dependency for implementing Spring Boot Tests
  ```
    <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
  ```
* Configured Logback in Spring. Configuration file placed at src/main/resources/logback-spring.xml. 
* Log files are rotated hourly basis and will be available within logs folder
* Added Actuator dependency to enable built-in support for monitoring in Spring Boot
  ```
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
  </dependency>
  ```
* Added Micrometer dependency to integrate it with Spring Boot Actuator to enable support for Prometheus .
  ```
  <dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
  </dependency>
  ```
* This will expose http://localhost:8080/actuator/prometheus which is configured to be scraped by Prometheus as the below
configuration is enabled in prometheus.yml file
  ```
    scrape_configs:
  - job_name: 'spring-boot-actuator'
    metrics_path: '/actuator/prometheus'  # Override the default /metrics path
    static_configs:
    - targets: ['localhost:8080']  
  ```

* React App for the frontend
* Bootstrapped a react app using the command
    ```
    npx create-react-app react-frontend
    ```
* Using Axios in React to invoke http requests to Spring Boot application
* React Spectrum libraries to leverage Adobe's design system
* Added Web Vitals to monitor frontend performance. Their events can be tracked in Google Analytics dashboard to monitor
the health of a React app


### Logic to convert integer to roman
1. Divide the given number into digits at different places like one’s, two’s, hundred’s or thousand’s.
2. Starting from the thousand's place, print the corresponding roman value. For example, if the digit at thousand’s place is 3 then print the roman equivalent of 3000.
3. Repeat the second step until we reach one’s place.
4. Wikipedia for Roman Numeral specification : https://en.wikipedia.org/wiki/Roman_numerals