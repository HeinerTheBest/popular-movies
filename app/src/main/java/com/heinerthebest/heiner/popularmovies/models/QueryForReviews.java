package com.heinerthebest.heiner.popularmovies.models;

import java.util.ArrayList;

public class QueryForReviews
{
    private String id;
    private int page;
    private int total_pages;
    private int total_results;
    private ArrayList<Review> results;

    public QueryForReviews(String id, int page, int total_pages, int total_results, ArrayList<Review> reviews) {
        this.id = id;
        this.page = page;
        this.total_pages = total_pages;
        this.total_results = total_results;
        results = reviews;
    }

    public ArrayList<Review> getResults() {
        return results;
    }

    public void setResults(ArrayList<Review> results) {
        this.results = results;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }
}
