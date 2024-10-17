package com.example.movieapp.utils

object Utility {

    fun getImageUrl(path : String) : String{
        val imageBaseUrl = "https://image.tmdb.org/t/p/w500"
        return imageBaseUrl + path
    }
}