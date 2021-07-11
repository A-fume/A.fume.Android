package com.afume.afume_android.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.afume.afume_android.AfumeApplication
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.request.FilterInfoP
import com.afume.afume_android.data.vo.request.SendFilter
import com.afume.afume_android.databinding.ActivityMainBinding
import com.afume.afume_android.ui.search.SearchViewModel
import com.afume.afume_android.ui.search.SingleViewModelFactory
import com.afume.afume_android.util.toastLong
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initNavigation()
        initLoginInfo()
        searchViewModel= ViewModelProvider(this, SingleViewModelFactory.getInstance())[SearchViewModel::class.java]
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

    private fun initLoginInfo(){
        if(AfumeApplication.prefManager.haveToken()){
            this.toastLong("자동 로그인되었습니다.")
        }
    }

    private fun getFilter() {
        if (intent.getIntExtra("flag", 0) == 1) {
            val filter = intent?.getParcelableExtra<SendFilter>("filter")
            Log.e("서치 결과 프래그먼트", filter.toString())

            searchViewModel.filter.value=filter

            navController.navigate(R.id.searchHomeFragment)
            navController.navigate(R.id.action_searchHomeFragment_to_searchResultFragment)
        }
        if (intent.getIntExtra("flag", 0) == 3) {

            val searchText = intent?.getStringExtra("searchText")

            if(searchText != ""&& searchText !=null) {

                searchViewModel.filter.value?.apply {
                    filterInfoPList?.clear()
                    filterSeriesPMap?.clear()
                    filterInfoPList?.add(0, FilterInfoP(idx = 0, name = searchText!!, type = 4))
                }
            }
            navController.navigate(R.id.searchHomeFragment)
            navController.navigate(R.id.action_searchHomeFragment_to_searchResultFragment)
        }
    }
}