package com.scentsnote.android.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import com.scentsnote.android.ScentsNoteApplication
import com.scentsnote.android.R
import com.scentsnote.android.ScentsNoteApplication.Companion.firebaseAnalytics
import com.scentsnote.android.databinding.ActivitySplashBinding
import com.scentsnote.android.ui.MainActivity
import com.scentsnote.android.ui.survey.SurveyActivity
import com.scentsnote.android.utils.base.BaseActivity
import com.scentsnote.android.utils.extension.setPageViewEvent
import com.scentsnote.android.viewmodel.splash.SplashViewModel
import com.scentsnote.android.utils.extension.startActivityWithFinish
import com.scentsnote.android.utils.extension.toast
import com.scentsnote.android.utils.view.AppUpdateDialog

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onResume() {
        super.onResume()

        firebaseAnalytics.setPageViewEvent("Loading",this::class.java.name)
    }

    override fun initObserver() {
        splashViewModel.isValidVersion.observe(this) {
            when(it){
                "pass" -> goToNextActivity()
                "update" -> createDialog()
                "error" -> showNetworkErrorDialog()
            }
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
        bundle.putString("title", "update")
        val dialog = AppUpdateDialog().AppUpdateDialogBuilder()
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

    private fun showNetworkErrorDialog() {
        val bundle = Bundle()
        bundle.putString("title", "error")
        val dialog = AppUpdateDialog().AppUpdateDialogBuilder()
            .setBtnClickListener(object : AppUpdateDialog.AppUpdateDialogListener {
                override fun onPositiveClicked() {
                    finish()
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