package com.scentsnote.android.ui.detail

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.scentsnote.android.ScentsNoteApplication
import com.scentsnote.android.R
import com.scentsnote.android.data.vo.ParcelableWishList
import com.scentsnote.android.databinding.ActivityPerfumeDetailBinding
import com.scentsnote.android.ui.detail.info.DetailInfoFragment
import com.scentsnote.android.ui.detail.note.DetailNoteFragment
import com.scentsnote.android.ui.note.NoteActivity
import com.scentsnote.android.util.*
import com.bumptech.glide.Glide
import com.scentsnote.android.util.BindingAdapter.setNoteBtnText

class PerfumeDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityPerfumeDetailBinding
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
        initObserve()
    }

    override fun onResume() {
        super.onResume()

        viewModel.getPerfumeInfo(perfumeIdx)
        viewModel.getPerfumeInfoWithReview(perfumeIdx)
    }

    private fun initInfo(){
        viewModel.getPerfumeInfo(perfumeIdx)
        viewModel.perfumeDetailData.observe(this, Observer {
            binding.item = it

            checkLiked = it.isLiked
            isLiked = it.isLiked

            perfumeName = it.name
            brandName = it.brandName
            image = it.imageUrls[0]

            Glide.with(this)
                .load(it.imageUrls[0])
                .into(binding.imgPerfumeDetail)

            reviewIdx = it.reviewIdx

            binding.btnPerfumeDetailNoteWrite.setNoteBtnText(reviewIdx)
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

    private fun initObserve(){
        viewModel.isValidReport.observe(this, Observer {
            if(it)
                this.toast("신고가 접수되었습니다.")
            else
                this.toast("시향 노트 신고 실패")
        })
    }

    private fun setClick(){
        binding.actPerfumeDetailClLike.setOnClickListener{
            if(ScentsNoteApplication.prefManager.haveToken()){
                if(isLiked) {
                    isLiked = false
                    binding.actPerfumeDetailIvLike.setImageResource(R.drawable.favorite_inactive)
                }else{
                    isLiked = true
                    binding.actPerfumeDetailIvLike.setImageResource(R.drawable.favorite_active)
                }
            }else{
                this.createDialog(supportFragmentManager,"login")
            }
        }

        binding.actPerfumeDetailIvWrite.setOnClickListener {
            if(ScentsNoteApplication.prefManager.haveToken()){
                val intent = Intent(this@PerfumeDetailActivity, NoteActivity::class.java)

                val wishListPerfume = ParcelableWishList(perfumeIdx,reviewIdx,perfumeName,brandName,image)
                intent.run {
                    putExtra("wishListPerfume", wishListPerfume)
                    addFlags(FLAG_ACTIVITY_CLEAR_TOP)
                }

                startActivity(intent)
            }else{
                this.createDialog(supportFragmentManager,"login")
            }

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