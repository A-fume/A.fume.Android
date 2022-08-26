package com.scents.note.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.scents.note.R
import com.scents.note.databinding.ActivityButtonBinding
import com.scents.note.ui.detail.PerfumeDetailActivity
import com.scents.note.ui.note.NoteActivity
import com.scents.note.ui.signin.SignHomeActivity

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