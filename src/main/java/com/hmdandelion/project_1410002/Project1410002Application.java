package com.hmdandelion.project_1410002;

import jdk.jfr.Enabled;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Project1410002Application {

    public static void main(String[] args) {
        SpringApplication.run(Project1410002Application.class, args);
    }

}
