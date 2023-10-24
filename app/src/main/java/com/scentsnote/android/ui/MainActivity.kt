package com.scentsnote.android.ui

import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.scentsnote.android.R
import com.scentsnote.android.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.scentsnote.android.ScentsNoteApplication.Companion.firebaseAnalytics
import com.scentsnote.android.utils.base.BaseActivity
import com.scentsnote.android.utils.extension.setClickEvent

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private lateinit var navController: NavController
    private lateinit var bottomNav: BottomNavigationView

    private lateinit var toast: Toast
    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toast = Toast.makeText(this, getString(R.string.txt_app_end_message), Toast.LENGTH_SHORT)

        initNavigation()
        initNavigationEvent()
    }

    override fun onPause() {
        super.onPause()
        toast.cancel()
    }

    private fun initNavigation() {
        bottomNav = findViewById(R.id.bottom_navigation)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottomNav.setupWithNavController(navController)
        bottomNav.itemIconTintList = null
    }

    private fun initNavigationEvent(){
        bottomNav.setOnItemSelectedListener { menuItem ->
            var buttonName = ""

            when (menuItem.itemId) {
                R.id.homeFragment -> {
                    buttonName = "NavigationHome"
                }
                R.id.searchFragment -> {
                    buttonName = "NavigationSearch"
                }
                R.id.myPageFragment -> {
                    buttonName = "NavigationMy"
                }
            }

            firebaseAnalytics.setClickEvent(buttonName)

            navController.navigate(menuItem.itemId)
            true
        }
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.homeFragment) {
            if (System.currentTimeMillis() - backPressedTime < 2000) {
                finish()
            } else {
                showExitToast()
            }
            backPressedTime = System.currentTimeMillis()
        } else {
            super.onBackPressed()
        }
    }

    private fun showExitToast() {
        toast.cancel()
        toast.show()
    }
}