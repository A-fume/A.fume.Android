package com.afume.afume_android.ui.search

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivitySearchTextBinding
import com.afume.afume_android.ui.MainActivity

class SearchTextActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySearchTextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        clickSearch()
        clickBack()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_text)
        binding.lifecycleOwner = this
    }

    private fun clickSearch(){
        binding.btnSearch.setOnClickListener {
            val intent= Intent(this, MainActivity::class.java)
            intent.putExtra("flag",3)
            intent.putExtra("searchText",binding.edtSearch.text.toString())
            startActivity(intent)
        }
    }
    private fun clickBack(){
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

}