package com.myetisir.spacetransporter.ui.fragment.station

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.myetisir.spacetransporter.common.hideKeyboard
import com.myetisir.spacetransporter.data.model.Station
import com.myetisir.spacetransporter.data.model.TravelStatus
import com.myetisir.spacetransporter.databinding.FragmentStationBinding
import com.myetisir.spacetransporter.ui.adapter.StationAdapter
import com.myetisir.spacetransporter.ui.fragment.base.BaseFragment
import com.myetisir.spacetransporter.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StationFragment : BaseFragment<FragmentStationBinding, StationViewModel>() {
    override val viewModel: StationViewModel by viewModels()

    private val adapter = lazy {
        StationAdapter({ favoriteClickStation ->
            sharedViewModel.addFavoriteStation(favoriteClickStation)
        }) { travelClickStation ->
            sharedViewModel.startTransport(travelClickStation)
        }
    }


    override fun setBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStationBinding = FragmentStationBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(savedInstanceState: Bundle?) {
        binding?.recyclerViewStations?.adapter = adapter.value
        LinearSnapHelper().attachToRecyclerView(binding?.recyclerViewStations)
        sharedViewModel.getStations()

        binding?.imgBtnLeftStation?.click(compositeDisposable) {
            val position =
                (binding?.recyclerViewStations?.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            if (position > 0) {
                binding?.recyclerViewStations?.smoothScrollToPosition(position - 1)
            }
        }

        binding?.imgBtnRightStation?.click(compositeDisposable) {
            val position =
                (binding?.recyclerViewStations?.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            if (position < adapter.value.stations.size) {
                binding?.recyclerViewStations?.smoothScrollToPosition(position + 1)
            }
        }
    }

    override fun observeViewModel() {
        observeStations()
        observeTransporter()
        observeTransportMission()

        sharedViewModel.currentStation.observe(viewLifecycleOwner, { currentStation ->
            binding?.txtCurrentStationName?.text = currentStation.name
        })

        sharedViewModel.crash.observe(viewLifecycleOwner, {
            binding?.txtCurrentUnkonwn?.text = it.toString()
            if (it == 0) {
                showInfoPopup("Görev için verilen süre tamamlandı artık gezegen seyehati gerçekleştiremezsiniz.")
            }
        })

        sharedViewModel.crashTimer.observe(viewLifecycleOwner, { time ->
            binding?.txtCurrentTime?.text = "${time}s"
        })

        sharedViewModel.addFavoriteStation.observe(viewLifecycleOwner, {
            if (it is Resource.Success) {
                showInfoPopup("Gezegen favoriler listesine eklendi.")
            } else if (it is Resource.Error) {
                showInfoPopup(it.exception.localizedMessage)
            }
        })
    }

    private fun observeStations() {
        sharedViewModel.stations.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    adapter.value.updateList(it.data)
                    setAutoComplateAdapter(it.data)
                }
                is Resource.Error -> {
                    val errorMessage =
                        it.exception.localizedMessage + " Gezegenleri çekmek için tekrar denememiz gerekmekte."
                    showInfoPopup(errorMessage, false) {
                        sharedViewModel.getStations()
                    }
                }
                Resource.Loading -> {

                }
            }
        })
    }

    private fun observeTransporter() {
        sharedViewModel.transporter.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data.apply {
                        binding?.txtCurrentTransporterName?.text = name
                        binding?.txtEUS?.text = speed.toString()
                        binding?.txtUGS?.text = capacity.toString()
                        binding?.txtDS?.text = durability.toString()
                    }
                }
                is Resource.Error -> {
                    showInfoPopup("Oluşturmuş olduğunuz gemiyi çekerken bir problemle karşılaşıldı.")
                }
                Resource.Loading -> {
                }
            }
        }
    }

    private fun observeTransportMission() {
        sharedViewModel.transportMission.observe(viewLifecycleOwner, {
            when (it) {
                is TravelStatus.Error -> {
                    showInfoPopup(it.exception.localizedMessage)
                }
                is TravelStatus.Complated -> {
                    showInfoPopup("Görevi tamamladınız.")
                }
                is TravelStatus.Success -> {
                }
                is TravelStatus.Loading -> {
                }
            }
        })
    }

    private fun setAutoComplateAdapter(stations: List<Station>) {
        binding?.searchViewStation?.setAdapter(
            ArrayAdapter(
                requireActivity(),
                android.R.layout.simple_dropdown_item_1line,
                stations.map { it.name }.toTypedArray()
            )
        )
        binding?.searchViewStation?.threshold = 0
        binding?.searchViewStation?.setOnItemClickListener { parent, view, position, id ->
            val item = parent.getItemAtPosition(position) as String
            adapter.value.stations.indexOfFirst { station -> station.name == item }?.let {
                closeKeyboard()
                binding?.recyclerViewStations?.smoothScrollToPosition(it)
            }
        }
    }

    private fun closeKeyboard() {
        binding?.searchViewStation?.apply {
            clearFocus()
            context.hideKeyboard(this)
        }
    }

    override fun onPause() {
        closeKeyboard()
        super.onPause()
    }
}