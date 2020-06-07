package com.goodline.pastebin.controller;

import com.goodline.pastebin.model.Paste;
import com.goodline.pastebin.repos.PasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private PasteRepository repository;


    @GetMapping()
    public List<Paste> searchByText(@RequestParam String text) {
        return repository.findByTextContains(text);
    }

}