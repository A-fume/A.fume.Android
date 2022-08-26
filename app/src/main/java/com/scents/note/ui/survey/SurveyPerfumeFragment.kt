package com.scents.note.ui.survey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.scents.note.R
import com.scents.note.databinding.FragmentSurveyPerfumeBinding

class SurveyPerfumeFragment : Fragment() {
    private lateinit var binding: FragmentSurveyPerfumeBinding
    private lateinit var surveyPerfumeAdapter: CircleRecyclerViewAdapter
    private val viewModel: SurveyViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return initBinding(container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
//        observeList()
    }

    override fun onResume() {
        super.onResume()
        viewModel.setActiveButton(0)
        surveyPerfumeAdapter.notifyDataSetChanged()
    }

    private fun initBinding(container: ViewGroup?): View {
        binding = FragmentSurveyPerfumeBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        binding.listEmpty.text = String.format(getString(R.string.txt_list_empty),"향수")
        viewModel.setActiveButton(0)
        binding.vm = viewModel
        return binding.root
    }

    private fun initRv() {
        surveyPerfumeAdapter = CircleRecyclerViewAdapter(0,
            add = { index -> viewModel.addPerfumeList(index) },
            remove = { index: Int -> viewModel.removePerfumeList(index) })
        binding.rvItemSurveyPerfume.apply {
            setEmptyView(binding.listEmpty)
            adapter = surveyPerfumeAdapter
        }
        surveyPerfumeAdapter.notifyDataSetChanged()
    }


}