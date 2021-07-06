package com.afume.afume_android.ui.filter.incense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.afume.afume_android.databinding.FragmentFilterIncenseSeriesBinding
import com.afume.afume_android.ui.filter.FilterViewModel
import com.afume.afume_android.ui.filter.FilterViewModelFactory

class FilterIncenseSeriesFragment : Fragment() {
    private lateinit var binding: FragmentFilterIncenseSeriesBinding
    private val viewModel: FilterViewModel by activityViewModels(){
        FilterViewModelFactory.getInstance()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       return initBinding(inflater,container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeBlockClickMoreThan5()

    }

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?):View{
        binding = FragmentFilterIncenseSeriesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        initIncenseSeriesRv()

        return binding.root
    }

    private fun initIncenseSeriesRv() {
        val seriesAdapter = SeriesIngredientsViewAdapter(
            { idx, list -> viewModel.addSeriesIngredientIdx(idx, list) },
            { tabNumber, add -> viewModel.countBadges(tabNumber, add) })
        binding.rvIncenseSeries.adapter = seriesAdapter
        seriesAdapter.notifyDataSetChanged()
    }

    fun observeBlockClickMoreThan5(){
        viewModel.badgeCount.observe(viewLifecycleOwner, Observer {
            viewModel.blockClickSeriesMoreThan5()
        })
    }

}