package com.scents.note.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.scents.note.ScentsNoteApplication
import com.scents.note.R
import com.scents.note.data.vo.request.FilterInfoP
import com.scents.note.data.vo.request.SendFilter
import com.scents.note.databinding.ActivityMainBinding
import com.scents.note.ui.my.MyViewModel
import com.scents.note.ui.search.SearchViewModel
import com.scents.note.ui.search.SingleViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var searchViewModel: SearchViewModel
    private val myViewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val flag = intent.getIntExtra("flag", 0)
        initBinding()
//        getFilter()
        initNavigation()
        searchViewModel = ViewModelProvider(
            this,
            SingleViewModelFactory.getInstance()
        )[SearchViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()

        getFilter()
        if(ScentsNoteApplication.prefManager.haveToken()){
            lifecycleScope.launch {
                myViewModel.getLikedPerfume()
                myViewModel.getMyPerfume()
            }
        }
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
        val flag = intent.getIntExtra("flag", 0)
        if (flag == 1) {
            val filter = intent?.getParcelableExtra<SendFilter>("filter")
            Log.e("서치 결과 프래그먼트", filter.toString())

            searchViewModel.filter.value = filter

            navController.navigate(R.id.searchHomeFragment)
            navController.navigate(R.id.action_searchHomeFragment_to_searchResultFragment)
        }
        if (flag == 3) {
            val searchText = intent?.getStringExtra("searchText")

            if (searchText != "" && searchText != null) {
                if(searchViewModel.filter.value == null) {
                    searchViewModel.filter.value = SendFilter(mutableListOf(FilterInfoP(idx = 0, name = "", type = 4)),null)
                }
                searchViewModel.filter.value?.apply {
                    filterInfoPList?.clear()
                    filterSeriesPMap?.clear()
                    filterInfoPList?.add(0, FilterInfoP(idx = 0, name = searchText!!, type = 4))
                }
            }
            navController.navigate(R.id.searchHomeFragment)
            navController.navigate(R.id.action_searchHomeFragment_to_searchResultFragment)
        }
        intent.removeExtra("flag")
    }

    fun getBackSearchHome() {
        navController.navigate(R.id.action_searchResultFragment_to_searchHomeFragment)
    }
}