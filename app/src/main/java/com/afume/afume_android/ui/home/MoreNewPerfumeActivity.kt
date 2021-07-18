package com.afume.afume_android.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivityMoreNewPerfumeBinding
import com.afume.afume_android.ui.home.adapter.MoreNewListAdapter

class MoreNewPerfumeActivity : AppCompatActivity() {
    lateinit var binding: ActivityMoreNewPerfumeBinding
    lateinit var newAdapter: MoreNewListAdapter
    private val homeViewModel : HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_more_new_perfume)
        binding.lifecycleOwner=this
        binding.viewModel = homeViewModel

        initNewList()
    }

    private fun initNewList(){
        newAdapter = MoreNewListAdapter(supportFragmentManager) { idx -> homeViewModel.postPerfumeLike(2, idx)}
        binding.rvHomeMoreNew.adapter = newAdapter

    }
}