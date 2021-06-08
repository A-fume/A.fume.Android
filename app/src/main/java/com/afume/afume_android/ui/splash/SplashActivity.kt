package com.afume.afume_android.ui.splash

import android.animation.Animator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivitySplashBinding
import com.afume.afume_android.ui.signin.SignHomeActivity
import com.afume.afume_android.util.startActivityWithFinish

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding :ActivitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.lifecycleOwner=this

        startAnimation(binding)
        setLottieListener(binding)
    }

    private fun startAnimation(binding: ActivitySplashBinding){
        binding.lottieSplash.playAnimation()
    }

    private fun setLottieListener(binding: ActivitySplashBinding){
        binding.lottieSplash.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                startActivityWithFinish(SignHomeActivity::class.java)
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
            }
        })
    }
}