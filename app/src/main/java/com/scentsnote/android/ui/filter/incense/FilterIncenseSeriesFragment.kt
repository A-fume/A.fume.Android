package com.scentsnote.android.ui.filter.incense

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.scentsnote.android.ScentsNoteApplication
import com.scentsnote.android.databinding.FragmentFilterIncenseSeriesBinding
import com.scentsnote.android.viewmodel.filter.FilterSeriesViewModel

/**
 * 향수 검색 - 필터 - 계열 탭
 *
 * 상위 항목과 하위 계열 리스트 제공
 */
class FilterIncenseSeriesFragment : Fragment() {
    private lateinit var binding: FragmentFilterIncenseSeriesBinding
    private val viewModel: FilterSeriesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return initBinding(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initIncenseSeriesRv()
    }

    override fun onResume() {
        super.onResume()

        ScentsNoteApplication.firebaseAnalytics.logEvent("page_view"){
            param(FirebaseAnalytics.Param.SCREEN_NAME, "FilterProductLine")
            param(FirebaseAnalytics.Param.SCREEN_CLASS, "FilterIncenseSeriesFragment")
        }
    }

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentFilterIncenseSeriesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    private fun initIncenseSeriesRv() {
        val seriesAdapter = SeriesViewAdapter(viewModel)
        binding.rvIncenseSeries.adapter = seriesAdapter
        viewModel.dataFetched.observe(viewLifecycleOwner) {
            seriesAdapter.submitList(viewModel.getFilterSeriesViewData())
        }
    }
}