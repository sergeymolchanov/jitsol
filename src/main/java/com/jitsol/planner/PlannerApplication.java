package com.jitsol.planner;

import com.jitsol.planner.web.IndexController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(new Object[] {PlannerApplication.class}, args);
	}
}
