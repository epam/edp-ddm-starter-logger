# ddm-starter-logger

### Overview

* Project with configuration for logging.

### Usage

1. Specify dependency in your service:

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

2. Auto-configuration should be activated through the `@SpringBootApplication`
   or `@EnableAutoConfiguration` annotation in main class.

### Configuration properties

Available properties are following:

* `platform.logging.aspect.enabled` - enable aspect-based logging;
* `platform.logging.primary-url.enabled` - enable logging of the primary url;
* `logbook.feign.enabled` - enable feign-based logging;
* `logbook.info-logging.enabled` - enable info level logging;
* `logbook.strategy` -  choose logbook logging strategy : request-body-on-debug, without-body, etc.

More information about [Logbook](https://github.com/zalando/logbook)

### Annotation

* *@Logging* - can annotate a class or method to enable logging;
* *@Confidential* - can annotate method or method parameters to exclude confidential data from logs:
    - For a method - exclude the result of execution;
    - For method parameters - exclude method parameters.

#### Default:

* Aspect logging works for the following `Spring Framework` annotations:
    - *@RestController*;
    - *@Service*;
    - *@KafkaListener*.

#### Request logging:

* To enable request logging you need to set next properties in configuration file:

```properties
logging.level.org.springframework.web.filter.CommonsRequestLoggingFilter=DEBUG
```

### Test execution

* Tests could be run via maven command:
  * `mvn verify` OR using appropriate functions of your IDE.
  
### License

The ddm-starter-logger is Open Source software released under
the [Apache 2.0 license](https://www.apache.org/licenses/LICENSE-2.0).