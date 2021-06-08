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
//    private val viewModel = ViewModelProvider(activity as MainActivity)[SearchViewModel::class.java]
//    private val viewModel:SearchViewModel by activityViewModels()
//    private val viewModel: SearchViewModel by view
//    } activityViewModels()
//    private val viewModel: SearchViewModel by activityViewModels {
//        object : ViewModelProvider.Factory {
//            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
//                SearchViewModel() as T
//        }
//    }
//    private val viewModel: SearchViewModel by lazy {
//        ViewModelProvider(requireActivity(), object : ViewModelProvider.Factory {
//            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
//                SearchViewModel() as T
//        }).get(SearchViewModel::class.java)
//    }
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
//        rvPerfumeAdapter.data = listOf(
//            DefaultRecyclerViewPerfumeViewModel("르라브", "어마더 13"),
//            DefaultRecyclerViewPerfumeViewModel("르라브", "어마더 13")
//        )
        rvPerfumeAdapter.notifyDataSetChanged()
    }

    private fun initRvFilterList() {
        val rvFilterAdapter = SelectedFilterRecyclerViewAdapter { viewModel.postSearchResultPerfume()}
        binding.rvSearchFilter.adapter = rvFilterAdapter
//        rvFilterAdapter.filterList =
//            mutableListOf(FilterInfoP(2, "시트러스", 2), FilterInfoP(3, "비누", 3))
        rvFilterAdapter.notifyDataSetChanged()
    }

    private fun goToSelectFilters(ctx: Context) {
        val intent = Intent(ctx, FilterActivity::class.java)
        startActivity(intent)
    }

    private fun observeFilter(){
        viewModel.filterList.observe(viewLifecycleOwner, Observer {
            if(it!=null&&it.size==0){
                binding.rvSearchFilter.visibility=View.GONE
            }
        })
    }


}