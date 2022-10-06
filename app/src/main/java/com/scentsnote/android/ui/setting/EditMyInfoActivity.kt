package com.scentsnote.android.ui.setting

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.scentsnote.android.R
import com.scentsnote.android.databinding.ActivityMyInfoEditBinding
import com.scentsnote.android.databinding.ActivitySearchTextBinding
import com.scentsnote.android.util.BaseActivity
import com.scentsnote.android.util.CommonDialog
import com.scentsnote.android.util.YearPickerDialog
import com.scentsnote.android.util.toast

class EditMyInfoActivity : BaseActivity<ActivityMyInfoEditBinding>(R.layout.activity_my_info_edit) {
    private val editViewModel : EditMyInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            viewModel = editViewModel
        }

        editViewModel.checkMyInfo()
        setCompleteBtn()
        setUpdateMyInfoToastObserve("내 정보가 수정되었습니다.", "내 정보 수정 실패")
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
    }

    private fun setUpdateMyInfoToastObserve(success: String, fail: String){
        editViewModel.showMyInfoUpdateToast.observe(this, Observer{
            if(editViewModel.isValidMyInfoUpdate.value!!){
                this.toast(success)
            }else{
                this.toast(fail)
            }
        })
    }
}