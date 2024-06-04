package com.example.githubworkflow1.repository;

import com.example.githubworkflow1.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}

