
package com.sb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableFeignClients //needs to be on SBA class
@SpringBootApplication
public class TransactionApplication {
	public static void main(String[] args) {
		SpringApplication.run(TransactionApplication.class, args);
	}
}
