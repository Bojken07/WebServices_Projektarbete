package com.bojken.ws_projektarbete_6.dao;

import com.bojken.ws_projektarbete_6.model.Movie;

import java.util.List;

public interface MovieDAO {

    List<Movie> findByTitle(String title);
    List<Movie> findByTitleAndOriginCountry(String title, List<String> originCountry);
    List<Movie> findAllOrderByBudgetDesc();
    List<Movie> findAllOrderByVoteAverageDesc();

}