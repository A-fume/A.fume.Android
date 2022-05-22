package com.afume.afume_android.ui.survey

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.afume.afume_android.AfumeApplication
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivitySurveyBinding
import com.afume.afume_android.ui.MainActivity
import com.afume.afume_android.ui.filter.AfumeViewPagerAdapter
import com.afume.afume_android.util.TabSelectedListener
import com.afume.afume_android.util.changeTabsFont
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
            // TODO Remove this code. it can be replace with xml declaration
            getTabAt(0)?.text = resources.getString(R.string.perfume)
            getTabAt(1)?.text = resources.getString(R.string.keyword)
            getTabAt(2)?.text = resources.getString(R.string.series)
        }
        val tabListener = TabSelectedListener(binding.tabSurvey)
        binding.tabSurvey.addOnTabSelectedListener(tabListener)
        binding.tabSurvey.changeTabsFont(0)
    }

    private fun clickBtnComplete(){
        binding.btnSurveyApply.setOnClickListener {
            Log.e("버튼 눌리나","버튼 눌린다")
            binding.vpSurvey.apply {
                when (currentItem) {
                    0 -> currentItem = 1
                    1 -> currentItem = 2
                    2 -> {
                        AfumeApplication.prefManager.userSurvey = false
                        viewModel.postSurvey(AfumeApplication.prefManager.accessToken)
                        this@SurveyActivity.startActivityWithFinish(MainActivity::class.java)
                    }
                }
            }
        }
    }
}