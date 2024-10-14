package com.example.movieapp.adapter

import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.RecyclerRowBinding
import com.example.movieapp.models.MovieModel
import com.squareup.picasso.Picasso
import java.text.DecimalFormat


class RecyclerViewAdapter(private var movieModelList: ArrayList<MovieModel>)
    : RecyclerView.Adapter<RecyclerViewAdapter.MovieHolder>() {

    var onItemClicked: (Int) -> Unit = {}

    class MovieHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){
//
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

        val decimalFormat = DecimalFormat("#.#") // Virgülden sonra bir basamak gösterecek

        val rate = decimalFormat.format(item.vote_average).toString() + "/10" //🌟
        val spannable = SpannableString(rate)
        val startIndex: Int = rate.indexOf("/10")
        val endIndex = startIndex + 3
        spannable.setSpan(RelativeSizeSpan(1.2f), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        holder.binding.rateTextView.text = spannable

        val imageUrl = "https://image.tmdb.org/t/p/w500${item.poster_path}"
        Picasso.get().load(imageUrl).into(holder.binding.topRatedImageView)

    }

    override fun getItemCount(): Int {
        return movieModelList.size
    }


}