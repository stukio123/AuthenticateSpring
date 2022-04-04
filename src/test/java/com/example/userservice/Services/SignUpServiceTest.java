package com.example.userservice.Services;

import com.example.userservice.Models.SignUpRequest;
import com.example.userservice.Repositories.TokenRepository;
import com.example.userservice.Repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Closeable;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class SignUpServiceTest {
    @Rule
    public MockitoRule initRule = MockitoJUnit.rule();

    private Closeable closeable;

    @Spy
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private TokenService tokenService;
    @Mock
    private UserService userService;

    @InjectMocks
    private SignUpService signUpService;

    private SignUpRequest signUpRequest;

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.signUpRequest = new SignUpRequest(
                "abc",
                "yxz",
                "abcxyz@gmail.com",
                "Abc Xyz"
        );
    }

    @Test
    public void signUp() {
        String payload = UUID.randomUUID().toString();
        Mockito.when(signUpService.signUp(this.signUpRequest)).thenReturn(payload);
        assertEquals(payload,signUpService.signUp(signUpRequest));
    }

    @Test
    public void confirmToken() {
    }
}