package com.glos.api.operationservice;

import com.glos.api.operationservice.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * @author Mykola Melnyk
 */
@SpringBootApplication
@EnableFeignClients
@EnableMongoRepositories
@EnableRetry
@EnableAsync
public class OperationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OperationServiceApplication.class, args);
    }

}
