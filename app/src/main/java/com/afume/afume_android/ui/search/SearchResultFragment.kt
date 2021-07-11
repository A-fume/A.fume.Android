package com.afume.afume_android.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.afume.afume_android.databinding.FragmentSearchBinding
import com.afume.afume_android.ui.filter.FilterActivity


class SearchResultFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var rvFilterAdapter: SelectedFilterRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(requireActivity(), SingleViewModelFactory.getInstance())[SearchViewModel::class.java]
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRvPerfumeList()
        initRvFilterList()

        binding.fabFilter.setOnClickListener { context?.let { it1 -> goToSelectFilters(it1) } }


        initToolbar()

        viewModel.postSearchResultPerfume()
        observeFilter()
    }

    override fun onResume() {
        super.onResume()
        viewModel.postSearchResultPerfume()
    }


    fun initToolbar(){
        binding.toolbarBtnSearch.setOnClickListener {
            val intent = Intent(context, SearchTextActivity::class.java)
            startActivity(intent)
        }

        binding.toolbarSearchTxt.text = "검색 결과"
        binding.toolbarBtnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initRvPerfumeList() {
        val rvPerfumeAdapter = DefaultPerfumeRecyclerViewAdapter(parentFragmentManager) { idx->viewModel.postPerfumeLike(idx)}
        binding.rvSearchPerfume.adapter = rvPerfumeAdapter
        rvPerfumeAdapter.notifyDataSetChanged()
    }

    private fun initRvFilterList() {
        rvFilterAdapter = SelectedFilterRecyclerViewAdapter { viewModel.postSearchResultPerfume()}
        binding.rvSearchFilter.adapter = rvFilterAdapter
        rvFilterAdapter.notifyDataSetChanged()
    }

    private fun goToSelectFilters(ctx: Context) {
        val intent = Intent(ctx, FilterActivity::class.java)
        startActivity(intent)
    }

    private fun observeFilter(){
        viewModel.filter.observe(viewLifecycleOwner, Observer {
            if(it.filterInfoPList!=null&& it.filterInfoPList!!.size==0){
                binding.rvSearchFilter.visibility=View.GONE
            }
        })
    }


}