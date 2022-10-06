package com.scentsnote.android.ui.my

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.scentsnote.android.R
import com.scentsnote.android.databinding.ActivityMyInquiryBinding
import com.scentsnote.android.databinding.ActivityPerfumeDetailBinding
import com.scentsnote.android.util.BaseActivity

class MyInquiryActivity : BaseActivity<ActivityMyInquiryBinding>(R.layout.activity_my_inquiry) {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val url = getUrl()

        binding.wvMyInquiry.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(url)
        }
    }

    override fun onBackPressed() {
        if (binding.wvMyInquiry.canGoBack())
        {
            binding.wvMyInquiry.goBack()
        }
        else
        {
            finish()
        }
    }

    fun getUrl() : String{
        val url = intent.getStringExtra("url")
        return if(url == "feedback"){
            "https://docs.google.com/forms/d/e/1FAIpQLSfnvvc2O3_1X59lL243vsVXAjy-PIcq6-cgDgrhPph9mCAI1g/viewform"
        }else{
            "https://docs.google.com/forms/d/e/1FAIpQLSeZL-aslJd_YDgX2kLx31Gra1CXjG6ivaHqyAlko_iDQEVzYg/viewform"
        }
    }
}