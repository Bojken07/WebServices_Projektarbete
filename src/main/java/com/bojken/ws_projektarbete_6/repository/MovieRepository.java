package com.bojken.ws_projektarbete_6.repository;

import com.bojken.ws_projektarbete_6.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT m FROM Movie m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Movie> findByTitle(@Param("title") String title);
    List<Movie> findByTitleAndOriginCountry(String title, List<String> originCountry);

    List<Movie> findAllByOrderByBudgetDesc();

    List<Movie> findAllByOrderByVoteAverageDesc();
}