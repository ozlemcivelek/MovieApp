package com.example.movieapp.network.service

import com.example.movieapp.BuildConfig
import com.example.movieapp.models.MovieDetailResponse
import com.example.movieapp.models.TopRatedMovieResponse
import com.example.movieapp.models.UpcomingMovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {

    @GET(
        "movie/upcoming?" +
                "&api_key=${BuildConfig.API_KEY}"
    )
    fun getUpcomingMovies(): Call<UpcomingMovieResponse>

    @GET(
        "movie/top_rated?" +
                "&api_key=${BuildConfig.API_KEY}"
    )
    fun getTopRatedMovies(): Call<TopRatedMovieResponse>

    @GET(
        "movie/{movie_id}?" +
                "&api_key=${BuildConfig.API_KEY}"
    )
    fun getMovieDetail(@Path("movie_id") movieId: Int): Call<MovieDetailResponse>
}