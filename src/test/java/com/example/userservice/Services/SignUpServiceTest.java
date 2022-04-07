package com.example.userservice.Services;

import com.example.userservice.DTO.UserDTO;
import com.example.userservice.Models.ResponseMessages;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Closeable;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class SignUpServiceTest {
    @Rule
    public MockitoRule initRule = MockitoJUnit.rule();

    private Closeable closeable;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private UserService userService;

    private UserDTO signUpRequest = new UserDTO(
            "abc",
            "yxz",
            "abcxyz@gmail.com",
            "Abc Xyz"
    );

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void signUp() {
        ResponseMessages expected =  new ResponseMessages();
        expected.setMessages("Sign Up Sucessful !!");
        expected.setCode(HttpStatus.CREATED.name());
        ResponseMessages actual = new ResponseMessages();
        actual = userService.signUp(signUpRequest);
        assertEquals(expected,actual);
        assertNotNull(actual);
    }

    @Test
    public void test_SignUpAccountAlreadyExist(){
        ResponseMessages expected =  new ResponseMessages();
        expected.setMessages(String.format("%s already exist",signUpRequest.getUsername()));
        expected.setCode(HttpStatus.NOT_ACCEPTABLE.name());
        ResponseMessages actual = new ResponseMessages();
        actual = userService.signUp(signUpRequest);
        ResponseMessages actual1 = new ResponseMessages();
        actual1 = userService.signUp(signUpRequest);
        assertEquals(expected,actual1);
        assertNull(actual1);
    }

    @Test
    public void confirmToken() {
    }
}