package com.heinerthebest.heiner.popularmovies.models;

import java.util.ArrayList;

public class QueryForTrailers
{

    private String id;
    private ArrayList<Trailer> results;

    public QueryForTrailers(String id, ArrayList<Trailer> results) {
        this.id = id;
        this.results = results;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Trailer> getResults() {
        return results;
    }

    public void setResults(ArrayList<Trailer> results) {
        this.results = results;
    }
}
