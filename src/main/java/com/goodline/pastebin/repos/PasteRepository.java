package com.goodline.pastebin.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goodline.pastebin.model.Paste;

public interface PasteRepository extends JpaRepository<Paste, Integer> {
    Paste findByHash(String hash);
}
