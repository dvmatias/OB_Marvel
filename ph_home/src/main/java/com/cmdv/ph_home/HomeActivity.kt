package com.cmdv.ph_home

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.cmdv.core.base.BaseActivity
import com.cmdv.ph_home.databinding.ActivityHomeBinding
import com.cmdv.ph_home.ui.viewmodels.CharactersViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity<HomeActivity, ActivityHomeBinding>(R.layout.activity_home) {
    private val viewModel by viewModel<CharactersViewModel>()

    private lateinit var navController: NavController

    override fun initView() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.content.bottomNav, navController)

        // TODO setup toolbar
    }

    override fun observe() {
        if (viewModel.totalCharactersCount == 0) {
            viewModel.getTotalCharactersCount()
        }
    }
}