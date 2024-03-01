package com.example.security.jwt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.example.security.jwt.dao.mapper")
public class SecurityJwtApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(SecurityJwtApplication.class,args);
    }
}
