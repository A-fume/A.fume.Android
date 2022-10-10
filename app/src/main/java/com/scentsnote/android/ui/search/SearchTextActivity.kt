package com.scentsnote.android.ui.search

import android.content.Intent
import android.os.Bundle
import com.scentsnote.android.R
import com.scentsnote.android.databinding.ActivitySearchTextBinding
import com.scentsnote.android.ui.MainActivity
import com.scentsnote.android.util.view.BaseActivity

class SearchTextActivity : BaseActivity<ActivitySearchTextBinding>(R.layout.activity_search_text)  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clickSearch()
        clickBack()
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