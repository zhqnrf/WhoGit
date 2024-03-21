package com.example.whogit.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whogit.R
import com.example.whogit.data.store.AppreanceSetting
import com.example.whogit.data.model.UserModel
import com.example.whogit.databinding.ActivityMainBinding
import com.example.whogit.ui.detail.DetailActivity
import com.example.whogit.ui.favorite.ActivityFavorite
import com.example.whogit.ui.setting.ActivitySetting
import com.example.whogit.ui.utils.Result

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        UserAdapter { user ->
            startActivityDetail(user)
        }
    }
    private val viewModel by viewModels<ViewModel>{
        ViewModel.Factory(AppreanceSetting(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        getListRecyclerView()
        getThemeMode()
        getSearchViewData()
        getResultData()

        viewModel.getUser("a")
    }



    private fun getThemeMode() {
        viewModel.getThemeMode().observe(this){
            val modeMalam = if (it){
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(modeMalam)
        }
    }


    private fun getResultData(){
        viewModel.resultUser.observe(this){
            when(it){
                is  Result.Success<*> -> {
                   val data = it.data as MutableList<UserModel.ItemsItem>
                    adapter.setData(data)
                }
                is  Result.Error -> {
                    Toast.makeText(this, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    binding.progressBar.isVisible = it.isLoading
                }
            }
        }
    }
    private fun getSearchViewData(){
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
               query?.let {
                   viewModel.getUser(it)
               }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
    private fun getListRecyclerView(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
    }

    private fun startActivityDetail(user : UserModel.ItemsItem) {
        Intent(this, DetailActivity::class.java).apply {
            putExtra("item", user)
            startActivity(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite -> startFavoriteActivity()
            R.id.setting -> startSettingActivity()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun startFavoriteActivity() {
        Intent(this, ActivityFavorite::class.java).apply {
            startActivity(this)
        }
    }


    private fun startSettingActivity(){
        Intent(this, ActivitySetting::class.java).apply {
            startActivity(this)
        }
    }
}