package com.example.movieapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardScreenBinding.inflate(inflater, container, false)
        val view = binding.root
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
                if (response.isSuccessful) {
                    Log.d("TAG", "onResponse: ${response.body()}")
                    response.body()?.let { result ->
                        result.results.forEach { // resimlerden rastgele 5 tanesini gösterebilirsin, resim ekrana tam sıgsın.
                            imageList.add(SlideModel("https://image.tmdb.org/t/p/w500${it.poster_path}", it.title))
                        }
                    }
                    binding.imageSlider.setImageList(imageList)

                }
                else {
                    Log.e("TAG", "onResponse: ${response.errorBody()}")
                }

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
                response.body()?.let {it ->

                    Log.d("TAG", "onResponse: ${it.results}")

                    recyclerViewAdapter = RecyclerViewAdapter(it.results as ArrayList<MovieModel>)
                    recyclerViewAdapter?.let {
                        it.onItemClicked = { itemId->
                            Log.d("TAG", "onResponse: Clicked")
                            val action = DashboardScreenFragmentDirections.actionDashboardScreenFragmentToDetailScreenFragment(itemId)
                            view?.findNavController()?.navigate(action)
                        }
                    }

                    binding.recyclerView.adapter = recyclerViewAdapter

                }
            }

            override fun onFailure(call: Call<TopRatedMovieResponse>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message}", t)
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}