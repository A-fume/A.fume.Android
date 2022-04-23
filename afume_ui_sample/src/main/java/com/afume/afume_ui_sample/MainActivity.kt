package com.afume.afume_ui_sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.afume.afume_ui_sample.selectbox.SelectBoxActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // TODO : 동적으로 생성하는 리스트로 변경하기
        startActivity(Intent(this, SelectBoxActivity::class.java))
    }
}