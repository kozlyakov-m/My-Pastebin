package com.goodline.pastebin;

import com.goodline.pastebin.controller.RegistrationController;
import com.goodline.pastebin.model.PastebinUser;
import com.goodline.pastebin.repos.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class RegistrationControllerTest {

    @Autowired
    private RegistrationController registrationController;

    @MockBean
    UserRepository repository;

    @MockBean
    PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setMocks() {
        Mockito.when(repository.findByLogin("user"))
                .thenReturn(new PastebinUser("user", "pass"));
        Mockito.when(repository.findByLogin("test"))
                .thenReturn(null);
    }

    @Test
    @DisplayName("Успешное добавление пользователя")
    public void createUser() {
        PastebinUser user = new PastebinUser("test", "pass");

        ResponseEntity<String> result;
        result = registrationController.newUser(user);

        Mockito.verify(repository).save(user);

        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    @DisplayName("Попытка создать пользователя с сущестующим именем")
    public void tryCreateExistingUser() {
        PastebinUser user = new PastebinUser("user", "pass");

        ResponseEntity<String> result;
        result = registrationController.newUser(user);


        Assertions.assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
    }

    @Test
    @DisplayName("Попытка создать пользователя с пустым логином")
    public void emptyLogin() {
        PastebinUser user = new PastebinUser( null, "pass");

        ResponseEntity<String> result;
        result = registrationController.newUser(user);


        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    @DisplayName("Попытка создать пользователя с пустым паролем")
    public void emptyPassword() {
        PastebinUser user = new PastebinUser( "login", null);

        ResponseEntity<String> result;
        result = registrationController.newUser(user);


        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }


}
