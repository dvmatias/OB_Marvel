package com.cmdv.ph_character_details.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmdv.common.DETACHED_TO_ROOT
import com.cmdv.common.extensions.addAllNoRepeated
import com.cmdv.domain.models.ComicModel
import com.cmdv.ph_character_details.databinding.ItemComicBinding

/**
 * Adapter class for [ComicModel] items.
 */
class ComicAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    /**
     * List of comics to be displayed.
     */
    private val comics: ArrayList<ComicModel> = arrayListOf()

    fun addItems(comics: List<ComicModel>) {
        val startIndex = itemCount
        this.comics.addAllNoRepeated(comics)
        notifyItemRangeChanged(startIndex, comics.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ComicViewHolder(
            ItemComicBinding.inflate(LayoutInflater.from(parent.context), parent, DETACHED_TO_ROOT)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        comics[position].let { (holder as ComicViewHolder).bindItem(it) }
    }

    override fun getItemCount(): Int = comics.size

    class ComicViewHolder(private val binding: ItemComicBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(comic: ComicModel) {
            binding.comic = comic
        }
    }
}