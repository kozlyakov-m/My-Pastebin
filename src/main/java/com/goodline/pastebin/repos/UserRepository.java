package com.goodline.pastebin.repos;

import com.goodline.pastebin.model.PastebinUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<PastebinUser, Integer> {

}
