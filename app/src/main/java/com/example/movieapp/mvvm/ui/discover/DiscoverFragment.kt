package com.example.movieapp.mvvm.ui.discover

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.movieapp.adapter.RecyclerViewAdapter
import com.example.movieapp.databinding.FragmentDiscoverScreenBinding

class DiscoverFragment : Fragment() {

    private var _binding: FragmentDiscoverScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<DiscoverViewModel>()
    private val args by navArgs<DiscoverFragmentArgs>()

    private var recyclerViewAdapter = RecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiscoverScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.discoverScreenBackBtn.setOnClickListener{
            findNavController().popBackStack()
        }

        binding.discoverScreenCloseBtn.setOnClickListener{
            val action = DiscoverFragmentDirections.actionDiscoverFragmentToDashboardFragment()
           findNavController().navigate(action)
        }

        recyclerViewAdapter.onItemClicked = { itemId ->
            Log.d("TAG", "onResponse: Discover Item Clicked")
            val action = DiscoverFragmentDirections.actionDiscoverFragmentToDetailFragment(itemId)
            view?.findNavController()?.navigate(action)
        }
        binding.discoverScreenRecyclerView.adapter = recyclerViewAdapter

        viewModel.topDiscoverMovies(args.genresId)
        observeAndHandleTopRatedMovies()
    }

    private fun observeAndHandleTopRatedMovies() {
        viewModel.discoverMovies.observe(viewLifecycleOwner) { movies ->
            if (movies.isEmpty()) return@observe
            recyclerViewAdapter.setItems(movies)
        }
    }

}