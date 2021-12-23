package com.afume.afume_android.ui.note

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.ParcelableWishList
import com.afume.afume_android.databinding.ActivityNoteBinding
import com.afume.afume_android.ui.detail.PerfumeDetailActivity
import com.afume.afume_android.util.*
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

class NoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityNoteBinding
    lateinit var noteKeywordAdapter : NoteKeywordAdapter
    private val noteViewModel : NoteViewModel by viewModels()
    private val keywordBottomSheetFragment = KeywordBottomSheetFragment()

    lateinit var txtLongevityList : List<TextView>
    lateinit var txtReverbList : List<TextView>
    lateinit var txtGenderList : List<TextView>

    var perfumeIdx: Int = 0
    var reviewIdx: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_note)
        binding.lifecycleOwner = this
        binding.viewModel = noteViewModel
        initNote()

        initObserver()
        setComponentList()
        onSeekBarChangeListener()
        initKeywordList()
    }

    private fun initNote(){
        val wishList = intent?.getParcelableExtra<ParcelableWishList>("wishListPerfume")
        binding.item = wishList

        if(wishList?.reviewIdx == 0){ // 추가일 경우
            perfumeIdx = wishList.perfumeIdx
        }else{ // 조회, 수정, 삭제일 경우
            wishList?.reviewIdx?.let {
                noteViewModel.getReview(it)
                perfumeIdx = wishList.perfumeIdx
                reviewIdx = wishList.reviewIdx
            }
        }
    }

    private fun initObserver(){
        noteViewModel.rating.observe(this, Observer {
            noteViewModel.checkShareBtn()
            noteViewModel.checkCompleteBtn()
            noteViewModel.checkUpdateInfo(0)
        })

        noteViewModel.contentsTxt.observe(this, Observer {
            noteViewModel.checkShareBtn()
            noteViewModel.checkCompleteBtn()
            noteViewModel.checkUpdateInfo(0)
        })

        noteViewModel.selectedKeywordList.observe(this, Observer{
            noteViewModel.checkShareBtn()
            noteViewModel.checkUpdateInfo(0)
        })

        setSeekBarObserve(noteViewModel.longevityProgress)
        setSeekBarObserve(noteViewModel.reverbProgress)
        setSeekBarObserve(noteViewModel.genderProgress)

        setSeasonBtnObserve(noteViewModel.springBtn)
        setSeasonBtnObserve(noteViewModel.summerBtn)
        setSeasonBtnObserve(noteViewModel.fallBtn)
        setSeasonBtnObserve(noteViewModel.winterBtn)

        noteViewModel.shareBtn.observe(this, Observer {
            noteViewModel.checkUpdateInfo(0)
        })

        noteViewModel.showErrorToast.observe(this, Observer {
            this.toastLong("입력 칸을 모두 작성해야 공개가 가능합니다.")
        })
    }

    private fun setSeekBarObserve(seekBar: LiveData<Int>){
        seekBar.observe(this, Observer {
            noteViewModel.checkShareBtn()
            noteViewModel.checkUpdateInfo(0)
        })
    }

    private fun setSeasonBtnObserve(seasonBtn: LiveData<Boolean>){
        seasonBtn.observe(this, Observer {
            noteViewModel.checkShareBtn()
            noteViewModel.checkUpdateInfo(0)
        })
    }

    private fun initKeywordList(){
        val flexboxLayoutManager= FlexboxLayoutManager(this).apply {
            flexDirection= FlexDirection.ROW
            flexWrap= FlexWrap.WRAP
            alignItems = AlignItems.STRETCH
        }

        noteKeywordAdapter = NoteKeywordAdapter(1) { index, add -> noteViewModel.addKeywordList(index, add) }

        binding.rvNoteKeywordList.apply {
            layoutManager=flexboxLayoutManager
            adapter=noteKeywordAdapter
        }
    }

    private fun setComponentList(){
        txtLongevityList = listOf(binding.txtNoteLongevityVeryWeak, binding.txtNoteLongevityWeak, binding.txtNoteLongevityUsual, binding.txtNoteLongevityStrong, binding.txtNoteLongevityVeryStrong)
        txtReverbList = listOf(binding.txtNoteReverbLight, binding.txtNoteReverbUsual, binding.txtNoteReverbHeavy)
        txtGenderList = listOf(binding.txtNoteGenderMan, binding.txtNoteGenderNeuter, binding.txtNoteGenderWoman)
    }

    private fun onSeekBarChangeListener(){
        noteViewModel.longevityProgress.observe(this, Observer {
            if(it > -1){
                binding.sbNoteLongevity.thumb = ContextCompat.getDrawable(this@NoteActivity, R.drawable.seekbar_note_thumb)
                setSelectedSeekBarTxtBold(txtLongevityList,it)
            }
        })

        noteViewModel.reverbProgress.observe(this, Observer {
            if(it > -1){
                binding.sbNoteReverb.thumb = ContextCompat.getDrawable(this@NoteActivity, R.drawable.seekbar_note_thumb)
                setSelectedSeekBarTxtBold(txtReverbList,it)
            }
        })

        noteViewModel.genderProgress.observe(this, Observer {
            if(it > -1){
                binding.sbNoteGender.thumb = ContextCompat.getDrawable(this@NoteActivity, R.drawable.seekbar_note_thumb)
                setSelectedSeekBarTxtBold(txtGenderList,it)
            }
        })
//        binding.sbNoteLongevity.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
//            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
//                setSelectedSeekBarTxtBold(txtReverbList,progress)
//            }
//
//            override fun onStartTrackingTouch(p0: SeekBar?) {
//                binding.sbNoteLongevity.thumb = ContextCompat.getDrawable(this@NoteActivity, R.drawable.seekbar_note_thumb)
//                if(binding.sbNoteLongevity.progress==0){
//                    setSelectedSeekBarTxtBold(txtLongevityList,0)
//                }
//            }
//
//            override fun onStopTrackingTouch(p0: SeekBar?) {
//            }
//
//        })
    }

    private fun setSelectedSeekBarTxtBold(list: List<TextView>, select: Int){
        for(i in list.indices){
           if(i==select){
               list[i].setSelectedSeekBarTxt(true)
           }else{
               list[i].setSelectedSeekBarTxt(false)
           }
        }
    }

    fun onClickBackBtn(view : View){
        if(reviewIdx==0){
            finish()
        }else{
            backBtn()
        }
    }

    override fun onBackPressed() {
        if(reviewIdx==0){
            finish()
        }else{
            backBtn()
        }
    }

    private fun backBtn(){
        noteViewModel.checkUpdateInfo(1)

        noteViewModel.showUpdateDialog.observe(this, Observer {
            if(it){
                val bundle = Bundle()
                bundle.putString("title","save")
                val dialog: CommonDialog = CommonDialog().CustomDialogBuilder()
                    .setBtnClickListener(object : CommonDialog.CustomDialogListener {
                        override fun onPositiveClicked() {
                            noteViewModel.updateReview(reviewIdx)
                            finish()
                        }
                        override fun onNegativeClicked() {
                            finish()
                        }
                    })
                    .getInstance()
                dialog.arguments = bundle
                dialog.show(this.supportFragmentManager, dialog.tag)
            }
            else{
                finish()
            }
        })

        setNoteToastObserve(noteViewModel.isValidNoteUpdate, "시향 노트가 수정되었습니다.", "시향 노트 수정 실패")
    }

    fun onClickDetailBtn(view : View){
        val intent = Intent(this, PerfumeDetailActivity::class.java)

        intent.run {
            putExtra("perfumeIdx", perfumeIdx)
            addFlags(FLAG_ACTIVITY_CLEAR_TOP)
        }

        startActivity(intent)
    }

    fun onClickKeywordBtn(view : View){
        keywordBottomSheetFragment.show(supportFragmentManager, "noteKeywordDialog")
        noteViewModel.convertKeyword()
    }

    fun onClickCompleteBtn(view : View){
        noteViewModel.postReview(perfumeIdx)
        setNoteToastObserve(noteViewModel.isValidNoteAdd, "시향 노트가 추가되었습니다.", "시향 노트 추가 실패")
        finish()
    }

    fun onClickUpdateBtn(view : View){
        noteViewModel.updateReview(reviewIdx)
        setNoteToastObserve(noteViewModel.isValidNoteUpdate, "시향 노트가 수정되었습니다.", "시향 노트 수정 실패")
        finish()
    }

    fun onClickDeleteBtn(view : View){
        val bundle = Bundle()
        bundle.putString("title","delete")
        val dialog: CommonDialog = CommonDialog().CustomDialogBuilder()
            .setBtnClickListener(object : CommonDialog.CustomDialogListener {
                override fun onPositiveClicked() {
                    noteViewModel.deleteReview(reviewIdx)
                    finish()
                }
                override fun onNegativeClicked() {
                }
            })
            .getInstance()
        dialog.arguments = bundle
        dialog.show(this.supportFragmentManager, dialog.tag)

        setNoteToastObserve(noteViewModel.isValidNoteDelete, "시향 노트가 삭제되었습니다.", "시향 노트 삭제 실패")
    }

    private fun setNoteToastObserve(noteNetworkState: LiveData<Boolean>,success: String, fail: String){
        noteNetworkState.observe(this, Observer {
            if(it){
                this.toast(success)
            }else{
                this.toast(fail)
            }
        })
    }
}