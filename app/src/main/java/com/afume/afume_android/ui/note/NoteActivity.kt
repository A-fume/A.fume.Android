package com.afume.afume_android.ui.note

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivityNoteBinding
import com.afume.afume_android.ui.detail.PerfumeDetailActivity
import com.afume.afume_android.util.startActivity

class NoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityNoteBinding
    private val noteViewModel : NoteViewModel by viewModels()
    private val keywordBottomSheetFragment = KeywordBottomSheetFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_note)
        binding.lifecycleOwner = this
        binding.viewModel = noteViewModel
    }

    fun onClickBackBtn(view : View){
        finish()
    }

    fun onClickDetailBtn(view : View){
        this.startActivity(PerfumeDetailActivity::class.java)
    }

    fun onClickKeywordBtn(view : View){
        keywordBottomSheetFragment.show(supportFragmentManager, "noteKeywordDialog")
    }
}