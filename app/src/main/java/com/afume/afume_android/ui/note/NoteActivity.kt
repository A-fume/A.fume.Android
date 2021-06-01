package com.afume.afume_android.ui.note

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckedTextView
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivityNoteBinding
import com.afume.afume_android.ui.detail.PerfumeDetailActivity
import com.afume.afume_android.util.setSelectedSeasonBtn
import com.afume.afume_android.util.setSelectedSeekBarTxt
import com.afume.afume_android.util.startActivity

class NoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityNoteBinding
    private val noteViewModel : NoteViewModel by viewModels()
    private val keywordBottomSheetFragment = KeywordBottomSheetFragment()

    lateinit var txtLongevityList : List<TextView>
    lateinit var txtReverbList : List<TextView>
    lateinit var txtGenderList : List<TextView>
    lateinit var btnSeasonList : List<CheckedTextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_note)
        binding.lifecycleOwner = this
        binding.viewModel = noteViewModel

        setComponentList()
        onSeekBarChangeListener()
        onClickSeasonBtn()
    }

    private fun setComponentList(){
        txtLongevityList = listOf(binding.txtNoteLongevityVeryWeak, binding.txtNoteLongevityWeak, binding.txtNoteLongevityUsual, binding.txtNoteLongevityStrong, binding.txtNoteLongevityVeryStrong)
        txtReverbList = listOf(binding.txtNoteReverbLight, binding.txtNoteReverbUsual, binding.txtNoteReverbHeavy)
        txtGenderList = listOf(binding.txtNoteGenderMan, binding.txtNoteGenderNeuter, binding.txtNoteGenderWoman)

        btnSeasonList = listOf(binding.btnNoteSeasonSpring, binding.btnNoteSeasonSummer, binding.btnNoteSeasonFall, binding.btnNoteSeasonWinder)
    }

    private fun onSeekBarChangeListener(){
        binding.sbNoteLongevity.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                setSelectedSeekBarTxtBold(txtLongevityList,progress)
                Log.d("명",progress.toString())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                binding.sbNoteLongevity.thumb = ContextCompat.getDrawable(this@NoteActivity, R.drawable.seekbar_note_thumb)
                if(binding.sbNoteLongevity.progress==0){
                    setSelectedSeekBarTxtBold(txtLongevityList,0)
                }
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        binding.sbNoteReverb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                setSelectedSeekBarTxtBold(txtReverbList,progress)
                Log.d("명",progress.toString())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                binding.sbNoteReverb.thumb = ContextCompat.getDrawable(this@NoteActivity, R.drawable.seekbar_note_thumb)
                if(binding.sbNoteReverb.progress==0){
                    setSelectedSeekBarTxtBold(txtReverbList,0)
                }
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        binding.sbNoteGender.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                setSelectedSeekBarTxtBold(txtGenderList,progress)
                Log.d("명",progress.toString())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                binding.sbNoteGender.thumb = ContextCompat.getDrawable(this@NoteActivity, R.drawable.seekbar_note_thumb)
                if(binding.sbNoteGender.progress==0){
                    setSelectedSeekBarTxtBold(txtGenderList,0)
                }
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
    }

    private fun setSelectedSeekBarTxtBold(list: List<TextView>, select: Int){
        for(i in list.indices){
           if(i==select){
               list[i].setSelectedSeekBarTxt(true)
           }else{
               list[i].setSelectedSeekBarTxt(false)
           }
        }
    }

    private fun onClickSeasonBtn(){
        btnSeasonList.forEachIndexed { index, checkedTextView ->
            btnSeasonList[index].setOnClickListener {
                if(btnSeasonList[index].isChecked) {
                    checkSeasonBtn(btnSeasonList[index], false)
                }else{
                    checkSeasonBtn(btnSeasonList[index], true)
            }
        }}
    }

    private fun checkSeasonBtn(button: CheckedTextView, isChecked: Boolean) {
        button.setSelectedSeasonBtn(isChecked)
        if(isChecked){
            button.isChecked = true
            //button.checkMarkDrawable = ContextCompat.getDrawable(this, R.drawable.ic_check_white)
        }else{
            button.isChecked = false
            //button.checkMarkDrawable = ContextCompat.getDrawable(this, R.drawable.ic_check_grey)
        }
    }

    fun onClickBackBtn(view : View){
        finish()
    }

    fun onClickDetailBtn(view : View){
        this.startActivity(PerfumeDetailActivity::class.java)
    }

    fun onClickKeywordBtn(view : View){
        keywordBottomSheetFragment.show(supportFragmentManager, "noteKeywordDialog")
    }

    fun onClickCompleteBtn(view : View){
        finish()
    }
}