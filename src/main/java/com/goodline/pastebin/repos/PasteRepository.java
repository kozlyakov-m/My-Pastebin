package com.goodline.pastebin.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goodline.pastebin.model.Paste;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasteRepository extends JpaRepository<Paste, Integer> {

    Paste findByHash(String hash);

    //@Query("SELECT p FROM Paste p WHERE p.type=false ORDER BY p.id DESC")
    //List<Paste> findLast10();


    //List<Paste> findTop10ByIsPrivateOrderByIdDesc(boolean isPrivate);
    List<Paste> findTop10ByTypeOrderByIdDesc(boolean type);

}
