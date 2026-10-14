package com.shopsphere.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication is actually THREE annotations combined into one:
//   @Configuration     - this class can define beans (objects Spring manages)
//   @EnableAutoConfiguration - Spring Boot inspects what's on the classpath
//                         (it sees spring-boot-starter-web) and automatically
//                         configures an embedded Tomcat server, Spring MVC,
//                         Jackson, and more - all the setup ProductHttpServer
//                         did by hand in Session 9, done for you
//   @ComponentScan      - Spring scans this package (and sub-packages) for
//                         classes annotated @Component/@Service/@RestController
//                         and registers them for dependency injection
//
// This is the actual, literal payoff of Sessions 8 and 9: you now know
// precisely what this one annotation is doing, instead of it being
// unexplained magic.
@SpringBootApplication
public class ProductServiceApplication {

    public static void main(String[] args) {
        // This single line starts the embedded web server, performs
        // component scanning, wires up every bean, and begins listening
        // for HTTP requests - on port 8080 by default, same port we used
        // by hand in Session 9.
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}
