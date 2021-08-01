# ddm-starter-logger

##### Project with configuration for logging.

## Usage
Auto-configuration should be activated through the `@SpringBootApplication` or `@EnableAutoConfiguration` annotation in main class.

##### To start using the library:
* Specify dependency in your service:

```xml

<dependencies>
  ...
  <dependency>
    <groupId>com.epam.digital.data.platform</groupId>
    <artifactId>ddm-starter-logger</artifactId>
    <version>...</version>
  </dependency>
  ...
</dependencies>
```
* *@Logging* - can annotate a class or method to enable logging;
* *@Confidential* - can annotate method or method parameters to exclude confidential data from logs:
  - For a method - exclude the result of execution;
  - For method parameters - exclude method parameters.
    
##### Default:
* Logging is enabled by default for using the following `Spring Framework` annotations:
  - *@RestController*;
  - *@Service*;
  - *@KafkaListener*.

##### Request logging:
* To enable request logging you need to set next properties in configuration file: 
```properties
logging.level.org.springframework.web.filter.CommonsRequestLoggingFilter=DEBUG
```