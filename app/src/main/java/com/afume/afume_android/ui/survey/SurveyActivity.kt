package com.afume.afume_android.ui.survey

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivitySurveyBinding
import com.afume.afume_android.ui.filter.AfumeViewPagerAdapter
import com.afume.afume_android.util.TabSelectedListener

class SurveyActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySurveyBinding
    private val viewModel: SurveyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        overridePendingTransition(R.anim.slide_down,R.anim.slide_up)

        initTabWithVp()
        binding.toolbarSurvey.toolbarBtn.setOnClickListener {
            finish()
        }
    }

    private fun initBinding(){
        binding = DataBindingUtil.setContentView(this,R.layout.activity_survey)
        binding.lifecycleOwner=this
        binding.toolbarSurvey.toolbar=R.drawable.icon_btn_cancel
    }

    private fun initTabWithVp(){
        val surveyViewPagerAdapter=AfumeViewPagerAdapter(supportFragmentManager)
        surveyViewPagerAdapter.fragments= listOf(
            SurveyPerfumeFragment(),
            SurveyKeywordFragment(),
            SurveyIncenseFragment()
        )
        binding.vpSurvey.adapter=surveyViewPagerAdapter
        binding.tabSurvey.setupWithViewPager(binding.vpSurvey)
        binding.tabSurvey.apply{
            getTabAt(0)?.text="향수"
            getTabAt(1)?.text="키워드"
            getTabAt(2)?.text="계열"
        }
        val tabListener = TabSelectedListener(binding.tabSurvey)
        binding.tabSurvey.addOnTabSelectedListener(tabListener)

    }
}