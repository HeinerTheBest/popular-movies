package com.heinerthebest.heiner.popularmovies.models;

import java.util.ArrayList;
import java.util.List;

public class Query
{

    private int page;
    private String total_results;
    private String total_pages;

    private ArrayList<Movie> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getTotal_results() {
        return total_results;
    }

    public void setTotal_results(String total_results) {
        this.total_results = total_results;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(String total_pages) {
        this.total_pages = total_pages;
    }

    public ArrayList<Movie> getResults() {
        return results;
    }

    public Query(int page, String total_results, String total_pages, ArrayList<Movie> results) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
        this.results = results;
    }

    public void setResults(ArrayList<Movie> results) {
        this.results = results;
    }
}
