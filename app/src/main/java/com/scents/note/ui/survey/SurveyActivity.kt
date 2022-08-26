package com.scents.note.ui.survey

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.scents.note.ScentsNoteApplication
import com.scents.note.R
import com.scents.note.databinding.ActivitySurveyBinding
import com.scents.note.ui.MainActivity
import com.scents.note.ui.filter.ScentsNoteViewPagerAdapter
import com.scents.note.util.*
import java.util.*

class SurveyActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySurveyBinding
    private val viewModel: SurveyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        overridePendingTransition(R.anim.slide_down, R.anim.slide_up)

        initTabWithVp()
        binding.toolbarSurvey.toolbarBtn.setOnClickListener {
            backClickListener()
        }
        clickBtnComplete()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_survey)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        binding.toolbarSurvey.toolbar = R.drawable.icon_btn_cancel
    }

    private fun initTabWithVp() {
        val surveyViewPagerAdapter = ScentsNoteViewPagerAdapter(supportFragmentManager)
        surveyViewPagerAdapter.fragments = listOf(
            SurveyPerfumeFragment(),
            SurveyKeywordFragment(),
            SurveyIncenseFragment()
        )
        binding.vpSurvey.adapter = surveyViewPagerAdapter
        binding.tabSurvey.setupWithViewPager(binding.vpSurvey)
        binding.tabSurvey.apply {
            getTabAt(0)?.text = "향수"
            getTabAt(1)?.text = "키워드"
            getTabAt(2)?.text = "계열"
        }
        val tabListener = TabSelectedListener(binding.tabSurvey)
        binding.tabSurvey.addOnTabSelectedListener(tabListener)
        binding.tabSurvey.changeTabsFont(0)
    }

    private fun clickBtnComplete(){
        binding.btnSurveyApply.setOnClickListener {
            binding.vpSurvey.apply {
                when (currentItem) {
                    0 -> currentItem = 1
                    1 -> currentItem = 2
                    2 -> {
                        ScentsNoteApplication.prefManager.userSurvey = false
                        viewModel.postSurvey(ScentsNoteApplication.prefManager.accessToken)
                        this@SurveyActivity.startActivityWithFinish(MainActivity::class.java)
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if(binding.vpSurvey.currentItem == 0){
            backClickListener()
        }else{
            binding.vpSurvey.currentItem -= 1
        }
    }

    private fun backClickListener(){
        this.createListenerDialog(supportFragmentManager, "survey",
            {
                ScentsNoteApplication.prefManager.userSurvey = false
                this@SurveyActivity.startActivityWithFinish(MainActivity::class.java)
            },
            {}
        )
    }
}