package com.example.movieapp.mvvm.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.movieapp.DetailScreenFragmentDirections
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentDetailScreenBinding
import com.example.movieapp.mvvm.ui.dashboard.DashboardViewModel
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailScreenBinding? = null
    private val binding get() = _binding!!
    private var str = ""

    private val viewModel by viewModels<DetailViewModel>()

    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.detailScreenBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.detailScreenDiscoverBtn.setOnClickListener {
            //val action =
            //   DetailScreenFragmentDirections.actionDetailScreenFragmentToDiscoverScreenFragment(str)
            //findNavController().navigate(action)
        }

        viewModel.getMovieDetail(args.movieId)


        viewModel.resultMovieDetail.observe(viewLifecycleOwner){
            if(it){
                viewModel.movieDetail?.let { response ->
                    val imageUrl = "https://image.tmdb.org/t/p/w500${response.poster_path}"
                    Picasso.get().load(imageUrl).into(binding.detailScreenImageView)

                    str = response.genres.map { response.id.toString() }.toTypedArray().joinToString("%20or%20")
                    //Log.d("TAG", "onResponse: $str")

                    binding.detailScreenTitle.text = response.title
                    binding.detailScreenOverview.text = response.overview

                    val decimalFormat = DecimalFormat("#.#")

                    val rate = decimalFormat.format(response.vote_average).toString() + "/10"
                    binding.detailScreenRate.text = rate
                }

            }
        }

    }

}