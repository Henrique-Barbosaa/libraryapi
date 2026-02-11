package com.henriq.libraryapi.config;

import com.henriq.libraryapi.model.Roles;
import com.henriq.libraryapi.model.User;
import com.henriq.libraryapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataInitializer {

    @Value("${app.admin.password}")
    private String password;

    @Value("${app.admin.email}")
    private String email;

    @Bean
    public CommandLineRunner initDatabase(UserRepository repository, PasswordEncoder encoder) {
        return args -> {

            if (repository.findByEmail(email).isEmpty()) {

                User admin = new User();
                admin.setEmail(email);
                admin.setPassword(encoder.encode(password));
                admin.setRoles(List.of(Roles.ADMIN, Roles.USER, Roles.LIBRARIAN));

                repository.save(admin);
                System.out.println("\n >>> Super Admin criado com sucesso! <<< \n");
            } else {
                System.out.println("\n >>> Super Admin já existe no banco. <<<  \n");
            }
        };
    }
}