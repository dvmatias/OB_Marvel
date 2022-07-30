package com.cmdv.ph_home.ui.fragments

import androidx.recyclerview.widget.RecyclerView
import com.cmdv.core.base.BaseFragment
import com.cmdv.domain.utils.ResponseWrapper.Status.*
import com.cmdv.ph_home.R
import com.cmdv.ph_home.databinding.FragmentCharactersBinding
import com.cmdv.ph_home.ui.adapters.CharacterAdapter
import com.cmdv.ph_home.ui.layoutmanagers.CharacterLayoutManager
import com.cmdv.ph_home.ui.listeners.CharacterAdapterListener
import com.cmdv.ph_home.ui.listeners.CharactersFragmentListener
import com.cmdv.ph_home.ui.viewmodels.CharactersViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * This Fragment is used to display the list of characters.
 */
class CharactersFragment : BaseFragment<CharactersFragment, FragmentCharactersBinding>(R.layout.fragment_characters) {
    /**
     * View model.
     */
    private val viewModel by sharedViewModel<CharactersViewModel>()

    /**
     * View adapter for [R.id.recyclerCharacter] recycler view.
     */
    private val characterAdapter: CharacterAdapter by inject()

    /**
     * Layout manager for [R.id.recyclerCharacter] recycler view.
     */
    private lateinit var characterLayoutManager: CharacterLayoutManager

    /**
     * Listener to communicate actions in this fragment with [com.cmdv.ph_home.HomeActivity].
     */
    private lateinit var fragmentListener: CharactersFragmentListener

    /**
     * Implementation.This object implements the functions in [CharacterAdapterListener].
     */
    private val characterAdapterListener = object : CharacterAdapterListener {
        override fun onLoadMoreCharacters(offset: Int) {
            viewModel.getCharacters(fetch = true, offset = offset)
        }

        override fun onCharacterClick(characterId: Int, characterName: String) {
            fragmentListener.onCharacterClick(characterId, characterName)
        }

        override fun onFavoriteClick(characterId: Int, characterIndex: Int, isFavourite: Boolean) {
            when (isFavourite) {
                true -> viewModel.addFavorite(characterId, characterIndex)
                false -> viewModel.removeFavorite(characterId, characterIndex)
            }
        }
    }

    /**
     * This listener communicates to [CharacterAdapter] a scroll action to triggers the load for more characters if
     * needed.
     */
    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            characterAdapter.onScroll(characterLayoutManager.findLastVisibleItemPosition())
        }
    }

    override fun initView() {
        fragmentListener = activity as CharactersFragmentListener
        characterLayoutManager = CharacterLayoutManager(requireContext(), characterAdapter)
        binding.recyclerCharacter.apply {
            addOnScrollListener(scrollListener)
            itemAnimator = null
        }

        binding.viewModel = viewModel
        binding.adapter = characterAdapter.also { it.listener = characterAdapterListener }
        binding.layoutManager = characterLayoutManager
    }

    override fun observe() {
        with(viewModel) {
            if (totalCharactersCount != 0) {
                viewModel.getCharacters(fetch = false)
            }
            addedFavoritePosition.observe(this@CharactersFragment) { event ->
                event.getContentIfNotHandled()?.let { position ->
                    handleFavorite(position, true)
                }
            }
            removedFavoritePosition.observe(this@CharactersFragment) { event ->
                event.getContentIfNotHandled()?.let { position ->
                    handleFavorite(position, false)
                }
            }
            viewModelState.observe(this@CharactersFragment) {state ->
                if (state == ERROR) setErrorViewState()
            }
        }
    }

    /**
     * Handles the update favorite status action over a character.
     *
     * @param updatedPosition Character position to be updated.
     * @param isFavorite true if the character is currently a favorite, false if not.
     */
    private fun handleFavorite(updatedPosition: Int, isFavorite: Boolean) {
        characterAdapter.updateFavorite(updatedPosition, isFavorite)
    }

    private fun setErrorViewState() {
        characterAdapter.isLoading = false
        fragmentListener.showErrorSnackBar("Something went wrong. Please try again later.")
    }
}