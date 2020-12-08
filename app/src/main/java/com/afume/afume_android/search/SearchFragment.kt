package com.afume.afume_android.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.afume.afume_android.R
import com.afume.afume_android.databinding.FragmentSearchBinding
import com.afume.afume_android.filter.FilterActivity


class SearchFragment : Fragment() {
    private lateinit var binding:FragmentSearchBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRvPerfumeList()
        initRvFilterList()

        binding.fabFilter.setOnClickListener { context?.let { it1 -> goToSelectFilters(it1) } }
        binding.search=R.drawable.icon_btn_search
        binding.toolbarSearch.toolbarBtn.setOnClickListener {
            val intent=Intent(context, FilterActivity::class.java)
            startActivity(intent)
        }

        binding.toolbarSearch.toolbartxt="검색"
        binding.toolbarSearch.toolbar=R.drawable.icon_btn_search



    }

    private fun initRvPerfumeList(){
        val rvPerfumeAdapter=DefaultPerfumeRecyclerViewAdapter(context)
        binding.rvSearchPerfume.adapter=rvPerfumeAdapter
        rvPerfumeAdapter.data= listOf(DefaultRecyclerViewPerfumeViewModel("르라브","어마더 13"),DefaultRecyclerViewPerfumeViewModel("르라브","어마더 13"))
        rvPerfumeAdapter.notifyDataSetChanged()
    }
    private fun initRvFilterList(){
        val rvFilterAdapter=SelectedFilterRecyclerViewAdapter(context)
        binding.rvSearchFilter.adapter=rvFilterAdapter
        rvFilterAdapter.data= listOf(SelectedFilterViewModel("시트러스"),SelectedFilterViewModel("끌로에"))
        rvFilterAdapter.notifyDataSetChanged()
    }
    private fun goToSelectFilters(ctx:Context){
        val intent=Intent(ctx, FilterActivity::class.java)
        startActivity(intent)

    }


}