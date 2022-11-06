package com.binartest.challenge8.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binartest.challenge8.databinding.ItemMovieBinding
import com.binartest.challenge8.detail.DetailActivity
import com.binartest.challenge8.model.movie.GetNowPlayingResponseItem
import com.bumptech.glide.Glide

class MovieAdapter(
    private val listMovie: List<GetNowPlayingResponseItem>
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(itemView: ItemMovieBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
        private val binding = itemView
        fun bindMovie(dataMovie: GetNowPlayingResponseItem) {
            with(itemView){
                binding.tvTitle.text = dataMovie.title
                binding.tvTahun.text = dataMovie.releaseDate
                Glide.with(itemView).load(IMAGE_BASE + dataMovie.posterPath).into(binding.imgPoster)
                binding.cvIdMovie.setOnClickListener {
                    var id = Intent(context, DetailActivity::class.java).apply {
                        putExtra("id", dataMovie.id)
                    }
                    context.startActivity(id)
                }

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.context),
            parent, false
        ))
    }
    override fun getItemCount(): Int = listMovie.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindMovie(listMovie[position])
    }
}