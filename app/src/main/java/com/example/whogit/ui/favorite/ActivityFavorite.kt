package com.example.whogit.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whogit.database.ConfigDatabase
import com.example.whogit.databinding.ActivityFavoriteBinding
import com.example.whogit.ui.detail.DetailActivity
import com.example.whogit.ui.main.UserAdapter

class ActivityFavorite : AppCompatActivity() {

    private val viewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModel.factory(ConfigDatabase(this))
    }

    private lateinit var binding: ActivityFavoriteBinding
    private val adapter by lazy {
        UserAdapter { user ->
            Intent(this, DetailActivity::class.java).apply {
                putExtra("item", user)
                startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavoriteUser().observe(this) { favoriteList ->
            adapter.setData(favoriteList)
        }
    }

    private fun setupRecyclerView() {
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.adapter = adapter
    }
}
