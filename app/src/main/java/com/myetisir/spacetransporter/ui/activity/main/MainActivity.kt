package com.myetisir.spacetransporter.ui.activity.main

import android.os.Bundle
import androidx.activity.viewModels
import com.myetisir.spacetransporter.common.toast
import com.myetisir.spacetransporter.databinding.ActivityMainBinding
import com.myetisir.spacetransporter.ui.activity.base.BaseActivity
import com.myetisir.spacetransporter.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(savedInstanceState: Bundle?) {
        //viewModel.getStations()
    }

    override fun observeViewModel() {
        viewModel.stations.observe(this, {
            when (it) {
                is Resource.Success -> {
                    toast(it.data.toString())
                }
                is Resource.Error -> {
                    toast(it.exception.message)
                }
                Resource.Loading -> {
                    toast("loading")
                }
            }
        })
    }

    override fun setBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
}