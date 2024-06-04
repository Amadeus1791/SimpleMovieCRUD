package com.example.githubworkflow1.service;

import com.example.githubworkflow1.model.Movie;
import com.example.githubworkflow1.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.orElse(null);
    }

    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, Movie movieDetails) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()) {
            Movie existingMovie = movie.get();
            existingMovie.setMovieName(movieDetails.getMovieName());
            existingMovie.setMovieYear(movieDetails.getMovieYear());
            existingMovie.setMovieRating(movieDetails.getMovieRating());
            return movieRepository.save(existingMovie);
        } else {
            return null;
        }
    }

    public boolean deleteMovie(Long id) {
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}


