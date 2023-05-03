package com.scentsnote.android.ui.survey

import android.os.Bundle
import androidx.activity.viewModels
import com.scentsnote.android.ScentsNoteApplication
import com.scentsnote.android.R
import com.scentsnote.android.databinding.ActivitySurveyBinding
import com.scentsnote.android.ui.MainActivity
import com.scentsnote.android.ui.filter.ScentsNoteViewPagerAdapter
import com.scentsnote.android.utils.*
import com.scentsnote.android.utils.extension.changeTabsFont
import com.scentsnote.android.utils.extension.setOnSafeClickListener
import com.scentsnote.android.utils.base.BaseActivity
import com.scentsnote.android.utils.extension.startActivityWithFinish
import com.scentsnote.android.utils.listener.TabSelectedListener

class SurveyActivity : BaseActivity<ActivitySurveyBinding>(R.layout.activity_survey)  {
    private val viewModel: SurveyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            vm = viewModel
            toolbarSurvey.toolbar = R.drawable.icon_btn_cancel
        }
        overridePendingTransition(R.anim.slide_down, R.anim.slide_up)

        initTabWithVp()
        binding.toolbarSurvey.toolbarBtn.setOnSafeClickListener {
            backClickListener()
        }
        clickBtnComplete()
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
        binding.btnSurveyApply.setOnSafeClickListener {
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