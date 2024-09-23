package com.bojken.ws_projektarbete_6.response;

import com.bojken.ws_projektarbete_6.model.Movie;

import java.util.List;

public class ListResponse implements WsResponse{
    private List<Movie> movies;

    public ListResponse(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}