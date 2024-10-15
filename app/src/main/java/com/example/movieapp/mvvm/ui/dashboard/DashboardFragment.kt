package com.example.movieapp.mvvm.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemChangeListener
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.example.movieapp.adapter.RecyclerViewAdapter
import com.example.movieapp.databinding.FragmentDashboardScreenBinding

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardScreenBinding? = null
    private val binding get() = _binding!!


    private val viewModel by viewModels<DashboardViewModel>()

    private var recyclerViewAdapter = RecyclerViewAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("TAG", "onCreateView:")
        _binding = FragmentDashboardScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewAdapter.onItemClicked = { itemId ->
            Log.d("TAG", "onResponse: Clicked")
            goToDetailScreen(itemId)
        }
        binding.recyclerView.adapter = recyclerViewAdapter


        observeAndHandleTopRatedMovies()
        observeAndHandleUpcomingMovies()
    }

    private fun observeAndHandleTopRatedMovies() {
        viewModel.topRatedMovies.observe(viewLifecycleOwner) { movies ->
            if (movies.isEmpty()) return@observe
            recyclerViewAdapter.setItems(movies)
        }
    }

    private fun observeAndHandleUpcomingMovies() {

        viewModel.upcomingMovies.observe(viewLifecycleOwner) { movies ->
            if (movies.isEmpty()) return@observe
            binding.imageSlider.setImageList(viewModel.imageList, ScaleTypes.CENTER_CROP)

            val movie = movies.first()
            binding.imageSliderOverviewTextView.text = movie.overview
            binding.imageSliderTitleTextView.text = movie.title

            setUpImageSlider()
        }
    }

    private fun setUpImageSlider() {

        binding.imageSlider.setItemChangeListener(object : ItemChangeListener {
            override fun onItemChanged(position: Int) {
                val movie = viewModel.getMovie(position)
                binding.imageSliderTitleTextView.text = movie?.title
                binding.imageSliderOverviewTextView.text = movie?.overview
            }
        })

        binding.imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                viewModel.getMovie(position)?.let {
                    goToDetailScreen(it.id)
                }
            }
        })
    }

    private fun goToDetailScreen(movieId: Int) {
        val action = DashboardFragmentDirections.actionDashboardFragmentToDetailFragment(movieId)
        view?.findNavController()?.navigate(action)
    }
}