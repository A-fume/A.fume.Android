package com.afume.afume_android.ui.survey

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.afume.afume_android.AfumeApplication
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivitySurveyBinding
import com.afume.afume_android.ui.MainActivity
import com.afume.afume_android.ui.filter.AfumeViewPagerAdapter
import com.afume.afume_android.util.TabSelectedListener
import com.afume.afume_android.util.startActivityWithFinish

class SurveyActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySurveyBinding
    private val viewModel: SurveyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        overridePendingTransition(R.anim.slide_down, R.anim.slide_up)

        initTabWithVp()
        binding.toolbarSurvey.toolbarBtn.setOnClickListener {
            finish()
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
        val surveyViewPagerAdapter = AfumeViewPagerAdapter(supportFragmentManager)
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

    }

    private fun clickBtnComplete() {
        binding.btnSurveyApply.setOnClickListener {
            binding.vpSurvey.apply {
                when (currentItem) {
                    0 -> currentItem = 1
                    1 -> currentItem = 2
                    2 -> {
                        viewModel.postSurvey(AfumeApplication.prefManager.accessToken)
                        this@SurveyActivity.startActivityWithFinish(MainActivity::class.java)
                    }
                }
            }
        }
    }

}