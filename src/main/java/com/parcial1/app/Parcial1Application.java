package com.parcial1.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan (basePackages = {"com.parcial1.controller"})
public class Parcial1Application {

    public static void main(String[] args) {
        SpringApplication.run(Parcial1Application.class, args);
    }


}
