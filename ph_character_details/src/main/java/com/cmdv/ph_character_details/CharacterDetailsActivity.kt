package com.cmdv.ph_character_details

import com.cmdv.common.KEY_CHARACTER_ID_ARG
import com.cmdv.core.base.BaseActivity
import com.cmdv.ph_character_details.databinding.ActivityCharacterDetailsBinding
import com.cmdv.ph_character_details.ui.viewmodels.CharacterDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterDetailsActivity :
    BaseActivity<CharacterDetailsActivity, ActivityCharacterDetailsBinding>(R.layout.activity_character_details) {
    /**
     * Android view model.
     */
    private val viewModel by viewModel<CharacterDetailsViewModel>()

    /**
     * Character's unique identifier to get its details.
     */
    private var characterId: Int? = null

    override fun getExtras() {
        // Get the character ID to fetch its details
        characterId = intent.extras?.getInt(KEY_CHARACTER_ID_ARG)
    }

    override fun initView() {
        // TODO
    }

    override fun observe() {
        with(viewModel) {
            characterId?.let {
                getCharacterDetails(it)
                getCharacterComics(it)
            }
        }
    }
}