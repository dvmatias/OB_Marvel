package com.cmdv.ph_home.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmdv.common.DETACHED_TO_ROOT
import com.cmdv.domain.models.CharacterModel
import com.cmdv.domain.models.IndexedFavoriteCharactersModel
import com.cmdv.ph_home.databinding.ItemIndexFavoriteCharacterBinding

class IndexFavoriteCharacterAdapter :
    RecyclerView.Adapter<IndexFavoriteCharacterAdapter.IndexFavoriteCharactersViewHolder>() {

    private val items: ArrayList<IndexedFavoriteCharactersModel> = arrayListOf()
    private val indexes: ArrayList<String> = arrayListOf()
    private val characters: ArrayList<CharacterModel> = arrayListOf()

    fun setCharacters(favoriteCharacters: List<CharacterModel>) {
        this.characters.apply {
            clear()
            addAll(favoriteCharacters)
        }
        setItems()
        notifyDataSetChanged()
    }

    private fun setItems() {
        setIndexes()
        items.clear()
        indexes.forEach { index ->
            val indexedCharacters = arrayListOf<CharacterModel>()
            characters.forEach { character ->
                character.takeIf { it.name.startsWith(index) }?.also {
                    indexedCharacters.add(it)
                }
            }
            items.add(IndexedFavoriteCharactersModel(index, indexedCharacters))
        }
    }

    fun clear() {
        items.clear()
        indexes.clear()
        characters.clear()
        notifyDataSetChanged()
    }

    private fun setIndexes() {
        indexes.clear()
        characters.forEach { character ->
            val index = character.name.first().toString()
            if (!indexes.contains(index)) indexes.add(index)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IndexFavoriteCharactersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return IndexFavoriteCharactersViewHolder(
            ItemIndexFavoriteCharacterBinding.inflate(inflater, parent, DETACHED_TO_ROOT)
        )
    }

    override fun onBindViewHolder(holder: IndexFavoriteCharactersViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount(): Int = items.size

    class IndexFavoriteCharactersViewHolder(
        private val binding: ItemIndexFavoriteCharacterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: IndexedFavoriteCharactersModel) {
            binding.item = item

            binding.recyclerCharacter.apply {
                layoutManager = GridLayoutManager(context, 3)
                adapter = FavoriteCharacterAdapter()
            }.also {
                (it.adapter as FavoriteCharacterAdapter).setItems(item.favoriteCharacters)
            }
        }
    }
}