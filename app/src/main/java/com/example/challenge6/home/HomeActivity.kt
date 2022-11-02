package com.example.challenge6.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge6.adapter.MovieAdapter
import com.example.challenge6.databinding.ActivityHomeBinding
import com.example.challenge6.login.LoginActivity
import com.example.challenge6.profile.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private var _binding : ActivityHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        toProfile()
        viewModel.getDataStoreUsername().observe(this){
            binding.tvData.text = "Hi, $it"
        }
        binding.rvData.layoutManager = LinearLayoutManager(this)
        binding.rvData.setHasFixedSize(false)
        viewModel.setMoviesList()
        viewModel.movie.observe(this) {
            if (it != null) {
                binding.rvData.adapter = MovieAdapter(it)
            }
        }

    }

    private fun toProfile() {
        binding.imageButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)


        }
    }
}