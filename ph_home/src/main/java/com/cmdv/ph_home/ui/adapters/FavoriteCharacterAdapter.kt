package com.cmdv.ph_home.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmdv.common.DETACHED_TO_ROOT
import com.cmdv.domain.models.CharacterModel
import com.cmdv.ph_home.databinding.ItemFavoriteCharacterBinding
import com.cmdv.ph_home.ui.adapters.CharacterAdapterViewType.CHARACTER
import com.cmdv.ph_home.ui.adapters.CharacterAdapterViewType.HEADER_MAIN
import com.cmdv.ph_home.ui.listeners.FavoriteCharacterAdapterListener

private const val HEADER_POSITION = 0

class FavoriteCharacterAdapter(private val listener: FavoriteCharacterAdapterListener) :
    RecyclerView.Adapter<FavoriteCharacterAdapter.FavoriteCharacterViewHolder>() {

    private val favoriteCharacters: ArrayList<CharacterModel> = arrayListOf()
    private lateinit var inflater: LayoutInflater

    fun setItems(favoriteCharacters: List<CharacterModel>) {
        this.favoriteCharacters.addAll(favoriteCharacters)
        notifyItemRangeChanged(0, favoriteCharacters.size)
    }

    override fun getItemViewType(position: Int): Int =
        when (position) {
            HEADER_POSITION -> HEADER_MAIN.viewType
            else -> CHARACTER.viewType
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteCharacterViewHolder {
        inflater = LayoutInflater.from(parent.context)
        return FavoriteCharacterViewHolder(
            ItemFavoriteCharacterBinding.inflate(inflater, parent, DETACHED_TO_ROOT)
        )
    }

    override fun onBindViewHolder(holder: FavoriteCharacterViewHolder, position: Int) {
        holder.bindItem(favoriteCharacters[position], listener, position)
    }

    override fun getItemCount(): Int = this.favoriteCharacters.size

    class FavoriteCharacterViewHolder(
        private val binding: ItemFavoriteCharacterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(character: CharacterModel, listener: FavoriteCharacterAdapterListener, index: Int) {
            binding.listener = listener
            binding.characterIndex = index
            binding.character = character
        }
    }

}