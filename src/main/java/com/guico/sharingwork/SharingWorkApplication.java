package com.guico.sharingwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})

public class SharingWorkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SharingWorkApplication.class, args);
    }

}
