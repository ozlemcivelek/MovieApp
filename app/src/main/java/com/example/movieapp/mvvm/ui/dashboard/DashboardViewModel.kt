package com.example.movieapp.mvvm.ui.dashboard

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.movieapp.models.MovieModel
import com.example.movieapp.models.TopRatedMovieResponse
import com.example.movieapp.models.UpcomingMovieResponse
import com.example.movieapp.network.retrofit.Retrofit
import com.example.movieapp.utils.Utility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewModel : ViewModel() {
    private var service = Retrofit.getMovieService()
    val imageList = ArrayList<SlideModel>()
    val topRatedMovies: MutableLiveData<List<MovieModel>> = MutableLiveData(emptyList())
    var upcomingMovies: MutableLiveData<List<MovieModel>> = MutableLiveData(emptyList())

    init {
        upcomingMovies()
        getTopRatedMovies()
    }

    private fun upcomingMovies() {
        service.getUpcomingMovies().enqueue(object : Callback<UpcomingMovieResponse> {
            override fun onResponse(
                call: Call<UpcomingMovieResponse>,
                response: Response<UpcomingMovieResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.results?.let { safeResult ->
                        safeResult.forEach {
                            imageList.add(
                                SlideModel(
                                    Utility.getImageUrl(it.backdrop_path),
                                    ScaleTypes.FIT
                                )
                            )
                        }
                        upcomingMovies.value = safeResult
                    }
                }
            }

            override fun onFailure(call: Call<UpcomingMovieResponse>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message}", t)
            }

        })
    }

    private fun getTopRatedMovies() {
        Log.d("TAG", "getTopRatedMovies: istek atılacak")
        service.getTopRatedMovies().enqueue(object : Callback<TopRatedMovieResponse> {
            override fun onResponse(
                call: Call<TopRatedMovieResponse>,
                response: Response<TopRatedMovieResponse>
            ) {
                Log.d("TAG", "topRatedMovies istek atıldı, yanıt alındı")
                if (response.isSuccessful) {
                    response.body()?.results?.let { safeResult ->
                        topRatedMovies.value = safeResult
                    }
                }
            }

            override fun onFailure(call: Call<TopRatedMovieResponse>, t: Throwable) {
                Log.d("TAG", "topRatedMovies istek atıldı, hata alındı")
            }
        })
        Log.d("TAG", "getTopRatedMovies: istek atıldı!")
    }

    fun getMovie(position: Int): MovieModel? {
        upcomingMovies.value?.let {
            return it[position]
        }
        return null
    }
}