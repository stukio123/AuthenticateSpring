package com.example.userservice.Repositories;

import com.example.userservice.Models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class UserRepositoryTest {

    private final UserRepository userRepository;
    private final TestEntityManager entityManager;

    UserRepositoryTest(UserRepository userRepository, TestEntityManager entityManager) {
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    void test_CreateUser(){
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setEmail("");
        user.setName("");
    }
}