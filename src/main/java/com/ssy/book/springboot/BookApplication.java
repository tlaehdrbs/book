package com.ssy.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.system.ApplicationPid;

//@EnableJpaAuditing // JPA Auditing 활성화 -> 별도 Config로 분리
@SpringBootApplication
public class BookApplication {

	public static void main(String[] args) {
		// SpringApplication.run(BookApplication.class, args);
		SpringApplication application = new SpringApplication(BookApplication.class);
		application.addListeners(new ApplicationPidFileWriter());
		application.run(args);
	}

}
