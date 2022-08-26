package com.scents.note.util

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CheckedTextView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.scents.note.ScentsNoteApplication
import com.scents.note.R
import com.bumptech.glide.Glide

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("setPerfumeLike")
    fun ImageView.setPerfumeLike(status: Boolean) {
        if(status) setImageResource(R.drawable.favorite_active)
        else setImageResource(R.drawable.favorite_inactive)
    }

    @JvmStatic
    @BindingAdapter("setLikeList")
    fun ImageView.setLikeList(status: Int) {
        if (status == 0) {
            setImageResource(R.drawable.favorite_inactive)
        } else {
            setImageResource(R.drawable.favorite_active)
        }
    }

    @JvmStatic
    @BindingAdapter("setLikeList")
    fun ImageView.setLikeList(status: Boolean) {
        if (!status) {
            setImageResource(R.drawable.favorite_inactive)
        } else {
            setImageResource(R.drawable.favorite_active)
        }
    }

    @JvmStatic
    @BindingAdapter("setKeywordList")
    fun TextView.setKeywordList(status: Boolean) {
        if (!status) {
            background = ContextCompat.getDrawable(this.context, R.drawable.border_gray_cd_line_square)
            setTextColor(ContextCompat.getColor(this.context, R.color.gray_cd))
        } else {
            setBackgroundColor(ContextCompat.getColor(this.context, R.color.point_beige))
            setTextColor(ContextCompat.getColor(this.context, R.color.white))
        }
    }

    @JvmStatic
    @BindingAdapter("setImage")
    fun setImage(view: ImageView, res: Int?) {
        Glide.with(view.context)
            .load(res)
            .into(view)

    }

    @JvmStatic
    @BindingAdapter("setImage")
    fun setImage(view: ImageView, res: String?) {
        Glide.with(view.context)
            .load(res)
            .into(view)

    }

    @JvmStatic
    @BindingAdapter("setManIcon")
    fun ImageView.setManIcon(status: Boolean) {
        if (status) {
            setImageResource(R.drawable.btn_man_active)
        } else {
            setImageResource(R.drawable.btn_man_inactive)
        }
    }

    @JvmStatic
    @BindingAdapter("setWomanIcon")
    fun ImageView.setWomanIcon(status: Boolean) {
        if (status) {
            setImageResource(R.drawable.btn_woman_active)
        } else {
            setImageResource(R.drawable.btn_woman_inactive)
        }
    }

    @JvmStatic
    @BindingAdapter("setGenderFont")
    fun TextView.setGenderFont(status: Boolean){
        typeface = ResourcesCompat.getFont(this.context, if(status) R.font.nanummyeongjo_extrabold else R.font.nanummyeongjo)
        setTextColor(ResourcesCompat.getColor(this.resources, if(status) R.color.primary_black else R.color.gray_cd, null))
    }

    @JvmStatic
    @BindingAdapter("setWarningFont")
    fun EditText.setWarningFont(status: Boolean){
        setTextColor(ResourcesCompat.getColor(this.resources, if(status) R.color.brick else R.color.primary_black, null))
    }

    @JvmStatic
    @BindingAdapter("setNoteContentsText")
    fun setNoteContentsText(view: EditText, text: String) {
        when (text.length) {
            0 -> {
                view.setBackgroundResource(R.drawable.border_gray_cd_line)
            }
            else -> {
                view.setBackgroundResource(R.drawable.border_gray_cd_line_square)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("TextAttrChanged")
    fun setTextAttrChanged(view: EditText, listener: InverseBindingListener) {
        view.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                listener.onChange()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "setNoteContentsText", event = "TextAttrChanged")
    fun getNoteContentsSizeText(view: EditText): String {
        return view.text.toString()
    }

    @JvmStatic
    @BindingAdapter("setNoteShareBtnBackground")
    fun CheckedTextView.setNoteShareBtnBackground(status: Boolean) {
        if(status){
            setBackgroundColor(resources.getColor(R.color.point_beige))
        }else{
            setBackgroundColor(resources.getColor(R.color.light_gray_f0))
        }
    }

    @JvmStatic
    @BindingAdapter("setNoteSeasonBtnBackground")
    fun CheckedTextView.setNoteSeasonBtnBackground(status: Boolean) {
        if(!status){
            this.typeface = ResourcesCompat.getFont(this.context, R.font.notosans_regular)
            this.setTextColor(ContextCompat.getColor(this.context, R.color.dark_gray_7d))
            background = ContextCompat.getDrawable(this.context, R.drawable.border_gray_cd_line)
        }else{
            this.typeface = ResourcesCompat.getFont(this.context, R.font.notosans_bold)
            this.setTextColor(ContextCompat.getColor(this.context, R.color.white))
            setBackgroundColor(resources.getColor(R.color.point_beige))
        }
    }

    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter("noteUserAge","noteUserGender")
    fun TextView.setNoteUserInfoText(age: String, gender: Int) {
        when (gender) {
            1 -> {
                this.text = "$age / 남자"
            }
            2 -> {
                this.text = "$age / 여자"
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setNoteBtnText")
    fun TextView.setNoteBtnText(review: Int){
        when(review){
            0 -> {
                this.text = "시향 노트 쓰기"
            }
            else -> {
                this.text = "시향 노트 수정"
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setNoteReportVisible")
    fun ImageView.setNoteReportVisible(nickname: String){
        if(nickname == ScentsNoteApplication.prefManager.userNickname){
            this.visibility = View.INVISIBLE
        }else{
            this.visibility = View.VISIBLE
        }
    }

    @JvmStatic
    @BindingAdapter("setNoteReportVisible")
    fun TextView.setNoteReportVisible(nickname: String){
        if(nickname == ScentsNoteApplication.prefManager.userNickname){
            this.visibility = View.GONE
        }else{
            this.visibility = View.VISIBLE
        }
    }
}