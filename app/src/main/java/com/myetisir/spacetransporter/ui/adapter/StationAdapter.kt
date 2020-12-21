package com.myetisir.spacetransporter.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.myetisir.spacetransporter.common.invisible
import com.myetisir.spacetransporter.common.visible
import com.myetisir.spacetransporter.data.model.Station
import com.myetisir.spacetransporter.databinding.ItemStationBinding


class StationAdapter(
    private val favoriteClick: (Station) -> Unit,
    private val travelClick: (Station) -> Unit
) : RecyclerView.Adapter<StationAdapter.StationViewHolder>() {
    var stations: ArrayList<Station> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        return StationViewHolder(
            ItemStationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        val station = stations[position]
        holder.bind(station)
        holder.binding.btnTravel.setOnClickListener {
            travelClick.invoke(station)
        }
        holder.binding.imgBtnAddFavorite.setOnClickListener {
            favoriteClick.invoke(station)
        }
    }

    override fun getItemCount(): Int = stations.size

    fun updateList(newStations: List<Station>) {
        DiffUtil.calculateDiff(StationDiffUtil(this.stations, newStations)).dispatchUpdatesTo(this)
        stations.clear()
        stations.addAll(newStations)
        notifyDataSetChanged()
    }

    class StationViewHolder(b: ItemStationBinding) : RecyclerView.ViewHolder(b.root) {
        var binding: ItemStationBinding = b

        fun bind(station: Station) {
            binding.txtStationEus.text = "${station.eus.toString()} EUS"
            binding.txtStationUgs.text = "${station.capacity} / ${station.need}"
            binding.txtStationName.text = station.name

            if (station.isCurrentStation) binding.btnTravel.invisible()
            else binding.btnTravel.visible()
        }
    }
}

class StationDiffUtil(
    private val oldList: List<Station>,
    private val newList: List<Station>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldList[oldItemPosition].need == newList[newItemPosition].need) || (oldList[oldItemPosition].eus == newList[newItemPosition].eus) || (oldList[oldItemPosition] == newList[newItemPosition])
    }
}