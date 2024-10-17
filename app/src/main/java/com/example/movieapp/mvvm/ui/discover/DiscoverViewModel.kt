package com.example.movieapp.mvvm.ui.discover

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.models.MovieModel
import com.example.movieapp.models.TopRatedMovieResponse
import com.example.movieapp.network.retrofit.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiscoverViewModel : ViewModel() {

    private val service = Retrofit.getMovieService()

    val discoverMovies: MutableLiveData<List<MovieModel>> = MutableLiveData(emptyList())

    fun topDiscoverMovies(genresId: String ){
        service.getDiscoverMovies(genresId).enqueue(object: Callback<TopRatedMovieResponse> {
            override fun onResponse(
                call: Call<TopRatedMovieResponse>,
                response: Response<TopRatedMovieResponse>
            ) {

                if (response.isSuccessful) {
                    response.body()?.results?.let { safeResult ->
                        discoverMovies.value = safeResult
                    }
                }
            }
            override fun onFailure(call: Call<TopRatedMovieResponse>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message}", t)
            }

        })
    }



}