package com.example.linkly.config;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource("classpath:dbconfig.properties")
public class DbConfig {
    @Value("${db_name}")
    private String dbName;

    @Value("${db_user}")
    private String dbUser;

    @Value("${db_password}")
    private String dbPassword;
}
