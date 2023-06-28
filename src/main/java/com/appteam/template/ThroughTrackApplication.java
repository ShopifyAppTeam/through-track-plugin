package com.appteam.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ThroughTrackApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThroughTrackApplication.class, args);
    }

}
