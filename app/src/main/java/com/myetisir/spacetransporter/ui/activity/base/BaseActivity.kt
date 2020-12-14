package com.myetisir.spacetransporter.ui.activity.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.myetisir.spacetransporter.viewmodel.base.BaseViewModel

abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {

    abstract val viewModel: VM

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setBinding()
        setContentView(binding.root)
        onViewCreated(savedInstanceState)
        observeViewModel()
    }

    abstract fun setBinding(): VB

    abstract fun onViewCreated(savedInstanceState: Bundle?)

    abstract fun observeViewModel()
}