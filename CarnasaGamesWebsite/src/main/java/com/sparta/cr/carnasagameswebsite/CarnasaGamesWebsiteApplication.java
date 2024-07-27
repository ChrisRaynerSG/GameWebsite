package com.sparta.cr.carnasagameswebsite;

import com.sparta.cr.carnasagameswebsite.models.User;
import com.sparta.cr.carnasagameswebsite.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CarnasaGamesWebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarnasaGamesWebsiteApplication.class, args);
	}

//	@Bean
//	CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//		return args -> {
//			userRepository.save(new User("admin", "admin@admin.com", passwordEncoder.encode("admin"), "ROLE_ADMIN"));
//		};
//	}
}
