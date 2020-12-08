package com.afume.afume_android.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.HomePerfumeListData
import com.afume.afume_android.databinding.ActivityMoreNewPerfumeBinding
import com.afume.afume_android.databinding.FragmentHomeBinding
import com.afume.afume_android.ui.home.adapter.MoreNewListAdapter
import com.afume.afume_android.ui.home.adapter.NewListAdapter

class MoreNewPerfumeActivity : AppCompatActivity() {
    lateinit var binding: ActivityMoreNewPerfumeBinding
    lateinit var newAdapter: MoreNewListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_more_new_perfume)
        binding.lifecycleOwner=this

        initNewList()
    }

    private fun initNewList(){
        newAdapter =
            MoreNewListAdapter(this)
        binding.rvHomeMoreNew.adapter = newAdapter

        newAdapter.data = mutableListOf(
            HomePerfumeListData(
                image = null,
                brand = "1번 브랜드",
                name = "1번향수",
                like = 1
            ),
            HomePerfumeListData(
                image = null,
                brand = "2번 브랜드",
                name = "2번향수",
                like = 1
            ),
            HomePerfumeListData(
                image = null,
                brand = "3번 브랜드",
                name = "3번향수",
                like = 1
            ),
            HomePerfumeListData(
                image = null,
                brand = "4번 브랜드",
                name = "4번향수",
                like = 0
            )
        )
        newAdapter.notifyDataSetChanged()

    }
}