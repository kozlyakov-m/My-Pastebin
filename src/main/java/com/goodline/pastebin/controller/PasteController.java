package com.goodline.pastebin.controller;

import com.goodline.pastebin.exceptions.NotFoundException;
import com.goodline.pastebin.model.Paste;
import com.goodline.pastebin.repos.PasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
public class PasteController{

    HashMap<Integer, Paste> pastes = new HashMap<>();

    @Autowired
    private PasteRepository repository;




    /*@RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }*/
    @GetMapping
    public List<Paste> getTen(){
        return repository.findTop10ByIsPrivateOrderByIdDesc(false);
    }



    @PostMapping("/")
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> newPaste(@RequestBody Paste paste){
        UUID uniqueKey = UUID.randomUUID();
        paste.setHash(String.valueOf(uniqueKey));

        repository.save(paste);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"+uniqueKey));
        String content = "{ message: Paste has been saved, url: " + uniqueKey;
        return new ResponseEntity<>(content, headers, HttpStatus.CREATED);
        //return "paste has been saved\n" + uniqueKey+"\nexpire date: "+ paste.getExpireDate();
    }

    @GetMapping("/{hash}")
    public Paste getOne(@PathVariable String hash) {
        Paste response = repository.findByHash(hash);
        if(response == null) {
            return response;
        }
        else{
            throw new NotFoundException();
        }
    }

}