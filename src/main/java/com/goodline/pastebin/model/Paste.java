package com.goodline.pastebin.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity

public class Paste{

    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String text;
    private String hash;

    protected Paste(){}

    public Paste(String text, String hash) {
        this.text = text;
        this.hash = hash;
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
}
