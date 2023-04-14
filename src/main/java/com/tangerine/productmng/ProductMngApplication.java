package com.tangerine.productmng;

import com.tangerine.productmng.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
@EnableConfigurationProperties({JwtProperties.class})
public class ProductMngApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductMngApplication.class, args);
    }

}
