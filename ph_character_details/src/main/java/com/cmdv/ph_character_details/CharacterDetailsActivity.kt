package com.cmdv.ph_character_details

import android.view.MenuItem
import android.widget.Toast
import com.cmdv.common.KEY_CHARACTER_ID_ARG
import com.cmdv.common.KEY_CHARACTER_NAME_ARG
import com.cmdv.core.base.BaseActivity
import com.cmdv.ph_character_details.databinding.ActivityCharacterDetailsBinding
import com.cmdv.ph_character_details.ui.adapters.ComicAdapter
import com.cmdv.ph_character_details.ui.adapters.SerieAdapter
import com.cmdv.ph_character_details.ui.listeners.CharacterDetailsListener
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

    /**
     * View adapter for [R.id.recyclerSerie]
     */
    private val serieAdapter: SerieAdapter by inject()

    /**
     * Implementation. This object implements [CharacterDetailsListener].
     */
    private val characterDetailsListener = object : CharacterDetailsListener {
        override fun onFavoriteClick(id: Int, isFavorite: Boolean) {
            Toast.makeText(this@CharacterDetailsActivity, "Not implemented yet.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getExtras() {
        // Get the character's ID to fetch its details
        characterId = intent.extras?.getInt(KEY_CHARACTER_ID_ARG)
        // Get the character's name to display in toolbar
        characterName = intent.extras?.getString(KEY_CHARACTER_NAME_ARG)
    }

    override fun initView() {
        setupToolbar()
        binding.viewModel = viewModel
        binding.listener = characterDetailsListener
        binding.characterName = characterName
        binding.comicAdapter = comicAdapter
        binding.serieAdapter = serieAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun observe() {
        with(viewModel) {
            getCharacterDetails(characterId)
            eventCharacterDetailsReady.observe(this@CharacterDetailsActivity) { event ->
                if (!event.hasBeenHandled) {
                    event.getContentIfNotHandled()
                    getCharacterComics(characterId!!)
                    getCharacterSeries(characterId!!)
                    getIsFavoriteCharacter(characterId!!)
                }
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