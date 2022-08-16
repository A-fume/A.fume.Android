package com.afume.afume_android.ui.splash

import android.animation.Animator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.afume.afume_android.AfumeApplication
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivitySplashBinding
import com.afume.afume_android.ui.MainActivity
import com.afume.afume_android.ui.survey.SurveyActivity
import com.afume.afume_android.util.AppUpdateDialog
import com.afume.afume_android.util.startActivityWithFinish
import com.afume.afume_android.util.toast


class SplashActivity : AppCompatActivity() {
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding :ActivitySplashBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_splash
        )
        binding.lifecycleOwner=this

        startAnimation(binding)
        setLottieListener(binding)
    }

    private fun startAnimation(binding: ActivitySplashBinding){
        binding.lottieSplash.playAnimation()
    }

    private fun setLottieListener(binding: ActivitySplashBinding){
        binding.lottieSplash.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                if (splashViewModel.isValidVersion.value == true) {
                    goToNextActivity()
                } else {
                    createDialog()
                }
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
            }
        })
    }

    private fun goToNextActivity(){
        if(AfumeApplication.prefManager.haveToken() && AfumeApplication.prefManager.userSurvey){
            startActivityWithFinish(SurveyActivity::class.java)
        }else if(AfumeApplication.prefManager.haveToken() && !AfumeApplication.prefManager.userSurvey){
            startActivityWithFinish(MainActivity::class.java)
            this@SplashActivity.toast("자동 로그인되었습니다.")
        }else{
            startActivityWithFinish(MainActivity::class.java)
        }
    }

    private fun createDialog(){
        val bundle = Bundle()
        val dialog: DialogFragment = AppUpdateDialog().AppUpdateDialogBuilder()
            .setBtnClickListener(object : AppUpdateDialog.AppUpdateDialogListener {
                override fun onPositiveClicked() {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.afume.afume_android"))
                    startActivity(intent)
                }

                override fun onNegativeClicked() {
                    finish()
                }
            })
            .getInstance()
        dialog.isCancelable = false
        dialog.arguments = bundle
        dialog.show(supportFragmentManager, dialog.tag)
    }
}