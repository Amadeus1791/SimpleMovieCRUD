package com.example.githubworkflow1;

import com.example.githubworkflow1.model.Movie;
import com.example.githubworkflow1.repository.MovieRepository;
import com.example.githubworkflow1.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GitHubWorkflow1Application implements CommandLineRunner {
    @Autowired
    private MovieService movieService;

    public static void main(String[] args) {
        SpringApplication.run(GitHubWorkflow1Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
       // movieService.createMovie(new Movie("Inception", 2010, 8.8));
    }
}
