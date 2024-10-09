package com.example.movieapp.models

data class UpcomingMovieResponse(
    val dates: DateModel,
    val page: Int,
    val results: List<MovieModel>,
    val total_pages: Int,
    val total_results: Int,
)
