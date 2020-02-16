package com.goodline.pastebin.model;


import javax.persistence.*;
import java.util.Date;


@Entity
//@Table(name = "Paste")
public class Paste{

    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String text;
    private String hash;
    private Date expireDate;
    private boolean isPrivate;

    protected Paste(){}

    public Paste(String text, String hash) {
        this.text = text;
        this.hash = hash;
    }

    public Paste(String text, String hash, Date expireDate, boolean isPrivate) {
        this.text = text;
        this.hash = hash;
        this.expireDate = expireDate;
        this.isPrivate = isPrivate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText(){
        return text;
    }


    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }
}
