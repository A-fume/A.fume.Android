package com.afume.afume_android.ui.setting

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivityMyInfoEditBinding

class MyInfoEditActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMyInfoEditBinding
    private val editViewModel : MyInfoEditViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_info_edit)
        binding.lifecycleOwner = this
        binding.viewModel = editViewModel

        editViewModel.checkMyInfo()
    }

    fun onClickBackBtn(view : View){
        finish()
    }
}