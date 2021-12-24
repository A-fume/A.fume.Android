package com.afume.afume_android.ui.detail

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
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
import com.afume.afume_android.util.BindingAdapter.setNoteBtnText
import com.afume.afume_android.util.TabSelectedListener
import com.afume.afume_android.util.changeTabsFont

class PerfumeDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityPerfumeDetailBinding
    lateinit var detailImageAdapter: DetailImageAdapter
    lateinit var viewPagerAdapter: ViewPagerAdapter
    private val viewModel: PerfumeDetailViewModel by viewModels()
    private var isLiked : Boolean = false
    private var checkLiked : Boolean = false
    var perfumeIdx: Int = 0
    var reviewIdx: Int = 0
    var perfumeName = ""
    var brandName = ""
    var image : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        perfumeIdx = intent.getIntExtra("perfumeIdx", 1)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_perfume_detail)
        binding.lifecycleOwner=this
        binding.viewModel = viewModel

        binding.svDetail.run {
            header = binding.tabPerfumeDetail
        }

        initInfo()
        initViewPager()
        initTab()
        setClick()

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun initInfo(){
        detailImageAdapter = DetailImageAdapter(baseContext)
        binding.vpPerfumeDetailImage.adapter = detailImageAdapter

        viewModel.getPerfumeInfo(perfumeIdx)
        viewModel.perfumeDetailData.observe(this, Observer {
            binding.item = it

            detailImageAdapter.run {
                replaceAll(ArrayList(it.imageUrls))
                notifyDataSetChanged()
            }

            checkLiked = it.isLiked
            isLiked = it.isLiked

            perfumeName = it.name
            brandName = it.brandName
            image = it.imageUrls[0]

            reviewIdx = it.reviewIdx

            binding.btnPerfumeDetailNoteWrite.setNoteBtnText(reviewIdx)
            binding.indicatorPerfumeDetail.setViewPager(binding.vpPerfumeDetailImage)
        })

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
        binding.tabPerfumeDetail.addOnTabSelectedListener(TabSelectedListener(binding.tabPerfumeDetail))
        binding.tabPerfumeDetail.changeTabsFont(0)
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

            val wishListPerfume = ParcelableWishList(perfumeIdx,reviewIdx,perfumeName,brandName,image)
            intent.run {
                putExtra("wishListPerfume", wishListPerfume)
                addFlags(FLAG_ACTIVITY_CLEAR_TOP)
            }

            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        if(checkLiked != isLiked){
            viewModel.postPerfumeLike(perfumeIdx)
        }

        finish()
    }

    fun onClickBackBtn(view : View){
        if(checkLiked != isLiked){
            viewModel.postPerfumeLike(perfumeIdx)
        }

        finish()
    }
}