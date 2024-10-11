package com.example.movieapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.adapter.RecyclerViewAdapter
import com.example.movieapp.databinding.FragmentDetailScreenBinding
import com.example.movieapp.databinding.FragmentDiscoverScreenBinding
import com.example.movieapp.models.MovieModel
import com.example.movieapp.models.TopRatedMovieResponse
import com.example.movieapp.network.retrofit.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiscoverScreenFragment : Fragment() {

    private var _binding: FragmentDiscoverScreenBinding? = null
    private val binding get() = _binding!!
    private var recyclerViewAdapter: RecyclerViewAdapter? = null

    val args by navArgs<DiscoverScreenFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiscoverScreenBinding.inflate(inflater, container, false)
        val view = binding.root

        //recyclerView
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        binding.discoverScreenRecyclerView.layoutManager = layoutManager

        topDiscoverMovies()

        binding.discoverScreenBackBtn.setOnClickListener{
            findNavController().popBackStack()
        }

        binding.discoverScreenCloseBtn.setOnClickListener{
            val action = DiscoverScreenFragmentDirections.actionDiscoverScreenFragmentToDashboardScreenFragment()
            findNavController().navigate(action)
        }

        return view
    }

    private fun topDiscoverMovies(){
        val service = Retrofit.getMovieService()
        service.getDiscoverMovies(args.genresId).enqueue(object: Callback<TopRatedMovieResponse> {
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

                        }
                    }

                    binding.discoverScreenRecyclerView.adapter = recyclerViewAdapter

                }
            }

            override fun onFailure(call: Call<TopRatedMovieResponse>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message}", t)
            }

        })
    }

}