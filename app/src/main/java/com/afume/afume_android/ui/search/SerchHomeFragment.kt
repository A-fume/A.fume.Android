package com.afume.afume_android.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.afume.afume_android.R
import com.afume.afume_android.databinding.FragmentSearchBinding
import com.afume.afume_android.ui.filter.FilterActivity


class SearchHomeFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRvPerfumeList()
        removeRvFilterList()

        binding.fabFilter.setOnClickListener { context?.let { it1 -> goToSelectFilters(it1) } }
        binding.search = R.drawable.icon_btn_search
        binding.toolbarSearch.toolbarBtn.setOnClickListener {

            findNavController().navigate(R.id.action_searchHomeFragment_to_searchResultFragment)
//            val intent = Intent(context, FilterActivity::class.java)
//            startActivity(intent)
        }

        binding.toolbarSearch.toolbartxt = "검색"
        binding.toolbarSearch.toolbar = R.drawable.icon_btn_search

    }

    private fun initRvPerfumeList() {
        val rvPerfumeAdapter = DefaultPerfumeRecyclerViewAdapter(context)
        binding.rvSearchPerfume.adapter = rvPerfumeAdapter
        rvPerfumeAdapter.data = listOf(
            DefaultRecyclerViewPerfumeViewModel("르라브", "어마더 13"),
            DefaultRecyclerViewPerfumeViewModel("르라브", "어마더 13")
        )
        rvPerfumeAdapter.notifyDataSetChanged()
    }

    private fun removeRvFilterList() {
        binding.rvSearchFilter.visibility=View.GONE
//        val rvFilterAdapter = SelectedFilterRecyclerViewAdapter()
//        binding.rvSearchFilter.adapter = rvFilterAdapter
////        rvFilterAdapter.filterList =
////            mutableListOf(FilterInfoP(2, "시트러스", 2), FilterInfoP(3, "비누", 3))
//        rvFilterAdapter.notifyDataSetChanged()
    }

    private fun goToSelectFilters(ctx: Context) {
        val intent = Intent(ctx, FilterActivity::class.java)
        startActivity(intent)
    }


}