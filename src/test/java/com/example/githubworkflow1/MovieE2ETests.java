package com.example.githubworkflow1;

import com.example.githubworkflow1.model.Movie;
import com.example.githubworkflow1.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MovieE2ETests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    public void setUp() {
        movieRepository.deleteAll();
    }

    @Test
    public void shouldCreateAndFetchMovie() throws Exception {
        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"movieName\":\"Inception\", \"movieYear\":2010, \"movieRating\":8.8}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieName").value("Inception"))
                .andExpect(jsonPath("$.movieYear").value(2010))
                .andExpect(jsonPath("$.movieRating").value(8.8));

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].movieName").value("Inception"))
                .andExpect(jsonPath("$[0].movieYear").value(2010))
                .andExpect(jsonPath("$[0].movieRating").value(8.8));
    }

    @Test
    public void shouldFetchMovieById() throws Exception {
        Movie movie = new Movie("Interstellar", 2014, 8.6);
        movie = movieRepository.save(movie);

        mockMvc.perform(get("/movies/" + movie.getMovieId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieName").value(movie.getMovieName()))
                .andExpect(jsonPath("$.movieYear").value(movie.getMovieYear()))
                .andExpect(jsonPath("$.movieRating").value(movie.getMovieRating()));
    }

    @Test
    public void shouldUpdateMovie() throws Exception {
        Movie movie = new Movie("Interstellar", 2014, 8.6);
        movie = movieRepository.save(movie);

        mockMvc.perform(put("/movies/" + movie.getMovieId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"movieName\":\"Interstellar\", \"movieYear\":2014, \"movieRating\":9.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieName").value(movie.getMovieName()))
                .andExpect(jsonPath("$.movieYear").value(movie.getMovieYear()))
                .andExpect(jsonPath("$.movieRating").value(movie.getMovieRating()));

        // Fetch the updated movie
        mockMvc.perform(get("/movies/" + movie.getMovieId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieName").value(movie.getMovieName()))
                .andExpect(jsonPath("$.movieYear").value(movie.getMovieYear()))
                .andExpect(jsonPath("$.movieRating").value(movie.getMovieRating()));
    }

    @Test
    public void shouldDeleteMovie() throws Exception {
        Movie movie = new Movie("Interstellar", 2014, 8.6);
        movie = movieRepository.save(movie);

        mockMvc.perform(delete("/movies/" + movie.getMovieId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/movies/" + movie.getMovieId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnNotFoundForNonExistingMovie() throws Exception {
        mockMvc.perform(get("/movies/999"))
                .andExpect(status().isNotFound());

        mockMvc.perform(put("/movies/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"movieName\":\"Non-Existing\", \"movieYear\":2000, \"movieRating\":5.0}"))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/movies/999"))
                .andExpect(status().isNotFound());
    }
}



