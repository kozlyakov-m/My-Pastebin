package com.goodline.pastebin.controller;

import com.goodline.pastebin.exceptions.NotFoundException;
import com.goodline.pastebin.model.Paste;
import com.goodline.pastebin.repos.PasteRepository;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sun.security.util.SecurityProperties;

import java.net.URI;
import java.security.Security;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api")
public class PasteController {

    @Autowired
    private PasteRepository repository;

    @GetMapping
    public List<Paste> getTen() {
        return repository.findTop10ByIsPrivateOrderByIdDesc(false);
    }


    @PostMapping("/")
    public ResponseEntity<String> newPaste(@RequestBody Paste paste) {
        UUID uniqueKey = UUID.randomUUID();
        paste.setHash(String.valueOf(uniqueKey));

        repository.save(paste);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/" + uniqueKey));
        String content = "{ \"message\": \"Paste has been saved\", \"hash\": \"" + uniqueKey + "\"";
        return new ResponseEntity<>(content, headers, HttpStatus.CREATED);
    }

    @GetMapping("/{hash}")
    public Paste getOne(@PathVariable String hash) {
        Paste response = repository.findByHash(hash);
        if (response == null) {
            throw new NotFoundException();
        } else {
            return response;
        }
    }

    @PutMapping("/edit/{hash}")
    public ResponseEntity<String> editPaste(@PathVariable String hash, @RequestBody Paste newPaste) {
        Paste oldPaste = repository.findByHash(hash);
        if ( oldPaste == null) {
            throw new NotFoundException();
        } else {
            oldPaste.setText(newPaste.getText());
            oldPaste.setExpireDate(newPaste.getExpireDate());
            oldPaste.setIsPrivate(newPaste.isPrivate());
            repository.save(oldPaste);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/" + hash));
            String content = "{ \"message\": \"Paste has been saved\", \"hash\": \"" + hash + "\"";
            return new ResponseEntity<>(content, headers, HttpStatus.OK);
        }
    }

    @GetMapping("/my")
    public String my(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return name;
    }

    @GetMapping("/foo")
    public String foo(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return name;
    }

}