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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_perfume_detail)
        binding.lifecycleOwner=this
        binding.viewModel = viewModel

        initInfo()
        initViewPager()
        initTab()

    }

    private fun initInfo(){
//        val item = PerfumeDetailData(
//            image = listOf(R.drawable.dummy_perfume_image,R.drawable.dummy_perfume_image,R.drawable.dummy_perfume_image,R.drawable.dummy_perfume_image),
//            brand = "GUCCI",
//            name = "뿌르 옴므 오 드 뚜왈렛",
//            rate = 2.5f
//        )

        detailImageAdapter = DetailImageAdapter(baseContext)
        binding.vpPerfumeDetailImage.adapter = detailImageAdapter

        viewModel.getPerfumeInfo(1)
        viewModel.perfumeDetailData.observe(this, Observer {
            binding.rbPerfumeDetail.setStar(it.score)
            detailImageAdapter.data = it.imageUrls
            detailImageAdapter.notifyDataSetChanged()
        })

//        detailImageAdapter.data = item.image

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
}