package com.heinerthebest.heiner.popularmovies.models;


public class Review
{
    private String id;
    private String author;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Review(String id,String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
