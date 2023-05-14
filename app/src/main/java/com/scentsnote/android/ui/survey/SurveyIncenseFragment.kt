package com.scentsnote.android.ui.survey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.scentsnote.android.R
import com.scentsnote.android.databinding.FragmentSurveyIncenseBinding
import com.scentsnote.android.viewmodel.survey.SurveyViewModel

class SurveyIncenseFragment : Fragment() {
    private lateinit var binding: FragmentSurveyIncenseBinding
    private lateinit var incenseAdapter: CircleRecyclerViewAdapter
    private val viewModel: SurveyViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return initBinding(container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRvIncense()

        incenseAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
//        incenseAdapter.setPerfumeData(viewModel.perfumeList.value)
        viewModel.setActiveButton(2)
        incenseAdapter.setSeriesData(viewModel.seriesList.value)
    }

    private fun initBinding(container: ViewGroup?): View {
        binding = FragmentSurveyIncenseBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        binding.listEmpty.text = String.format(getString(R.string.txt_list_empty),"계열")
        viewModel.setActiveButton(2)
        binding.vm = viewModel
        return binding.root
    }

    private fun initRvIncense() {
        incenseAdapter = CircleRecyclerViewAdapter(1,
            add = { index -> viewModel.addSeriesList(index) },
            remove = { index: Int -> viewModel.removeSeriesList(index) })
        binding.rvSurveyIncense.apply {
            setEmptyView(binding.listEmpty)
            adapter = incenseAdapter
        }
        incenseAdapter.notifyDataSetChanged()
    }


}