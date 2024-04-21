package com.alemkhan.webscoketdemo;

import com.alemkhan.webscoketdemo.user.User;
import com.alemkhan.webscoketdemo.user.UserRepository;
import com.alemkhan.webscoketdemo.user.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            userRepository.saveAll(List.of(

                    User.builder()
                    .name("User 2")
                    .username("user2")
                    .password(passwordEncoder.encode("pswd123456"))
                    .role(UserRole.ROLE_USER)
                    .build(),

                    User.builder()
                    .name("User 1")
                    .username("user1")
                    .password(passwordEncoder.encode("pswd123456"))
                    .role(UserRole.ROLE_USER)
                    .build(),

                    User.builder()
                            .name("Nieco")
                            .username("user3")
                            .password(passwordEncoder.encode("pswd123456"))
                            .role(UserRole.ROLE_USER)
                            .build())

            );
        };
    }
}
