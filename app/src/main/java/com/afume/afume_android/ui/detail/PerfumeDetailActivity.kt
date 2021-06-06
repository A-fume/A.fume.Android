package com.afume.afume_android.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivityPerfumeDetailBinding
import com.afume.afume_android.ui.detail.info.DetailInfoFragment
import com.afume.afume_android.ui.detail.note.DetailNoteFragment

class PerfumeDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityPerfumeDetailBinding
    lateinit var detailImageAdapter: DetailImageAdapter
    lateinit var viewPagerAdapter: ViewPagerAdapter
    private val viewModel: PerfumeDetailViewModel by viewModels()
    private var isLiked : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_perfume_detail)
        binding.lifecycleOwner=this
        binding.viewModel = viewModel

        initInfo()
        initViewPager()
        initTab()
        setClick()

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.postPerfumeLike(1)
    }

    private fun initInfo(){
        detailImageAdapter = DetailImageAdapter(baseContext)
        binding.vpPerfumeDetailImage.adapter = detailImageAdapter

        viewModel.getPerfumeInfo(1)
        viewModel.perfumeDetailData.observe(this, Observer {
            binding.item = it
            binding.rbPerfumeDetail.setStar(it.score)
            detailImageAdapter.data = it.imageUrls
            detailImageAdapter.notifyDataSetChanged()

            isLiked = it.isLiked
        })

        binding.indicatorPerfumeDetail.setViewPager(binding.vpPerfumeDetailImage)

    }

    private fun initViewPager(){
        viewPagerAdapter = ViewPagerAdapter(
            supportFragmentManager
        )
        viewPagerAdapter.fragments = listOf(
            DetailInfoFragment(),
            DetailNoteFragment()
        )

        binding.vpPerfumeDetail.adapter = viewPagerAdapter
    }

    private fun initTab(){
        binding.tabPerfumeDetail.setupWithViewPager(binding.vpPerfumeDetail)
        binding.tabPerfumeDetail.apply {
            getTabAt(0)?.text = "향수 정보"
            getTabAt(1)?.text = "시향 노트"
        }
    }

    private fun setClick(){
        binding.actPerfumeDetailClLike.setOnClickListener{
            if(isLiked) {
                isLiked = false
                binding.actPerfumeDetailIvLike.setImageResource(R.drawable.favorite_inactive)
            }else{
                isLiked = true
                binding.actPerfumeDetailIvLike.setImageResource(R.drawable.favorite_active)
            }
        }
    }
}