package com.goodline.pastebin.repos;

import com.goodline.pastebin.model.Paste;
import com.goodline.pastebin.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional

public interface PasteRepository extends JpaRepository<Paste, Integer> {

    @Query("select p from Paste p where p.hash = ?1 " +
            "and (p.expireDate > CURRENT_TIMESTAMP() or p.expireDate = null)")
    Paste findByHash(String hash);

//        @Query("select p from Paste p where p.type = ?1 " +
//                "and (p.expireDate > now() or p.expireDate = null) " +
//                "order by p.id desc limit 10")
    //Причина существания ЭТОГО в том, что в hql нет limit, а писать свою имплементацию репозитория я не хочу
    List<Paste> findTop10ByTypeAndExpireDateNullOrTypeAndExpireDateGreaterThanOrderByIdDesc(Type t1, Type t2, Date now);

    @Query("select p from Paste p where p.author = ?1 " +
            "and (p.expireDate > now() or p.expireDate = null)")
    List<Paste> findByAuthor(String author);

    //type=0 means type=PUBLIC
    @Query("select p from Paste p where upper(p.text) like upper(?1) " +
            "and (p.type = 0) " +
            "and (p.expireDate > now() or p.expireDate = null)")
    List<Paste> findByText(String text);

    //type=0 means type=PUBLIC
    @Query("select p from Paste p where upper(p.text) like upper(?1) " +
            "and (p.type = 0 or p.author = ?2) " +
            "and (p.expireDate > now() or p.expireDate = null)")
    List<Paste> findByTextAndAuthor(String text, String author);

}
