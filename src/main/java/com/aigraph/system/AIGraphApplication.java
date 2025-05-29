package com.aigraph.system;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main Spring Boot application class for the AI Course Knowledge Graph System.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.aigraph.system")
public class AIGraphApplication extends SpringBootServletInitializer {
    
    private static final Logger logger = LoggerFactory.getLogger(AIGraphApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AIGraphApplication.class);
    }
    
    public static void main(String[] args) {
        SpringApplication.run(AIGraphApplication.class, args);
    }
    
    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            logger.info("==================================================");
            logger.info("知识图谱系统已成功启动!");
            logger.info("访问地址: http://localhost:8080/aigraph/dashboard");
            logger.info("默认用户名: admin");
            logger.info("默认密码: admin");
            logger.info("==================================================");
        };
    }
} 