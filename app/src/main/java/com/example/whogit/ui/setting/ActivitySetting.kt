package com.example.whogit.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.example.whogit.data.store.AppreanceSetting
import com.example.whogit.databinding.ActivitySettingBinding

class ActivitySetting : AppCompatActivity() {


    private val viewModel by viewModels<ViewModelSetting>{
        ViewModelSetting.Factory(AppreanceSetting(this))
    }

    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getThemeButton()

        binding.swNightMode.setOnCheckedChangeListener{_, checked ->
            viewModel.saveTheme(checked)
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getThemeButton(){
        viewModel.getTheme().observe(this){isDark ->
            val textMode = if (isDark) "Dark" else "Light"
            binding.swNightMode.text = textMode
            binding.swNightMode.isChecked = isDark

            val Dark = if (isDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(Dark)
        }
    }
}