package com.goodline.pastebin.repos;

import com.goodline.pastebin.model.PastebinUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<PastebinUser, Integer> {

    PastebinUser findByLogin(String login);
}
