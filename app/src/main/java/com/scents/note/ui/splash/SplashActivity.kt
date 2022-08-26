package com.scents.note.ui.splash

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.scents.note.ScentsNoteApplication
import com.scents.note.R
import com.scents.note.databinding.ActivitySplashBinding
import com.scents.note.ui.MainActivity
import com.scents.note.ui.survey.SurveyActivity
import com.scents.note.util.AppUpdateDialog
import com.scents.note.util.startActivityWithFinish
import com.scents.note.util.toast


class SplashActivity : AppCompatActivity() {
    private val splashViewModel: SplashViewModel by viewModels()
    private val time : Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : ActivitySplashBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_splash
        )
        binding.lifecycleOwner=this

        setDelayed()
//        startAnimation(binding)
//        setLottieListener(binding)
    }

    private fun setDelayed(){
        val handler = Handler()
        handler.postDelayed({
            goToNextActivity()
//            if (splashViewModel.isValidVersion.value == true) {
//                goToNextActivity()
//            } else {
//                createDialog()
//            }
        }, 2000)

//        CoroutineScope(Dispatchers.IO).launch {
//            delay(time)
//            if (splashViewModel.isValidVersion.value == true) {
//                goToNextActivity()
//            } else {
//                createDialog()
//            }
//        }
    }

//    private fun startAnimation(binding: ActivitySplashBinding){
//        binding.lottieSplash.playAnimation()
//    }
//
//    private fun setLottieListener(binding: ActivitySplashBinding){
//        binding.lottieSplash.addAnimatorListener(object : Animator.AnimatorListener {
//            override fun onAnimationRepeat(p0: Animator?) {
//            }
//
//            override fun onAnimationEnd(p0: Animator?) {
//                if (splashViewModel.isValidVersion.value == true) {
//                    goToNextActivity()
//                } else {
//                    createDialog()
//                }
//            }
//
//            override fun onAnimationCancel(p0: Animator?) {
//            }
//
//            override fun onAnimationStart(p0: Animator?) {
//            }
//        })
//    }

    private fun goToNextActivity(){
        if(ScentsNoteApplication.prefManager.haveToken() && ScentsNoteApplication.prefManager.userSurvey){
            startActivityWithFinish(SurveyActivity::class.java)
        }else if(ScentsNoteApplication.prefManager.haveToken() && !ScentsNoteApplication.prefManager.userSurvey){
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
        dialog.arguments = bundle
        dialog.show(supportFragmentManager, dialog.tag)
    }
}