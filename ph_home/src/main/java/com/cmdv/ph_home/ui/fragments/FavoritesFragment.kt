package com.cmdv.ph_home.ui.fragments

import com.cmdv.core.base.BaseFragment
import com.cmdv.domain.utils.ResponseWrapper.Status.ERROR
import com.cmdv.ph_home.R
import com.cmdv.ph_home.databinding.FragmentFavoritesBinding
import com.cmdv.ph_home.ui.adapters.IndexFavoriteCharacterAdapter
import com.cmdv.ph_home.ui.itemdecorators.IndexFavoriteCharacterDecorator
import com.cmdv.ph_home.ui.viewmodels.FavoritesViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * This Fragment is used to display the list of user's favorites characters.
 */
class FavoritesFragment : BaseFragment<FavoritesFragment, FragmentFavoritesBinding>(R.layout.fragment_favorites) {
    /**
     * Favorites view model.
     */
    private val viewModel: FavoritesViewModel by viewModel()

    /**
     * Indexed favorites adapter.
     */
    private val indexFavoriteCharacterAdapter: IndexFavoriteCharacterAdapter by inject()

    override fun initView() {
        binding.recyclerIndexFavoriteCharacter.addItemDecoration(IndexFavoriteCharacterDecorator())
        binding.adapter = indexFavoriteCharacterAdapter
        binding.viewModel = viewModel
    }

    override fun observe() {
        with(viewModel) {
            getFavorites()
            removeAll.observe(this@FavoritesFragment) { event ->
                event.getContentIfNotHandled()?.let {
                    getFavorites()
                }
            }
            viewModelState.observe(this@FavoritesFragment) { state ->
                if (state == ERROR) setErrorViewState()
            }
        }
    }

    private fun setErrorViewState() {
        // TODO
    }
}