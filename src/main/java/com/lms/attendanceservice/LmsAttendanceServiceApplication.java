package com.lms.attendanceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LmsAttendanceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmsAttendanceServiceApplication.class, args);
	}

}
