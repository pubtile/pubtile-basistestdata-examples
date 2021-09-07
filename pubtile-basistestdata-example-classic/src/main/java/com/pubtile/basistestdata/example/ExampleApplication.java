package com.pubtile.basistestdata.example;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.pubtile.basistestdata.example.*.mapper")
@EnableTransactionManagement
public class ExampleApplication {
	private static final Logger logger = LoggerFactory.getLogger(ExampleApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ExampleApplication.class, args);
		logger.info("========================启动完毕========================");
	}
}
