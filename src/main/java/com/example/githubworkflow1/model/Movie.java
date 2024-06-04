package com.example.githubworkflow1.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long movieId;
    private String movieName;
    private int movieYear;
    private double movieRating;

    public Movie(String movieName, int movieYear, double movieRating) {
        this.movieName = movieName;
        this.movieYear = movieYear;
        this.movieRating = movieRating;
    }
}

