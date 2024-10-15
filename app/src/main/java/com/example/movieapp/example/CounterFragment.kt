package com.example.movieapp.example

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.movieapp.databinding.FragmentCounterBinding

class CounterFragment : Fragment() {

    private var _binding: FragmentCounterBinding? = null
    private val binding get() = _binding!!

    val viewModel: CounterViewModel by viewModels()
//    val viewModel2 by viewModels<CounterViewModel>()

    init {
        Log.d("TAG", "Fragment: initialized")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TAG", "Fragment: created")
    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG", "Fragment: started")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG", "Fragment: resumed")
    }


    override fun onPause() {
        super.onPause()
        Log.d("TAG", "Fragment: paused")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG", "Fragment: stopped")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG", "Fragment: destroyed")
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCounterBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        writeCounter()
        binding.increaseBtn.setOnClickListener {
            viewModel.incrementCounter()
            writeCounter()
        }
    }

    private fun writeCounter() {
        binding.countTV.text = viewModel.counter.toString()
    }
}