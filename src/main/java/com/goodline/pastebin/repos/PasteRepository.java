package com.goodline.pastebin.repos;

import com.goodline.pastebin.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import com.goodline.pastebin.model.Paste;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasteRepository extends JpaRepository<Paste, Integer> {

    Paste findByHash(String hash);

    List<Paste> findTop10ByTypeOrderByIdDesc(Type type);

}
