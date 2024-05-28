package com.berk2s.ds.api;

import com.berk2s.ds.api.application.shared.ApplicationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(basePackages = "com.berk2s",
		includeFilters = {
				@ComponentScan.Filter(type = FilterType.ANNOTATION, value = {ApplicationService.class})
		})
//@EnableSwagger2
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}
