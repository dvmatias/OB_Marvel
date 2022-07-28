package com.cmdv.ph_home

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.cmdv.common.KEY_CHARACTER_ID_ARG
import com.cmdv.core.base.BaseActivity
import com.cmdv.ph_home.databinding.ActivityHomeBinding
import com.cmdv.ph_home.ui.listeners.CharactersFragmentListener
import com.cmdv.ph_home.ui.viewmodels.CharactersViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity<HomeActivity, ActivityHomeBinding>(R.layout.activity_home),
    CharactersFragmentListener {
    private val viewModel by viewModel<CharactersViewModel>()

    private lateinit var navController: NavController

    override fun initView() {
        // Every time this activity starts, remove DB stored characters to get them from service.
        viewModel.removeStoredCharacters()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.content.bottomNav, navController)

        // TODO setup toolbar
    }

    override fun observe() {
        // Get the total characters available in Marvel's API if this fields has not been set.
        if (viewModel.totalCharactersCount == 0) {
            viewModel.getTotalCharactersCount()
        }
    }

    /**
     * Triggered when a character item in the list is clicked. App should navigate to character details.
     *
     * @param characterId
     */
    override fun onCharacterClick(characterId: Int) {
        Bundle().apply {
            putInt(KEY_CHARACTER_ID_ARG, characterId)
        }.also {
            navigator.toCharacterDetails(this, it, false)
        }
    }

    /**
     * Triggered when something went wrong in the characters loading.
     *
     * @param message Error message to display.
     */
    override fun showErrorSnackBar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(this, com.cmdv.common.R.color.marvel_red))
            .show()
    }
}