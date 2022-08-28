package com.scentsnote.android.ui.filter.incense

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.scentsnote.android.databinding.FragmentFilterIncenseSeriesBinding
import com.scentsnote.android.ui.filter.FilterViewModel
import com.scentsnote.android.ui.filter.FilterViewModelFactory

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
        Log.e("incense badge cnt",viewModel.badgeCount.value?.toString()!!)
        initIncenseSeriesRv()
        observeBlockClickMoreThan5()
    }

    override fun onResume() {
        super.onResume()
        observeBlockClickMoreThan5()
    }

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?):View{
        binding = FragmentFilterIncenseSeriesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.vm = viewModel

//        initIncenseSeriesRv()

        return binding.root
    }

    private fun initIncenseSeriesRv() {
        val seriesAdapter = SeriesIngredientsViewAdapter(
            { seriesName, list -> viewModel.addSeriesIngredientIdx(seriesName, list) },
            { tabNumber, add -> viewModel.countBadges(tabNumber, add) })
        binding.rvIncenseSeries.adapter = seriesAdapter
        seriesAdapter.notifyDataSetChanged()
    }

    fun observeBlockClickMoreThan5(){
        viewModel.badgeCount.observe(viewLifecycleOwner, Observer {
            viewModel.blockClickSeriesMoreThan5()
            Log.d("series_obverse", viewModel.selectedSeriesMap.value.toString())
        })
    }

}