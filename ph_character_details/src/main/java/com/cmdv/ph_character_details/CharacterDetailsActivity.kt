package com.cmdv.ph_character_details

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

    override fun initView() {
        // TODO("Not yet implemented")
    }

    override fun observe() {
        // TODO("Not yet implemented")
    }
}