package com.wibmo.otpfraud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.wibmo.*")
public class OtpfraudApplication {

	public static void main(String[] args) {
		SpringApplication.run(OtpfraudApplication.class, args);
	}

}
