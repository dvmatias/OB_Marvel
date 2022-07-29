package com.cmdv.ph_character_details

import com.cmdv.common.KEY_CHARACTER_ID_ARG
import com.cmdv.common.KEY_CHARACTER_NAME_ARG
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

    /**
     * Character's unique identifier to get its details.
     */
    private var characterName: String? = null

    override fun getExtras() {
        // Get the character's ID to fetch its details
        characterId = intent.extras?.getInt(KEY_CHARACTER_ID_ARG)
        // Get the character's name to display in toolbar
        characterName = intent.extras?.getString(KEY_CHARACTER_NAME_ARG)
    }

    override fun initView() {
        setupToolbar()
        binding.viewModel = viewModel
        binding.characterName = characterName
    }

    override fun observe() {
        with(viewModel) {
            characterId?.let {
                getCharacterDetails(it)
                getCharacterComics(it)
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }
}