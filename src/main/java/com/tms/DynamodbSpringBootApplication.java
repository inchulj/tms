package com.tms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(excludeFilters =
//@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = BranchRepository.class))
public class DynamodbSpringBootApplication {

    public static void main(String[] args) {

        SpringApplication.run(DynamodbSpringBootApplication.class, args);
    }

}
