package com.afume.afume_android.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.request.RequestSearch
import com.afume.afume_android.databinding.ActivityMainBinding
import com.afume.afume_android.ui.search.SearchViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initNavigation()
    }

    override fun onResume() {
        super.onResume()

        getFilter()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
    }

    private fun initNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottomNav.setupWithNavController(navController)
        bottomNav.itemIconTintList = null
    }

    private fun getFilter() {
        if (intent.getIntExtra("flag", 0) == 1) {
            val filter = intent?.getParcelableExtra<RequestSearch>("filter")
            Log.e("서치 결과 프래그먼트", filter.toString())
            searchViewModel.filterList.value = filter?.filterInfoPList
            navController.navigate(R.id.searchHomeFragment)
            navController.navigate(R.id.action_searchHomeFragment_to_searchResultFragment)

        }
    }
}