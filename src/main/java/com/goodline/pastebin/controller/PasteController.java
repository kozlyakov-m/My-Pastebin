package com.goodline.pastebin.controller;

import com.goodline.pastebin.model.Paste;
import com.goodline.pastebin.repos.PasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

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
    public String newPaste(@RequestBody Paste paste){
        UUID uniqueKey = UUID.randomUUID();
        paste.setHash(String.valueOf(uniqueKey));

        repository.save(paste);
        return "paste has been saved\n"+ uniqueKey+"\nexpire date: "+paste.getExpireDate();
    }

    @GetMapping("/{hash}")
    public Paste getOne(@PathVariable String hash) {
        return repository.findByHash(hash);
    }

}