package com.goodline.pastebin.repos;

import com.goodline.pastebin.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import com.goodline.pastebin.model.Paste;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional

public interface PasteRepository extends JpaRepository<Paste, Integer> {

    Paste findByHash(String hash);

    List<Paste> findTop10ByTypeOrderByIdDesc(Type type);

    List<Paste> findByAuthor(String author);

    //type=0 means type=PUBLIC
    @Query("select p from Paste p where upper(p.text) like upper(?1) and (p.type = 0)")
    List<Paste> findByText(String text);

    //type=0 means type=PUBLIC
    @Query("select p from Paste p where upper(p.text) like upper(?1) " +
            "and (p.type = 0 or p.author = ?2)")
    List<Paste> findByTextAndAuthor(String text, String author);

}
