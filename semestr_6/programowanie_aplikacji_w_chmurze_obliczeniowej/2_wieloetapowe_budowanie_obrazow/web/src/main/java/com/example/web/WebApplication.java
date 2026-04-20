package com.example.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@RestController
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @GetMapping
    public String index() {
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            String hostname = InetAddress.getLocalHost().getHostName();
            String version = System.getenv("VERSION");
            return "IP address: " + ip + "\nHostname: " + hostname + "\nApp version: " + version;
        } catch (UnknownHostException e) {
            return "Oops! Something went wrong.";
        }
    }
}
