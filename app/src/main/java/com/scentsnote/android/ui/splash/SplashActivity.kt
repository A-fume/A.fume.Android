package com.scentsnote.android.ui.splash

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.scentsnote.android.ScentsNoteApplication
import com.scentsnote.android.R
import com.scentsnote.android.ScentsNoteApplication.Companion.firebaseAnalytics
import com.scentsnote.android.databinding.ActivitySplashBinding
import com.scentsnote.android.ui.MainActivity
import com.scentsnote.android.ui.survey.SurveyActivity
import com.scentsnote.android.utils.extension.setPageViewEvent
import com.scentsnote.android.viewmodel.splash.SplashViewModel
import com.scentsnote.android.utils.extension.startActivityWithFinish
import com.scentsnote.android.utils.extension.toast
import com.scentsnote.android.utils.view.AppUpdateDialog

class SplashActivity : AppCompatActivity() {
    private val splashViewModel: SplashViewModel by viewModels()
    private val time: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySplashBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_splash
        )
        binding.lifecycleOwner = this

        initObserver()
    }

    override fun onResume() {
        super.onResume()

        firebaseAnalytics.setPageViewEvent("Loading",this::class.java.name)
    }

    private fun initObserver() {
        splashViewModel.isValidVersion.observe(this) {
            if (it) goToNextActivity()
            else createDialog()
        }
    }

    private fun goToNextActivity() {
        if (ScentsNoteApplication.prefManager.haveToken() && ScentsNoteApplication.prefManager.userSurvey) {
            startActivityWithFinish(SurveyActivity::class.java)
        } else if (ScentsNoteApplication.prefManager.haveToken() && !ScentsNoteApplication.prefManager.userSurvey) {
            startActivityWithFinish(MainActivity::class.java)
            this@SplashActivity.toast("자동 로그인되었습니다.")
        } else {
            startActivityWithFinish(MainActivity::class.java)
        }
    }

    private fun createDialog() {
        val bundle = Bundle()
        val dialog: DialogFragment = AppUpdateDialog().AppUpdateDialogBuilder()
            .setBtnClickListener(object : AppUpdateDialog.AppUpdateDialogListener {
                override fun onPositiveClicked() {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=com.scentsnote.android")
                    )
                    finish()
                    startActivity(intent)
                }

                override fun onNegativeClicked() {
                    finish()
                }
            })
            .getInstance()
        dialog.arguments = bundle
        dialog.show(supportFragmentManager, dialog.tag)
    }
}