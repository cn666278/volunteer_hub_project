package com.wsa;

import com.wsa.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.env.Environment;

@SpringBootApplication
@RestController
@EnableAsync
public class WsaApplication implements CommandLineRunner{

	@Autowired
	private Environment env;
//test
	@GetMapping("/")
	public String home() {
		return "Spring is here!";
	}

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(WsaApplication.class, args);
//		EmailService mailService = ctx.getBean(EmailService.class);
//		mailService.sendTestMail();
	}
	@Override
	public void run(String... args) throws Exception {
		String staticLocations = env.getProperty("spring.web.resources.static-locations");
		System.out.println("Static locations: " + staticLocations);
	}
}
