package com.myetisir.spacetransporter.ui.fragment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.myetisir.spacetransporter.ui.activity.main.MainViewModel
import com.myetisir.spacetransporter.viewmodel.base.BaseViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {

    abstract val viewModel: VM

    val sharedViewModel: MainViewModel by viewModels()

    var binding: VB? = null

    protected val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = setBinding(inflater, container)

        onViewCreated(savedInstanceState)
        observeViewModel()

        return binding?.root
    }

    abstract fun setBinding(layoutInflater: LayoutInflater, container: ViewGroup?): VB

    abstract fun onViewCreated(savedInstanceState: Bundle?)

    abstract fun observeViewModel()

    override fun onDestroy() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
            compositeDisposable.clear()
        }
        binding = null
        super.onDestroy()
    }
}