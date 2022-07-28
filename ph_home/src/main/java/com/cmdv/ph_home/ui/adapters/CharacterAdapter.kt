package com.cmdv.ph_home.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmdv.common.DETACHED_TO_ROOT
import com.cmdv.common.extensions.addAllNoRepeated
import com.cmdv.domain.models.CharacterModel
import com.cmdv.ph_home.databinding.ItemCharacterBinding
import com.cmdv.ph_home.databinding.ItemCharacterHeaderMainBinding
import com.cmdv.ph_home.databinding.ItemLoadingBinding
import com.cmdv.ph_home.ui.adapters.CharacterAdapterViewType.*
import com.cmdv.ph_home.ui.listeners.CharacterAdapterListener

private const val ITEM_COUNT_BEFORE_LOAD_MORE = 6

/**
 * Adapter class for [CharacterModel] items.
 */
class CharacterAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * List of characters to be displayed.
     */
    private val characters: ArrayList<CharacterModel> = arrayListOf()

    /**
     * If true, this adapter is loading more characters.
     */
    internal var isLoading = true
        set(value) {
            field = value
            notifyItemChanged(itemCount - 1)
        }

    /**
     * Event listener over character items.
     */
    internal var listener: CharacterAdapterListener? = null

    fun addItems(characters: List<CharacterModel>) {
        val startIndex = itemCount
        this.characters.addAllNoRepeated(characters)
        notifyItemRangeChanged(startIndex, characters.size)
    }

    override fun getItemViewType(position: Int): Int =
        when (position) {
            0 -> HEADER_MAIN.viewType
            itemCount - 1 -> FOOTER_LOADING.viewType
            else -> CHARACTER.viewType
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            HEADER_MAIN.viewType -> HeaderViewHolder(
                ItemCharacterHeaderMainBinding.inflate(LayoutInflater.from(parent.context), parent, DETACHED_TO_ROOT)
            )
            CHARACTER.viewType -> CharacterViewHolder(
                ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, DETACHED_TO_ROOT)
            )
            FOOTER_LOADING.viewType -> LoadingViewHolder(
                ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, DETACHED_TO_ROOT)
            )
            else -> throw IllegalStateException("")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            HEADER_MAIN.viewType -> (holder as HeaderViewHolder)
            CHARACTER.viewType -> {
                val characterIndex = position - 1
                (holder as CharacterViewHolder).bindItem(characters[characterIndex], listener, characterIndex)
            }
            FOOTER_LOADING.viewType -> (holder as LoadingViewHolder).show(isLoading)
        }
    }

    override fun getItemCount(): Int = this.characters.size + 2

    /**
     * The adapter is empty (no characters) when itonly contains the header view and the footer view.
     */
    fun isEmpty(): Boolean = itemCount == 2

    fun onScroll(lastVisibleItemPosition: Int) {
        if (shouldLoadMore(lastVisibleItemPosition) && !isLoading) {
            isLoading = true
            listener?.onLoadMoreCharacters(charactersCount())
        }
    }

    /**
     * The app should load more characters if there are characters already displayed the app is not loading characters
     * and if the last visible character is the sixth counting backwards.
     */
    private fun shouldLoadMore(lastVisibleItemPosition: Int): Boolean =
        !isLoading && charactersCount() != 0 && charactersCount() <= lastVisibleItemPosition + ITEM_COUNT_BEFORE_LOAD_MORE

    /**
     * Returns the number of characters plus the header and the footer view.
     */
    private fun charactersCount() = itemCount - 2

    /**
     * View holder class. Header item.
     */
    class HeaderViewHolder(val binding: ItemCharacterHeaderMainBinding) : RecyclerView.ViewHolder(binding.root)

    /**
     * View holder class. Character item.
     */
    class CharacterViewHolder(private val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(character: CharacterModel, listener: CharacterAdapterListener?, characterIndex: Int) {
            binding.character = character
            binding.listener = listener
            binding.characterIndex = characterIndex
        }
    }

    /**
     * View holder class. Loading item.
     */
    class LoadingViewHolder(private val binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun show(show: Boolean) {
            binding.root.visibility = if (show) View.VISIBLE else View.GONE
        }
    }
}