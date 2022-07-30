package com.cmdv.ph_character_details.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmdv.common.DETACHED_TO_ROOT
import com.cmdv.common.extensions.addAllNoRepeated
import com.cmdv.domain.models.SerieModel
import com.cmdv.ph_character_details.databinding.ItemSerieBinding

/**
 * Adapter class for [SerieModel] items.
 */
class SerieAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    /**
     * List of series to be displayed.
     */
    private val series: ArrayList<SerieModel> = arrayListOf()

    fun addItems(series: List<SerieModel>) {
        val startIndex = itemCount
        this.series.addAllNoRepeated(series)
        notifyItemRangeChanged(startIndex, series.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        SerieViewHolder(
            ItemSerieBinding.inflate(LayoutInflater.from(parent.context), parent, DETACHED_TO_ROOT)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        series[position].let { (holder as SerieViewHolder).bindItem(it) }
    }

    override fun getItemCount(): Int = series.size

    class SerieViewHolder(private val binding: ItemSerieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(serie: SerieModel) {
            binding.serie = serie
        }
    }
}