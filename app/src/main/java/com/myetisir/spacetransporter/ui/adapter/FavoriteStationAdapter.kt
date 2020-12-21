package com.myetisir.spacetransporter.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myetisir.spacetransporter.data.model.Station
import com.myetisir.spacetransporter.databinding.ItemFavoriteBinding

class FavoriteStationAdapter(
    var stations: ArrayList<Station>,
    private val favoriteClick: (Station, Int) -> Unit
) : RecyclerView.Adapter<FavoriteStationAdapter.FavoriteStationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteStationViewHolder {
        return FavoriteStationViewHolder(
            ItemFavoriteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoriteStationViewHolder, position: Int) {
        val station = stations[position]
        holder.bind(station)
        holder.binding.imgBtnAddFavorite.setOnClickListener {
            favoriteClick.invoke(station, position)
        }
    }

    override fun getItemCount(): Int = stations.size

    fun removeItem(station: Station, position: Int) {
        stations.remove(station)
        notifyItemRemoved(position)
    }

    class FavoriteStationViewHolder(b: ItemFavoriteBinding) : RecyclerView.ViewHolder(b.root) {
        var binding: ItemFavoriteBinding = b

        fun bind(station: Station) {
            binding.txtStationEUS.text = "${station.eus.toString()} EUS"
            binding.txtStationName.text = station.name
        }
    }
}