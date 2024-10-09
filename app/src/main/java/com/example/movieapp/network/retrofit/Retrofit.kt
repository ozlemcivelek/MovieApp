package com.example.movieapp.network.retrofit

import com.example.movieapp.network.service.MovieService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {

    private val movieURL = "https://api.themoviedb.org/3/"

    fun getMovieService() : MovieService {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(movieURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofitBuilder.create(MovieService::class.java)
    }

}