package com.scentsnote.android.util.view

import android.content.Context
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.scentsnote.android.R
import com.scentsnote.android.ui.note.NoteViewModel
import com.scentsnote.android.util.setSelectedSeekBarTxt

fun setSelectedSeekBarTxtBold(list: List<TextView>, select: Int){
    for(i in list.indices){
        if(i==select){
            list[i].setSelectedSeekBarTxt(true)
        }else{
            list[i].setSelectedSeekBarTxt(false)
        }
    }
}

private fun setSeekBarProgress(vm : NoteViewModel, type: String, progress: Int){
    when (type){
        "longevity" -> {
            vm.longevityProgress.value = progress
        }
        "reverb" -> {
            vm.reverbProgress.value = progress
        }
        "gender" -> {
            vm.genderProgress.value = progress
        }
    }
}

class SeekBarListener(val context: Context, val seekBar: SeekBar, val list: List<TextView>, val vm : NoteViewModel, val type: String) : SeekBar.OnSeekBarChangeListener{
    override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
        setSelectedSeekBarTxtBold(list,progress)
        setSeekBarProgress(vm,type,progress)

    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
        seekBar.thumb = ContextCompat.getDrawable(context, R.drawable.seekbar_note_thumb)
        if(seekBar.progress==0){
            setSelectedSeekBarTxtBold(list,0)
        }
        setSeekBarProgress(vm,type,0)
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
    }

}