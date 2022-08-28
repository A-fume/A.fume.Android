package com.scentsnote.android.ui.home

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.scentsnote.android.R
import com.scentsnote.android.databinding.ActivityMoreNewPerfumeBinding
import com.scentsnote.android.ui.home.adapter.MoreNewListAdapter

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
        newAdapter = MoreNewListAdapter(this,supportFragmentManager) { idx -> homeViewModel.postPerfumeLike(2, idx)}
        binding.rvHomeMoreNew.adapter = newAdapter
    }

    fun onClickBackBtn(view : View){
        finish()
    }
}