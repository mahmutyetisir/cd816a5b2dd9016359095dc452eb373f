package com.myetisir.spacetransporter.ui.fragment.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.myetisir.spacetransporter.data.model.Station
import com.myetisir.spacetransporter.databinding.FragmentFavoriteBinding
import com.myetisir.spacetransporter.ui.adapter.FavoriteStationAdapter
import com.myetisir.spacetransporter.ui.fragment.base.BaseFragment
import com.myetisir.spacetransporter.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>() {

    override val viewModel: FavoriteViewModel by viewModels()

    private var adapter: FavoriteStationAdapter? = null

    override fun setBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoriteBinding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(savedInstanceState: Bundle?) {
        viewModel.getFavoriteStations()
    }

    override fun observeViewModel() {
        viewModel.favoriteStations.observe(viewLifecycleOwner, {
            if (it is Resource.Success) {
                checkDataSize(it.data)

                adapter =
                    FavoriteStationAdapter(it.data as ArrayList<Station>) { station, position ->
                        viewModel.removeFavoriteStation(station)
                        adapter?.removeItem(station, position)
                        adapter?.stations?.let { data ->
                            checkDataSize(data)
                        }
                    }
                binding?.recyclerViewFavoriteStations?.adapter = adapter
            }
        })
    }

    private fun checkDataSize(stations: List<Station>) {
        if (stations.isEmpty()) binding?.txtInfo?.visible()
        else binding?.txtInfo?.invisible()
    }
}