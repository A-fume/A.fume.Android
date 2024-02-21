package com.scentsnote.android.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.scentsnote.android.R
import com.scentsnote.android.ScentsNoteApplication.Companion.firebaseAnalytics
import com.scentsnote.android.databinding.ActivityMoreNewPerfumeBinding
import com.scentsnote.android.ui.home.adapter.MoreNewListAdapter
import com.scentsnote.android.viewmodel.home.HomeViewModel
import com.scentsnote.android.utils.base.BaseWebViewActivity
import com.scentsnote.android.utils.extension.setHeartBtnClickEvent
import com.scentsnote.android.utils.extension.setPageViewEvent
import kotlinx.coroutines.launch

/**
 * 홈 화면 - 새로운 향수 더보기
 *
 * 수요일 마다 업데이트 되는 새로운 향수 리스트 제공
 */
class MoreNewPerfumeActivity : AppCompatActivity() {
    lateinit var binding: ActivityMoreNewPerfumeBinding
    lateinit var newAdapter: MoreNewListAdapter
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_more_new_perfume)
        binding.lifecycleOwner = this
        binding.viewModel = homeViewModel

        lifecycleScope.launch {
            homeViewModel.getNewPerfumeList(null)
        }

        initNewList()
    }

    override fun onResume() {
        super.onResume()

        firebaseAnalytics.setPageViewEvent("NewRegister", this::class.java.name)
    }

    private fun initNewList() {
        newAdapter = MoreNewListAdapter(this, supportFragmentManager) { idx ->
            homeViewModel.postPerfumeLike(
                2,
                idx
            )
        }

        newAdapter.setOnItemClickListener(object  : MoreNewListAdapter.OnItemClickListener{
            override fun firebaseClickEvent(like: Boolean) {
                firebaseAnalytics.setHeartBtnClickEvent("home_new_more", like)
            }
        })

        binding.rvHomeMoreNew.adapter = newAdapter
    }

    fun onClickBackBtn(view: View) {
        finish()
    }

    fun onClickWithdrawalBtn(view: View) {
        val intent = Intent(this, BaseWebViewActivity::class.java)
        intent.putExtra("type", "tipOff")
        startActivity(intent)
    }
}