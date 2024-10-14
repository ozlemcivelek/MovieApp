package com.example.movieapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemChangeListener
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.movieapp.adapter.RecyclerViewAdapter
import com.example.movieapp.databinding.FragmentDashboardScreenBinding
import com.example.movieapp.models.MovieModel
import com.example.movieapp.models.TopRatedMovieResponse
import com.example.movieapp.models.UpcomingMovieResponse
import com.example.movieapp.network.retrofit.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardScreenFragment : Fragment() {

    private var _binding: FragmentDashboardScreenBinding? = null
    private val binding get() = _binding!!
    val imageList = ArrayList<SlideModel>()
    private var recyclerViewAdapter: RecyclerViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_LONG).show()
            }

        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //recyclerView
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager

        upcomingMovies()
        topRatedMovies()
    }

    private fun upcomingMovies() {
        val service = Retrofit.getMovieService()
        service.getUpcomingMovies().enqueue(object : Callback<UpcomingMovieResponse> {
            override fun onResponse(
                call: Call<UpcomingMovieResponse>,
                response: Response<UpcomingMovieResponse>
            ) {
                upcomingResponseIsSuccessful(response)
            }

            override fun onFailure(call: Call<UpcomingMovieResponse>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message}", t)
            }

        })
    }

    private fun topRatedMovies(){
        val service = Retrofit.getMovieService()
        service.getTopRatedMovies().enqueue(object: Callback<TopRatedMovieResponse> {
            override fun onResponse(
                call: Call<TopRatedMovieResponse>,
                response: Response<TopRatedMovieResponse>
            ) {
                topRatedResponseIsSuccessful(response)
            }

            override fun onFailure(call: Call<TopRatedMovieResponse>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message}", t)
            }

        })
    }

    private fun topRatedResponseIsSuccessful(response: Response<TopRatedMovieResponse>) {
        response.body()?.let {it ->

            Log.d("TAG", "onResponse: ${it.results}")

            recyclerViewAdapter = RecyclerViewAdapter(it.results as ArrayList<MovieModel>)
            recyclerViewAdapter?.let {
                it.onItemClicked = { itemId->
                    Log.d("TAG", "onResponse: Clicked")
                    goToDetailScreen(itemId)

                }
            }

            binding.recyclerView.adapter = recyclerViewAdapter

        }
    }

    private fun upcomingResponseIsSuccessful(response: Response<UpcomingMovieResponse>) {
        if (response.isSuccessful) {
            Log.d("TAG", "onResponse: ${response.body()}")
            response.body()?.let { result ->
                result.results.forEach {
                    imageList.add(SlideModel(
                        "https://image.tmdb.org/t/p/w500${it.backdrop_path}",
                        ScaleTypes.FIT))
                }
                binding.imageSlider.setImageList(imageList,ScaleTypes.CENTER_CROP)

                upcomingMovieResponse(response.body()!!)

            }


        } else {
            Log.e("TAG", "onResponse: ${response.errorBody()}")
        }
    }

    private fun upcomingMovieResponse( response: UpcomingMovieResponse){
        binding.imageSliderOverviewTextView.text = response.results[0].overview
        binding.imageSliderTitleTextView.text = response.results[0].title

        binding.imageSlider.setItemChangeListener(object : ItemChangeListener {
            override fun onItemChanged(position: Int) {
                binding.imageSliderTitleTextView.text = response.results[position].title
                binding.imageSliderOverviewTextView.text = response.results[position].overview
            }
        })

        binding.imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                goToDetailScreen(response.results[position].id)
            }
        })
    }

    private fun goToDetailScreen(movieId: Int){
        val action = DashboardScreenFragmentDirections.actionDashboardScreenFragmentToDetailScreenFragment(movieId)
        view?.findNavController()?.navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}