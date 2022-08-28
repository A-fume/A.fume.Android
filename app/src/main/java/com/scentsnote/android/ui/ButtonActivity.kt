package com.scentsnote.android.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.scentsnote.android.R
import com.scentsnote.android.databinding.ActivityButtonBinding
import com.scentsnote.android.ui.detail.PerfumeDetailActivity
import com.scentsnote.android.ui.note.NoteActivity
import com.scentsnote.android.ui.signin.SignHomeActivity

class ButtonActivity : AppCompatActivity() {
    lateinit var binding: ActivityButtonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_button)
        binding.lifecycleOwner=this

        binding.actButtonTvSignIn.setOnClickListener {
            startActivity(Intent(this, SignHomeActivity::class.java))
        }

        binding.actButtonTvMain.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.actButtonTvDetail.setOnClickListener {
            startActivity(Intent(this, PerfumeDetailActivity::class.java))
        }

        binding.textView4.setOnClickListener {
            val intent = Intent(this, NoteActivity::class.java)
            intent.putExtra("reviewIdx", 1)
            startActivity(intent)
        }
    }
}