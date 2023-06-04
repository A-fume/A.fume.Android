package com.scentsnote.android.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.scentsnote.android.R
import com.scentsnote.android.data.vo.request.FilterInfoP
import com.scentsnote.android.data.vo.request.SendFilter
import com.scentsnote.android.databinding.ActivityMainBinding
import com.scentsnote.android.viewmodel.search.SearchViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.scentsnote.android.ui.search.SearchFragmentType
import com.scentsnote.android.utils.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main)  {
    private lateinit var navController: NavController
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavigation()
    }

    override fun onResume() {
        super.onResume()

        getFilter()
    }

    private fun initNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottomNav.setupWithNavController(navController)
        bottomNav.itemIconTintList = null
    }

    private fun getFilter() {
        val flag = intent.getIntExtra("flag", 0)
        if (flag == 3) { // click search
            searchViewModel.setPageType(SearchFragmentType.RESULT)
            val searchText = intent?.getStringExtra("searchText")

            if (searchText != "" && searchText != null) {
                if(searchViewModel.filter.value == null) {
                    searchViewModel.filter.value = SendFilter(mutableListOf(FilterInfoP(idx = 0, name = "", type = 4)),null)
                }
                searchViewModel.filter.value?.apply {
                    filterInfoPList?.clear()
                    filterSeriesPMap?.clear()
                    filterInfoPList?.add(0, FilterInfoP(idx = 0, name = searchText, type = 4))
                }
            }
            navController.navigate(R.id.searchFragment)
        }
        intent.removeExtra("flag")
    }

}