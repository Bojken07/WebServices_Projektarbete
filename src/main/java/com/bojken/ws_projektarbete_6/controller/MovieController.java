package com.bojken.ws_projektarbete_6.controller;

import com.bojken.ws_projektarbete_6.model.Movie;
import com.bojken.ws_projektarbete_6.repository.MovieRepository;
import com.bojken.ws_projektarbete_6.response.WsResponse;
import com.bojken.ws_projektarbete_6.service.ApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MovieController {

    private final MovieRepository movieRepository;
    private final ApiService apiService;

    public MovieController(ApiService apiService, MovieRepository movieRepository) {
        this.apiService = apiService;
        this.movieRepository = movieRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<WsResponse> getMovie(@PathVariable Long id) {

        return apiService.getMovieById(id);
    }

    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getMovies() {

        return ResponseEntity.ok(movieRepository.findAll());
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<WsResponse> searchMovie(@PathVariable("title") String title,
                                                  @RequestParam(value = "country", required = false) List<String> country) {

        return apiService.searchMoviesByTitleAndCountry(title, country);
    }

    @GetMapping("/budget")
    public ResponseEntity<List<Movie>> getBudgetOfMovies() {

        return apiService.budgetHighToLow();
    }

    @GetMapping("/rating")
    public ResponseEntity<List<Movie>> getRatingOfMovies() {

        return apiService.ratingHighToLow();
    }

    @PostMapping("/{id}/review")
    public ResponseEntity<WsResponse> postReview(@PathVariable Long id, @RequestBody Movie movie) {

        return apiService.postAReviewById(id, movie);
    }

    @PostMapping("/{id}/save")
    public ResponseEntity<WsResponse> saveMovie(@PathVariable Long id) {

        return apiService.saveMovieById(id);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<WsResponse> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {

        return apiService.updateMovieCredentialsById(id, movie);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id) {

        return apiService.deleteMovieById(id);
    }
}