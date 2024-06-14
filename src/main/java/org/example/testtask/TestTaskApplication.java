package org.example.testtask;

import org.example.testtask.entity.Product;
import org.example.testtask.entity.Role;
import org.example.testtask.entity.User;
import org.example.testtask.repositories.ProductRepository;
import org.example.testtask.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@SpringBootApplication
public class TestTaskApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProductRepository productRepository;

    public TestTaskApplication(UserRepository userRepository,
                               PasswordEncoder passwordEncoder,
                               ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.productRepository = productRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(TestTaskApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.save(new User("test", "test", passwordEncoder.encode("passwordTest"), "email@gmail.com", Role.USER));
        userRepository.save(new User("test1", "test1", passwordEncoder.encode("passwordTest1"), "email1@gmail.com", Role.USER));
        userRepository.save(new User("test2", "test2", passwordEncoder.encode("passwordTest2"), "email2@gmail.com", Role.USER));
        userRepository.save(new User("test3", "test3", passwordEncoder.encode("passwordTest3"), "email3@gmail.com", Role.USER));

        var product1 = productRepository.save(new Product("Iphone11", "test1", new BigDecimal("500")));
        var product2 = productRepository.save(new Product("Iphone14", "test2", new BigDecimal("1100")));
        var product3 = productRepository.save(new Product("Iphone15", "test3", new BigDecimal("1500")));

    }
}
