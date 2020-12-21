package com.myetisir.spacetransporter.ui.activity.main

import android.os.Bundle
import androidx.activity.viewModels
import com.myetisir.spacetransporter.databinding.ActivityMainBinding
import com.myetisir.spacetransporter.ui.activity.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(savedInstanceState: Bundle?) {

    }

    override fun observeViewModel() {

    }

    override fun setBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun onBackPressed() {
        super.onBackPressed()
    }
}