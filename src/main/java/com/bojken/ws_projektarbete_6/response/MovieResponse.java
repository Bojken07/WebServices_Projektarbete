package com.bojken.ws_projektarbete_6.response;

import com.bojken.ws_projektarbete_6.model.Movie;

public class MovieResponse  extends Movie implements WsResponse {
    public MovieResponse(Movie movie) {
        super(movie.getId(), movie.getTitle(), movie.getOverview(), movie.getMovie_review(), movie.getRelease_date(),
                movie.getOriginCountry(),movie.getVoteAverage(), movie.getBudget());
    }

}