package com.example.movieapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.movieapp.databinding.FragmentDetailScreenBinding
import com.example.movieapp.models.MovieDetailResponse
import com.example.movieapp.network.retrofit.Retrofit
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailScreenFragment : Fragment() {

    private var _binding: FragmentDetailScreenBinding? = null
    private val binding get() = _binding!!
    var str = ""

    private val args by navArgs<DetailScreenFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        getMovieDetail()

        binding.detailScreenBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.detailScreenDiscoverBtn.setOnClickListener {
            val action =
                DetailScreenFragmentDirections.actionDetailScreenFragmentToDiscoverScreenFragment(str)
            findNavController().navigate(action)
        }

        return view
    }

    private fun getMovieDetail() {
        val service = Retrofit.getMovieService()
        service.getMovieDetail(args.movieId).enqueue(object : Callback<MovieDetailResponse> {
            override fun onResponse(
                call: Call<MovieDetailResponse>,
                response: Response<MovieDetailResponse>
            ) {
                response.body()?.let { responseBody ->

                    val imageUrl = "https://image.tmdb.org/t/p/w500${responseBody.poster_path}"
                    Picasso.get().load(imageUrl).into(binding.detailScreenImageView)

                    str = responseBody.genres.map { it.id.toString() }.toTypedArray().joinToString("%20or%20")
                    //Log.d("TAG", "onResponse: $str")

                    binding.detailScreenTitle.text = responseBody.title
                    binding.detailScreenOverview.text = responseBody.overview
                    val rate = responseBody.vote_average.toString() + "/10"
                    binding.detailScreenRate.text = rate
                }
            }

            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message}", t)
            }

        })

    }

}