package com.cmdv.ph_home.ui.fragments

import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
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
class FavoritesFragment :
    BaseFragment<FavoritesFragment, FragmentFavoritesBinding>(R.layout.fragment_favorites, R.menu.menu_favorites) {
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete) {
            AlertDialog.Builder(requireContext()).apply {
                setTitle(resources.getString(com.cmdv.common.R.string.favorite_fragment_delete_dialog_title))
                    .setMessage(resources.getString(com.cmdv.common.R.string.favorite_fragment_delete_dialog_message))
                    .setPositiveButton(resources.getString(com.cmdv.common.R.string.favorite_fragment_delete_dialog_positive)) { dialog, _ ->
                        dialog.dismiss()
                        viewModel.removeAllFavorites()
                    }
                    .setNegativeButton(resources.getString(com.cmdv.common.R.string.favorite_fragment_delete_dialog_negative)) { dialog, _ ->
                        dialog.dismiss()
                    }
            }.also { it.show() }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setErrorViewState() {
        // TODO
    }
}