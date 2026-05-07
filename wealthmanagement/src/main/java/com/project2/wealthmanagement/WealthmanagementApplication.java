package com.project2.wealthmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
public class WealthmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(WealthmanagementApplication.class, args);
	}

	@RequestMapping(value = "/**/{path:[^\\.]*}")
	public String forwardToIndex() {
		return "forward:/index.html";
	}
}