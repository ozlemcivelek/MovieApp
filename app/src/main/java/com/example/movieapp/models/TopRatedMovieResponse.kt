package com.example.movieapp.models

data class TopRatedMovieResponse(
    val page: Int,
    val results: List<MovieModel>,
    val total_pages: Int,
    val total_results: Int,
)
