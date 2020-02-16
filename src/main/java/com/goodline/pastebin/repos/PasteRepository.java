package com.goodline.pastebin.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goodline.pastebin.model.Paste;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasteRepository extends JpaRepository<Paste, Integer> {
    Paste findByHash(String hash);

    //@Query(value = "SELECT * FROM Paste WHERE isPrivate='false' ORDER BY DESC", nativeQuery = true)
    //List<Paste> findLast10();
    List<Paste> findTop10ByIsPrivateOrderByIdDesc(boolean isPrivate);

    //List<Paste> findLast10ByIsPrivateOrderById(boolean isPrivate);
}
