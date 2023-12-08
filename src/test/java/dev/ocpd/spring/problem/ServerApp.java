package dev.ocpd.spring.problem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApp {

    public static void main(String[] args) {
        var app = new SpringApplication(ServerApp.class);
        app.run(args);
    }
}
