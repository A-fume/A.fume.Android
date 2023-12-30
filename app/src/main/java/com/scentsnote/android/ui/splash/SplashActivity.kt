package com.scentsnote.android.ui.splash

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
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
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val splashViewModel: SplashViewModel by viewModels()
    private val time: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(checkDeviceOfRoot(this)){
            // dialog extension
            showNetworkErrorDialog("rooting")
        }else{
            // 앱 지원 여부 체크
            lifecycleScope.launch {
                splashViewModel.getVersion()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        firebaseAnalytics.setPageViewEvent("Loading",this::class.java.name)
    }

    private fun checkDeviceOfRoot(context: Context): Boolean {
        var isRooting = false
        val rootingPath =
            context.resources.getStringArray(R.array.rooting_file_list)
        for (i in rootingPath.indices) {
            if (File(rootingPath[i]).exists()) isRooting = true
        }
        isRooting = try {
            Runtime.getRuntime().exec(context.resources.getString(R.string.str_pass))
            true
        } catch (e: IOException) {
            false
        }
        return isRooting
    }

    override fun initObserver() {
        splashViewModel.isValidVersion.observe(this) {
            when(it){
                "pass" -> goToNextActivity()
                "update" -> createDialog()
                "error" -> showNetworkErrorDialog("error")
            }
        }
    }

    private fun goToNextActivity() {
        if (ScentsNoteApplication.prefManager.haveToken()) {
            if(ScentsNoteApplication.prefManager.userSurvey) {
                startActivityWithFinish(SurveyActivity::class.java)
            } else {
                startActivityWithFinish(MainActivity::class.java)
                this@SplashActivity.toast("자동 로그인되었습니다.")
            }
        } else {
            startActivityWithFinish(MainActivity::class.java)
        }
    }

    private fun createDialog() {
        val bundle = Bundle()
        bundle.putString("title", "update")
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

    private fun showNetworkErrorDialog(type: String) {
        val bundle = Bundle()
        bundle.putString("title", type)
        val dialog: DialogFragment = AppUpdateDialog().AppUpdateDialogBuilder()
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