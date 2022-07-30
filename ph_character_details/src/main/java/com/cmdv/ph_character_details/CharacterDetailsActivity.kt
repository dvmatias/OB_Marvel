package com.cmdv.ph_character_details

import com.cmdv.common.KEY_CHARACTER_ID_ARG
import com.cmdv.common.KEY_CHARACTER_NAME_ARG
import com.cmdv.core.base.BaseActivity
import com.cmdv.ph_character_details.databinding.ActivityCharacterDetailsBinding
import com.cmdv.ph_character_details.ui.adapters.ComicAdapter
import com.cmdv.ph_character_details.ui.viewmodels.CharacterDetailsViewModel
import org.koin.android.ext.android.inject
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

    /**
     * View adapter for [R.id.recyclerComic]
     */
    private val comicAdapter: ComicAdapter by inject()

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
        binding.comicAdapter = comicAdapter
    }

    override fun observe() {
        with(viewModel) {
            getCharacterDetails(characterId)
            character.observe(this@CharacterDetailsActivity) { character ->
                getCharacterComics(character.id)
                getCharacterSeries(character.id)
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