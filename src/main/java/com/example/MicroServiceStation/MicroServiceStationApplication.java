package com.example.MicroServiceStation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroServiceStationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroServiceStationApplication.class, args);
	}

}
