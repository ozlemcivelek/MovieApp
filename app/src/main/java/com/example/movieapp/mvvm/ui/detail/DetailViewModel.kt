package com.example.movieapp.mvvm.ui.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.models.MovieDetailResponse
import com.example.movieapp.network.retrofit.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private var service = Retrofit.getMovieService()

    var movieDetail: MovieDetailResponse? = null
    var resultMovieDetail: MutableLiveData<Boolean> = MutableLiveData()


    fun getMovieDetail(movieId: Int) {

        if (movieDetail != null) return
        service.getMovieDetail(movieId).enqueue(object : Callback<MovieDetailResponse> {
            override fun onResponse(
                call: Call<MovieDetailResponse>,
                response: Response<MovieDetailResponse>
            ) {
                if (response.isSuccessful) {
                    movieDetail = response.body()
                    resultMovieDetail.value = true

                }

            }

            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message}", t)
            }

        })
    }


}