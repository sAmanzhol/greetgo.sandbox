package kz.greetgo.sandbox.backend.dao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(
    basePackages = "kz.greetgo.sandbox.backend.dao",
    sqlSessionFactoryRef = "masterSqlSessionFactory"
)
public class BeanScannerDao {}
