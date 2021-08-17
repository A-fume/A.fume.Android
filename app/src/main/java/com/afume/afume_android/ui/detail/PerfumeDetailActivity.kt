package com.afume.afume_android.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.ParcelableWishList
import com.afume.afume_android.databinding.ActivityPerfumeDetailBinding
import com.afume.afume_android.ui.detail.info.DetailInfoFragment
import com.afume.afume_android.ui.detail.note.DetailNoteFragment
import com.afume.afume_android.ui.note.NoteActivity

class PerfumeDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityPerfumeDetailBinding
    lateinit var detailImageAdapter: DetailImageAdapter
    lateinit var viewPagerAdapter: ViewPagerAdapter
    private val viewModel: PerfumeDetailViewModel by viewModels()
    private var isLiked : Boolean = false
    var perfumeIdx: Int = 0
    var perfumeName = ""
    var brandName = ""
    var image : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        perfumeIdx = intent.getIntExtra("perfumeIdx", 1)

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
        viewModel.postPerfumeLike(perfumeIdx)
    }

    private fun initInfo(){
        detailImageAdapter = DetailImageAdapter(baseContext)
        binding.vpPerfumeDetailImage.adapter = detailImageAdapter

        viewModel.getPerfumeInfo(perfumeIdx)
        viewModel.perfumeDetailData.observe(this, Observer {
            binding.item = it
            binding.rbPerfumeDetail.setStar(it.score)
            detailImageAdapter.data = it.imageUrls
            detailImageAdapter.notifyDataSetChanged()

            isLiked = it.isLiked

            perfumeName = it.name
            brandName = it.brandName
            image = it.imageUrls?.get(0)
        })

        binding.indicatorPerfumeDetail.setViewPager(binding.vpPerfumeDetailImage)

    }

    private fun initViewPager(){
        viewPagerAdapter = ViewPagerAdapter(
            supportFragmentManager
        )
        viewPagerAdapter.fragments = listOf(
            DetailInfoFragment(perfumeIdx),
            DetailNoteFragment(perfumeIdx)
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

        binding.actPerfumeDetailIvWrite.setOnClickListener {
            val intent = Intent(this@PerfumeDetailActivity, NoteActivity::class.java)
            val wishListPerfume = ParcelableWishList(perfumeIdx,0,perfumeName,brandName,image)
            intent.run {
                putExtra("wishListPerfume", wishListPerfume)
            }
            startActivity(intent)
        }
    }

    fun onClickBackBtn(view : View){
        finish()
    }
}