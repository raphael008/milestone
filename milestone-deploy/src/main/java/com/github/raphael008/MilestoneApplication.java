package com.github.raphael008;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.github.raphael008.mapper")
public class MilestoneApplication {
    public static void main(String[] args) {
        SpringApplication.run(MilestoneApplication.class, args);
    }
}
