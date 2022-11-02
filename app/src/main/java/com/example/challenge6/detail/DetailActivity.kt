package com.example.challenge6.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.challenge6.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailViewModel
    private var b: Bundle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        b = intent.extras
        val id = b?.getInt("id")
        if (id != null) {
            viewModel.getMovieById(id)
            viewModel.movie.observe(this) {
                binding.apply {
                    if (it != null) {
                        tvJudul.text = it.title.toString()
                        tvRelease.text = it.releaseDate.toString()
                        Glide.with(this@DetailActivity)
                            .load("https://image.tmdb.org/t/p/w500/" + it.posterPath)
                            .into(binding.imgDetailPoster)
                        tvOverview.text = it.overview.toString()
                    }
                }
            }
        }
    }
}

