package com.cmdv.ph_home

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.cmdv.core.base.BaseActivity
import com.cmdv.ph_home.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity<HomeActivity, ActivityHomeBinding>(R.layout.activity_home) {
    private lateinit var navController: NavController

    override fun initView() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.content.bottomNav, navController)

        // TODO setup toolbar
    }

    override fun observe() {
        TODO("Not yet implemented")
    }
}