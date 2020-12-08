package com.afume.afume_android.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.PagerSnapHelper
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.PerfumeDetailData
import com.afume.afume_android.databinding.ActivityPerfumeDetailBinding
import com.afume.afume_android.detail.info.DetailInfoFragment
import com.afume.afume_android.detail.note.DetailNoteFragment

class PerfumeDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityPerfumeDetailBinding
    lateinit var detailImageAdapter: DetailImageAdapter
    lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_perfume_detail)
        binding.lifecycleOwner=this

        initInfo()
        initViewPager()
        initTab()
    }

    private fun initInfo(){
        val item = PerfumeDetailData(
            image = listOf(R.drawable.dummy_perfume_image,R.drawable.dummy_perfume_image),
            brand = "GUCCI",
            name = "뿌르 옴므 오 드 뚜왈렛",
            rate = 2.5f
        )

        binding.rbPerfumeDetail.setStar(2.5f)

        detailImageAdapter = DetailImageAdapter()
        binding.rvPerfumeDetailImage.adapter = detailImageAdapter

        detailImageAdapter.data = item.image
        binding.item = item

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvPerfumeDetailImage)

        binding.indicatorPerfumeDetail.attachTo(binding.rvPerfumeDetailImage)

    }

    private fun initViewPager(){
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
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