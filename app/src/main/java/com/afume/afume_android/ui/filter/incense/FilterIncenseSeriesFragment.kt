package com.afume.afume_android.ui.filter.incense

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.afume.afume_android.databinding.FragmentFilterIncenseSeriesBinding
import com.afume.afume_android.ui.filter.FilterViewModel

class FilterIncenseSeriesFragment : Fragment() {
    private lateinit var binding:FragmentFilterIncenseSeriesBinding
    private val viewModel: FilterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentFilterIncenseSeriesBinding.inflate(inflater, container, false)
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initIncenseSeriesRv(context)
    }
    private fun initIncenseSeriesRv(ctx: Context?){

        val seriesAdapter=SeriesIngredientsViewAdapter { idx, list->viewModel.addSeriesIngredientIdx(idx,list)}
        binding.rvIncenseSeries.adapter=seriesAdapter


      }
}