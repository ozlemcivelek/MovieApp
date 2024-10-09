package com.example.movieapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.movieapp.databinding.FragmentDetailScreenBinding
import com.example.movieapp.databinding.FragmentDiscoverScreenBinding

class DiscoverScreenFragment : Fragment() {

    private var _binding: FragmentDiscoverScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiscoverScreenBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.discoverScreenBackBtn.setOnClickListener{
            val action = DiscoverScreenFragmentDirections.actionDiscoverScreenFragmentToDetailScreenFragment(1)
            view?.findNavController()?.navigate(action)
        }

        binding.discoverScreenCloseBtn.setOnClickListener{
            val action = DiscoverScreenFragmentDirections.actionDiscoverScreenFragmentToDashboardScreenFragment()
            view?.findNavController()?.navigate(action)
        }

        return view
    }

}