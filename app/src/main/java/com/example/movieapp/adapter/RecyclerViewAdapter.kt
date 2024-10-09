package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.RecyclerRowBinding
import com.example.movieapp.models.MovieModel
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(private var movieModelList: ArrayList<MovieModel>)
    : RecyclerView.Adapter<RecyclerViewAdapter.MovieHolder>() {

    var onItemClicked: (Int) -> Unit = {}

    class MovieHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val item = movieModelList[position]
        holder.itemView.setOnClickListener {
            onItemClicked(item.id)
        }
        holder.binding.titleTextView.text = item.title
        holder.binding.overviewTextView.text = item.overview
        holder.binding.rateTextView.text = item.vote_average.toString() + "/10"

        val imageUrl = "https://image.tmdb.org/t/p/w500${item.poster_path}"
        Picasso.get().load(imageUrl).into(holder.binding.topRatedImageView)

    }

    override fun getItemCount(): Int {
        return movieModelList.size
    }


}