package com.cmdv.ph_home.ui.fragments

import com.cmdv.core.base.BaseFragment
import com.cmdv.ph_home.R
import com.cmdv.ph_home.databinding.FragmentCharactersBinding
import com.cmdv.ph_home.ui.viewmodels.CharactersViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * This Fragment is used to display the list of characters.
 */
class CharactersFragment : BaseFragment<CharactersFragment, FragmentCharactersBinding>(R.layout.fragment_characters) {
    private val viewModel by sharedViewModel<CharactersViewModel>()

    override fun initView() {
        binding.viewModel = viewModel
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