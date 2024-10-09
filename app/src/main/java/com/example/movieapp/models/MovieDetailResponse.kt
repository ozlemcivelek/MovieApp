package com.example.movieapp.models

data class MovieDetailResponse(
    val adult: Boolean,
    val backdrop_path: String,
    val budget: Int,
    val genres: List<GenresDataModel>,
    val homepage: String,
    val id: Int,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companies: List<CompaniesModel>,
    val production_countries: List<CountriesModel>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val spoken_languages: List<SpokenLanguagesModel>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

data class GenresDataModel(
    val id: Int,
    val name: String
)

data class CompaniesModel(
    val id: Int,
    val logo_path: String,
    val name: String,
    val origin_country: String
)

data class CountriesModel(
    val iso_3166_1: String,
    val name: String
)

data class SpokenLanguagesModel(
    val english_name: String,
    val iso_639_1: String,
    val name: String
)
