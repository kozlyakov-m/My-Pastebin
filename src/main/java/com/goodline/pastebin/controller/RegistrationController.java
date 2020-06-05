package com.goodline.pastebin.controller;

import com.goodline.pastebin.model.PastebinUser;
import com.goodline.pastebin.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping()
    public ResponseEntity<String> newPaste(@RequestBody PastebinUser user) {

        String rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole("USER");

        repository.save(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(user.getLogin(), rawPassword);
        headers.setLocation(URI.create("/api/my-pastes"));
        String content = "{ \"message\": \"User has been saved\"," +
                " \"login\": \"" + user.getLogin() + "\"" +
                " \"password\": \"" + rawPassword + "\"";

        return new ResponseEntity<>(content, headers, HttpStatus.CREATED);
    }
}
