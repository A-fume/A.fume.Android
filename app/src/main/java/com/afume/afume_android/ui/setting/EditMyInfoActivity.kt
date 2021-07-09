package com.afume.afume_android.ui.setting

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivityMyInfoEditBinding
import com.afume.afume_android.util.YearPickerDialog

class EditMyInfoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMyInfoEditBinding
    private val editViewModel : EditMyInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_info_edit)
        binding.lifecycleOwner = this
        binding.viewModel = editViewModel

        editViewModel.checkMyInfo()
    }

    fun showYearPicker(view : View){
        val dialog: DialogFragment = YearPickerDialog(binding.btnMyInfoEditYearPicker)
        dialog.show(this.supportFragmentManager, dialog.tag)
        editViewModel.checkChangeInfo()
    }

    fun onClickCompleteBtn(view: View){
        editViewModel.putMyInfo()

        finish()
    }

    fun onClickBackBtn(view : View){
        finish()
    }
}