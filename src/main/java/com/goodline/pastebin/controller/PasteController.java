package com.goodline.pastebin.controller;

import com.goodline.pastebin.exceptions.NotFoundException;
import com.goodline.pastebin.model.Paste;
import com.goodline.pastebin.repos.PasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

}