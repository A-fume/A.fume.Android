package com.afume.afume_android.ui.survey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.response.SeriesInfo
import com.afume.afume_android.databinding.FragmentSurveyIncenseBinding

class SurveyIncenseFragment : Fragment() {
    private lateinit var binding: FragmentSurveyIncenseBinding
    private lateinit var incenseAdapter: CircleRecyclerViewAdapter
    private val viewModel: SurveyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSurveyIncenseBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRvIncense()
        viewModel.getSeriesList()
    }

    private fun initRvIncense() {
        incenseAdapter = CircleRecyclerViewAdapter(1,
            add = { index->viewModel.addSeriesList(index)},remove = {index:Int->viewModel.removeSeriesList(index)})
        binding.rvSurveyIncense.adapter = incenseAdapter
        incenseAdapter.seriesData= mutableListOf(
            SeriesInfo(
                "구르망",
                englishName = "gu",
                seriesIdx = 1,
                imageUrl = R.drawable.dummy_example_1
            ),SeriesInfo(
                "그린",
                englishName = "gu",
                seriesIdx = 2,
                imageUrl = R.drawable.dummy_example_2
            ),SeriesInfo(
                "머스키",
                englishName = "gu",
                seriesIdx = 3,
                imageUrl = R.drawable.dummy_example_3
            ),SeriesInfo(
                "구르망",
                englishName = "gu",
                seriesIdx = 4,
                imageUrl = R.drawable.dummy_example_1
            )
        )
        incenseAdapter.notifyDataSetChanged()


    }

    override fun onResume() {
        super.onResume()
        incenseAdapter.setPerfumeData(viewModel.perfumeList.value)
    }

}