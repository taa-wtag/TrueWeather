package com.rektstudios.trueweather.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.HourlyWeatherItem
import com.rektstudios.trueweather.databinding.ItemCityCardFragmentBinding
import javax.inject.Inject

class CityItemAdapter @Inject constructor(
    private val glide: RequestManager
): RecyclerView.Adapter<CityItemViewHolder>() {
    private var isDeleteButtonVisible = false

    private var onDeleteButtonClickListener: ((String) -> Unit)? = null

    fun setOnDeleteButtonClickListener(listener: (String) -> Unit) {
        onDeleteButtonClickListener = listener
    }


    @SuppressLint("NotifyDataSetChanged")
    private val toggleDeleteButtonVisibility: () -> Unit = {
        isDeleteButtonVisible=!isDeleteButtonVisible
        notifyDataSetChanged()
    }

    private val differ = AsyncListDiffer(this, CityItemDiffCallback())

    var cityItems: List<Pair<CityItem,HourlyWeatherItem?>>
        get() = differ.currentList
        set(value) = differ.submitList(value)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityItemViewHolder {
        val binding = ItemCityCardFragmentBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return CityItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cityItems.size
    }

    override fun onBindViewHolder(holder: CityItemViewHolder, position: Int) {
        holder.bind(cityItems[position].first,cityItems[position].second,glide,onDeleteButtonClickListener, isDeleteButtonVisible
        ) { toggleDeleteButtonVisibility() }
    }
}