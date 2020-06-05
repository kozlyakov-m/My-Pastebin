package com.goodline.pastebin.controller;

import com.goodline.pastebin.exceptions.NoAccessException;
import com.goodline.pastebin.exceptions.NotFoundException;
import com.goodline.pastebin.model.Paste;
import com.goodline.pastebin.model.Type;
import com.goodline.pastebin.repos.PasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class PrivateController {

    @Autowired
    private PasteRepository repository;

    @PutMapping("/edit/{hash}")
    public ResponseEntity<String> editPaste(@PathVariable String hash, @RequestBody Paste newPaste) {
        Paste oldPaste = repository.findByHash(hash);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ( oldPaste == null) {
            throw new NotFoundException();
        } else if(!Objects.equals(oldPaste.getAuthor(), auth.getName())) { //Objects.equals не выкидывает NPE
            throw new NoAccessException();
        }
        else {
            oldPaste.setText(newPaste.getText());
            oldPaste.setExpireDate(newPaste.getExpireDate());
            oldPaste.setType(newPaste.getType());
            repository.save(oldPaste);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/" + hash));
            String content = "{ \"message\": \"Paste has been saved\", \"hash\": \"" + hash + "\"";
            return new ResponseEntity<>(content, headers, HttpStatus.OK);
        }
    }

    @GetMapping("/my-pastes")
    public List<Paste> myPastes(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return repository.findByAuthor(auth.getName());
    }

}