package com.cmdv.ph_home.ui.fragments

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.cmdv.common.R.*
import com.cmdv.core.base.BaseFragment
import com.cmdv.domain.utils.ResponseWrapper.Status.ERROR
import com.cmdv.ph_home.R
import com.cmdv.ph_home.databinding.FragmentFavoritesBinding
import com.cmdv.ph_home.ui.adapters.IndexFavoriteCharacterAdapter
import com.cmdv.ph_home.ui.itemdecorators.IndexFavoriteCharacterDecorator
import com.cmdv.ph_home.ui.listeners.CharactersFragmentListener
import com.cmdv.ph_home.ui.listeners.FavoriteCharacterAdapterListener
import com.cmdv.ph_home.ui.viewmodels.FavoritesViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * This Fragment is used to display the list of user's favorites characters.
 *
 * @author matias.delv.dom@gmail.com
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

    /**
     * Listener to communicate actions in this fragment with [com.cmdv.ph_home.HomeActivity].
     */
    private lateinit var fragmentListener: CharactersFragmentListener

    /**
     * Implementation.This object implements the functions in [FavoriteCharacterAdapterListener].
     */
    private val favoriteCharacterListener = object : FavoriteCharacterAdapterListener {
        override fun onFavoriteClick(id: Int, index: Int, name: String) {
            AlertDialog.Builder(requireContext()).apply {
                setTitle(resources.getString(string.favorite_fragment_delete_dialog_title))
                    .setMessage(String.format(resources.getString(string.favorite_fragment_delete_dialog_message), name))
                    .setPositiveButton(resources.getString(string.favorite_fragment_delete_dialog_positive)) { dialog, _ ->
                        dialog.dismiss()
                        viewModel.removeFavorite(id, index)
                    }
                    .setNegativeButton(resources.getString(string.favorite_fragment_delete_all_dialog_negative)) { dialog, _ -> dialog.dismiss() }
            }.also { it.show() }
        }
    }

    override fun initView() {
        if (activity is CharactersFragmentListener)
            fragmentListener = activity as CharactersFragmentListener
        else
            throw IllegalAccessError("Calling activity must implement CharactersFragmentListener")
        binding.recyclerIndexFavoriteCharacter.addItemDecoration(IndexFavoriteCharacterDecorator())
        binding.adapter = indexFavoriteCharacterAdapter
        binding.viewModel = viewModel
        binding.listener = favoriteCharacterListener
    }

    override fun observe() {
        with(viewModel) {
            getFavorites()
            removeAll.observe(this@FavoritesFragment) { event ->
                event.getContentIfNotHandled()?.let {
                    getFavorites()
                }
            }
            favoriteCharacters.observe(this@FavoritesFragment) {
                activity?.invalidateOptionsMenu()
            }
            viewModelState.observe(this@FavoritesFragment) { state ->
                if (state == ERROR) setErrorViewState()
            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val valid = viewModel.favoriteCharacters.value?.size != 0
        menu.findItem(R.id.action_delete).apply {
            isEnabled = valid
            isVisible = valid
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete) {
            AlertDialog.Builder(requireContext()).apply {
                setTitle(resources.getString(string.favorite_fragment_delete_all_dialog_title))
                    .setMessage(resources.getString(string.favorite_fragment_delete_all_dialog_message))
                    .setPositiveButton(resources.getString(string.favorite_fragment_delete_all_dialog_positive)) { dialog, _ ->
                        dialog.dismiss()
                        viewModel.removeAllFavorites()
                    }
                    .setNegativeButton(resources.getString(string.favorite_fragment_delete_all_dialog_negative)) { dialog, _ -> dialog.dismiss() }
            }.also { it.show() }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setErrorViewState() {
        fragmentListener.showErrorSnackBar("Something went wrong. Please try again later.")
    }
}