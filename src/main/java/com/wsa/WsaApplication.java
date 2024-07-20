package com.wsa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.env.Environment;

@SpringBootApplication
@RestController
public class WsaApplication implements CommandLineRunner{

	@Autowired
	private Environment env;
//test
	@GetMapping("/")
	public String home() {
		return "Spring is here!";
	}

	public static void main(String[] args) {
		SpringApplication.run(WsaApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		String staticLocations = env.getProperty("spring.web.resources.static-locations");
		System.out.println("Static locations: " + staticLocations);
	}
}
