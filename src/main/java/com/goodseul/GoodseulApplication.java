package com.goodseul;

import data.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "data")
@ComponentScan(basePackages = "jwt")
@ComponentScan(basePackages = "social")
@EnableJpaRepositories(basePackages = "data.repository")
@EntityScan(basePackages = "data.entity")
@EnableCaching
@EnableConfigurationProperties(FileStorageProperties.class)
public class GoodseulApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodseulApplication.class, args);
    }
}
