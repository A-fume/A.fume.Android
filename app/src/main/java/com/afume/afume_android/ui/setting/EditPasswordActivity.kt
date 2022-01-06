package com.afume.afume_android.ui.setting

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivityPasswordEditBinding
import com.afume.afume_android.util.CommonDialog
import com.afume.afume_android.util.setKeyboard
import com.afume.afume_android.util.toast

class EditPasswordActivity : AppCompatActivity() {
    lateinit var binding : ActivityPasswordEditBinding
    private val editViewModel : EditMyInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_password_edit)
        binding.lifecycleOwner = this
        binding.viewModel = editViewModel

        binding.edtEditPasswordCheck.requestFocus()

        observer()
        passwordAnimation()
        checkNextBtn()
        setKeyboard()
    }

    private fun observer(){
        editViewModel.isValidSamePassword.observe(this, Observer {
            if(it){
                binding.edtEditPasswordNew.requestFocus()
                this.setKeyboard(true, binding.edtEditPasswordNew)
            }
        })
    }

    private fun passwordAnimation(){
        editViewModel.newPasswordForm.observe(this, Observer { isValidPassword ->
            isValidPassword?.let {
                if(isValidPassword){
                    val animation = AnimationUtils.loadAnimation(this, R.anim.alpha_up)
                    binding.clPasswordEditNew.startAnimation(animation)

                    binding.clPasswordEditNew.requestFocus()
                }
            }
        })
    }

    private fun checkNextBtn(){
        editViewModel.isValidNewPassword.observe(this, Observer{
            editViewModel.checkPasswordNextBtn()
        })
        editViewModel.isValidAgainPassword.observe(this, Observer{
            editViewModel.checkPasswordNextBtn()
        })
    }

    private fun setKeyboard(){
        editViewModel.passwordEditCompleteBtn.observe(this, Observer { passwordEditCompleteBtn ->
            if(passwordEditCompleteBtn){
                this.setKeyboard(false,null)
            }
        })
    }

    fun onClickCompleteBtn(view: View){
        editViewModel.putPassword()

        editViewModel.isValidEditPassword.observe(this, Observer{ isValidEditPassword ->
            isValidEditPassword?.let{
                if(isValidEditPassword){
                    finish()
                }
            }
        })
    }

    override fun onBackPressed() {
        backBtn()
    }

    fun onClickBackBtn(view : View){
        backBtn()
    }

    private fun backBtn(){
        editViewModel.checkUpdatePassword()

        editViewModel.showUpdateDialog.observe(this, Observer {
            if(it){
                val bundle = Bundle()
                bundle.putString("title","save")
                val dialog: CommonDialog = CommonDialog().CustomDialogBuilder()
                    .setBtnClickListener(object : CommonDialog.CustomDialogListener {
                        override fun onPositiveClicked() {
                            editViewModel.putPassword()
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

        setUpdatePasswordToastObserve(editViewModel.isValidMyInfoUpdate, "비밀번호가 변경되었습니다.", "비밀번호 변경 실패")
    }

    private fun setUpdatePasswordToastObserve(settingNetworkState: LiveData<Boolean>, success: String, fail: String){
        settingNetworkState.observe(this, Observer {
            if(it){
                this.toast(success)
            }else{
                this.toast(fail)
            }
        })
    }
}