package com.myetisir.spacetransporter.ui.activity.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.myetisir.spacetransporter.common.toast
import com.myetisir.spacetransporter.databinding.ActivitySplashBinding
import com.myetisir.spacetransporter.ui.activity.base.BaseActivity
import com.myetisir.spacetransporter.ui.activity.main.MainActivity
import com.myetisir.spacetransporter.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    override val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(savedInstanceState: Bundle?) {
        viewModel.getStations()
    }

    override fun observeViewModel() {
        viewModel.stations.observe(this, {
            when (it) {
                is Resource.Success -> {
                    startActivity(Intent(this, MainActivity::class.java))
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

    override fun setBinding(): ActivitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)
}