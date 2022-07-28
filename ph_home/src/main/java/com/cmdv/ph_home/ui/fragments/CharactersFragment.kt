package com.cmdv.ph_home.ui.fragments

import com.cmdv.core.base.BaseFragment
import com.cmdv.ph_home.R
import com.cmdv.ph_home.databinding.FragmentCharactersBinding
import com.cmdv.ph_home.ui.adapters.CharacterAdapter
import com.cmdv.ph_home.ui.layoutmanagers.CharacterLayoutManager
import com.cmdv.ph_home.ui.listeners.CharacterAdapterListener
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
     * Implementation.This object implements the functions in [CharacterAdapterListener].
     */
    private val characterAdapterListener = object : CharacterAdapterListener {
        override fun onLoadMoreCharacters(offset: Int) {
            // TODO
        }

        override fun onCharacterClick(characterId: Int) {
           // TODO
        }

        override fun onFavoriteClick(characterId: Int, characterIndex: Int, isFavourite: Boolean) {
            // TODO
        }
    }

    override fun initView() {
        characterLayoutManager = CharacterLayoutManager(requireContext(), characterAdapter)
        binding.viewModel = viewModel
        binding.adapter = characterAdapter.also { it.listener = characterAdapterListener }
        binding.layoutManager = characterLayoutManager
    }

    override fun observe() {
        with(viewModel) {
            if (totalCharactersCount != 0) {
                viewModel.getCharacters(fetch = false)
            }
            viewModelState.observe(this@CharactersFragment) {
                // TODO
                // if (state == ERROR)  setErrorViewState()
            }
        }
    }
}