package com.scents.note.ui.setting

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.scents.note.R
import com.scents.note.databinding.ActivityMyInfoEditBinding
import com.scents.note.util.CommonDialog
import com.scents.note.util.YearPickerDialog
import com.scents.note.util.toast

class EditMyInfoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMyInfoEditBinding
    private val editViewModel : EditMyInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_info_edit)
        binding.lifecycleOwner = this
        binding.viewModel = editViewModel

        editViewModel.checkMyInfo()
        setCompleteBtn()
    }

    private fun setCompleteBtn(){
        setMyInfoObserve(editViewModel.isValidNickBtn)
        setMyInfoObserve(editViewModel.isValidNick)
        setMyInfoObserve(editViewModel.isCheckMan)
        setMyInfoObserve(editViewModel.isCheckWoman)
        editViewModel.ageTxt.observe(this, Observer {
            editViewModel.checkChangeInfo()
        })
    }

    private fun setMyInfoObserve(editInfo: LiveData<Boolean>){
        editInfo.observe(this, Observer {
            editViewModel.checkChangeInfo()
        })
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

    override fun onBackPressed() {
        backBtn()
    }

    fun onClickBackBtn(view : View){
        backBtn()
    }

    private fun backBtn(){
        editViewModel.checkUpdateInfo()

        editViewModel.showUpdateDialog.observe(this, Observer {
            if(it){
                val bundle = Bundle()
                bundle.putString("title","save")
                val dialog: CommonDialog = CommonDialog().CustomDialogBuilder()
                    .setBtnClickListener(object : CommonDialog.CustomDialogListener {
                        override fun onPositiveClicked() {
                            editViewModel.putMyInfo()
                            finish()
                        }
                        override fun onNegativeClicked() {
                            finish()
                        }
                    })
                    .getInstance()
                dialog.arguments = bundle
                dialog.show(this.supportFragmentManager, dialog.tag)
            }
            else{
                finish()
            }
        })

        setUpdateMyInfoToastObserve(editViewModel.isValidMyInfoUpdate, "내 정보가 수정되었습니다.", "내 정보 수정 실패")
    }

    private fun setUpdateMyInfoToastObserve(settingNetworkState: LiveData<Boolean>, success: String, fail: String){
        settingNetworkState.observe(this, Observer {
            if(it){
                this.toast(success)
            }else{
                this.toast(fail)
            }
        })
    }
}