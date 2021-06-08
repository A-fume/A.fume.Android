package com.afume.afume_android.ui.survey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.afume.afume_android.databinding.FragmentSurveyPerfumeBinding

class SurveyPerfumeFragment : Fragment() {
    private lateinit var binding: FragmentSurveyPerfumeBinding
    private lateinit var surveyPerfumeAdapter: CircleRecyclerViewAdapter
    private val viewModel: SurveyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSurveyPerfumeBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner=this
        binding.vm=viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
//        observeList()
    }

    private fun initRv() {
        surveyPerfumeAdapter = CircleRecyclerViewAdapter(0, add = { index->viewModel.addPerfumeList(index)},remove = {index:Int->viewModel.removePerfumeList(index)})
        binding.rvItemSurveyPerfume.adapter = surveyPerfumeAdapter
        surveyPerfumeAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        surveyPerfumeAdapter.notifyDataSetChanged()
    }

}