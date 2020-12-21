package com.myetisir.spacetransporter.ui.activity.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.myetisir.spacetransporter.databinding.ActivitySplashBinding
import com.myetisir.spacetransporter.ui.activity.base.BaseActivity
import com.myetisir.spacetransporter.ui.activity.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    override val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(savedInstanceState: Bundle?) {
        viewModel.startOpenTimer()
    }

    override fun observeViewModel() {
        viewModel.open.observe(this, {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })
    }

    override fun setBinding(): ActivitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)
}