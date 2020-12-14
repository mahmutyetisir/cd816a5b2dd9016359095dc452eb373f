package com.myetisir.spacetransporter.ui.fragment.create

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.myetisir.spacetransporter.R
import com.myetisir.spacetransporter.common.click
import com.myetisir.spacetransporter.common.toast
import com.myetisir.spacetransporter.databinding.FragmentCreateTransporterBinding
import com.myetisir.spacetransporter.ui.fragment.base.BaseFragment
import com.myetisir.spacetransporter.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateTransporterFragment :
    BaseFragment<FragmentCreateTransporterBinding, CreateTransporterViewModel>() {

    override val viewModel: CreateTransporterViewModel by viewModels()

    override fun setBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCreateTransporterBinding =
        FragmentCreateTransporterBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(savedInstanceState: Bundle?) {
        viewModel.checkTransporterIsCreated()

        binding?.btnContiune?.click(compositeDisposable) { view ->
            viewModel.contiune()
        }

        binding?.edtxtTransporterName?.addTextChangedListener(editTextName)

        binding?.seekBarTransporterDurability?.setOnSeekBarChangeListener(seekBarChangeDurability)
        binding?.seekBarTransporterSpeed?.setOnSeekBarChangeListener(seekBarChangeSpeed)
        binding?.seekBarTransporterCapacity?.setOnSeekBarChangeListener(seekBarChangeCapacity)
    }

    override fun observeViewModel() {
        viewModel.navigateHome.observe(this, {
            when (it) {
                is Resource.Success -> {
                    view?.findNavController()
                        ?.navigate(R.id.action_createTransporterFragment_to_homeFragment)
                }
                is Resource.Error -> {
                    requireActivity().toast(it.exception.message)
                }
                Resource.Loading -> {
                }
            }
        })

        viewModel.transporter.observe(this, {

        })

        viewModel.points.observe(this, {
            binding?.txtTransporterPointsCount?.text = it.toString()
        })
    }

    private val seekBarChangeDurability = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            seekBar?.progress?.let { progress ->
                viewModel.updateTransporterDurability(viewModel.tempDurability.updatePoint(progress))
                    .let {
                        binding?.seekBarTransporterSpeed?.progress = it.speed
                        binding?.seekBarTransporterCapacity?.progress = it.capacity
                    }
            }
        }
    }

    private val seekBarChangeSpeed = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            seekBar?.progress?.let { progress ->
                viewModel.updateTransporterSpeed(viewModel.tempSpeed.updatePoint(progress)).let {
                    binding?.seekBarTransporterDurability?.progress = it.durability
                    binding?.seekBarTransporterCapacity?.progress = it.capacity
                }
            }
        }
    }

    private val seekBarChangeCapacity = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            seekBar?.progress?.let { progress ->
                viewModel.updateTransporterCapacity(viewModel.tempCapacity.updatePoint(progress))
                    .let {
                        binding?.seekBarTransporterSpeed?.progress = it.speed
                        binding?.seekBarTransporterDurability?.progress = it.durability
                    }
            }
        }
    }

    private val editTextName = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            viewModel.updateTransporterName(s.toString())
        }
    }
}