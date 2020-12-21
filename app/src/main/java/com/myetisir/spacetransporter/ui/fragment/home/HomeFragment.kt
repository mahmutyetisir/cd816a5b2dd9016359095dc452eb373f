package com.myetisir.spacetransporter.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.myetisir.spacetransporter.common.invisible
import com.myetisir.spacetransporter.common.toast
import com.myetisir.spacetransporter.common.visible
import com.myetisir.spacetransporter.databinding.FragmentHomeBinding
import com.myetisir.spacetransporter.ui.fragment.base.BaseFragment
import com.myetisir.spacetransporter.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels()

    override fun setBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(savedInstanceState: Bundle?) {
        viewModel.getTransporter()

        childFragmentManager.fragments.first()?.findNavController()
            ?.let { navController ->
                binding?.bottomNav?.apply {
                    setupWithNavController(navController)
                }
            }
    }

    override fun observeViewModel() {
        viewModel.transporter.observe(this, {
            when (it) {
                is Resource.Loading -> {
                    requireActivity().toast("loading data")
                }
                is Resource.Success -> {
                    //binding?.txtTranspoerte?.text = it.data.name
                }
            }
        })
    }

    fun showBottomNavigation() {
        binding?.bottomNav?.visible()
    }

    fun hideBottomNavigation() {
        binding?.bottomNav?.invisible()
    }

}