package com.mohammed.valenbici

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mohammed.valenbici.model.Station

class StationAdapter(private var stations: List<Station>) : RecyclerView.Adapter<StationAdapter.StationViewHolder>() {

    private var filteredStations: MutableList<Station> = stations.toMutableList()

    class StationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = view.findViewById(R.id.cardView)
        val tvAddress: TextView = view.findViewById(R.id.tvAddress)
        val tvAvailable: TextView = view.findViewById(R.id.tvAvailable)
        val tvFree: TextView = view.findViewById(R.id.tvFree)
        val tvBikesCircle: TextView = view.findViewById(R.id.tvBikesCircle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_station, parent, false)
        return StationViewHolder(view)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        val station = filteredStations[position]

        val normalColor = if (position % 2 == 0) Color.parseColor("#EDEDED") else Color.parseColor("#D3D3D3")

        if (station.availableBikes == 0) {
            holder.cardView.setCardBackgroundColor(Color.RED)
            holder.tvBikesCircle.backgroundTintList = ColorStateList.valueOf(Color.RED)
        } else {
            holder.cardView.setCardBackgroundColor(normalColor)
            holder.tvBikesCircle.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FF5722"))
        }

        if (station.availableBikes >= 10) {
            holder.tvBikesCircle.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#4FF244"))
        } else if (station.availableBikes >= 5) {
            holder.tvBikesCircle.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FBC02D"))
        }

        holder.tvAddress.text = station.address
        holder.tvBikesCircle.text = station.availableBikes.toString()
        holder.tvAvailable.text = "Bicis: ${station.availableBikes}"
        holder.tvFree.text = "Espacios: ${station.freeSpaces}"

        holder.tvAvailable.setTextColor(Color.BLACK)
        holder.tvFree.setTextColor(Color.BLACK)
    }

    override fun getItemCount(): Int = filteredStations.size

    fun updateStations(newStations: List<Station>) {
        stations = newStations
        filteredStations = stations.toMutableList()
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        filteredStations = if (query.isEmpty()) {
            stations.toMutableList()
        } else {
            stations.filter { it.address.contains(query, ignoreCase = true) }.toMutableList()
        }
        notifyDataSetChanged()
    }
}
