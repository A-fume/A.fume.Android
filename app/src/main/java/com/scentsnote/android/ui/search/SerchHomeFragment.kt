package com.scentsnote.android.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.scentsnote.android.ScentsNoteApplication
import com.scentsnote.android.databinding.FragmentSearchBinding
import com.scentsnote.android.ui.filter.FilterActivity


class SearchHomeFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by activityViewModels()

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

        initToolbar()

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.postSearchResultPerfume()
        }
    }

    override fun onResume() {
        super.onResume()

        if (ScentsNoteApplication.prefManager.haveToken()) {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.postSearchResultPerfume()
            }
        } else {
            viewModel.resetHeartPerfumeList()
        }
    }

    fun initToolbar() {
        binding.toolbarBtnSearch.setOnClickListener {
            val intent = Intent(context, SearchTextActivity::class.java)
            startActivity(intent)
        }
        binding.toolbarSearchTxt.text = "검색"
        binding.toolbarBtnBack.visibility = View.GONE
    }

    private fun initRvPerfumeList() {
        val rvPerfumeAdapter = DefaultPerfumeRecyclerViewAdapter(requireContext(),parentFragmentManager) { idx ->
            viewModel.postPerfumeLike(idx, context)
        }
        binding.rvSearchPerfume.adapter = rvPerfumeAdapter
        rvPerfumeAdapter.notifyDataSetChanged()
    }

    private fun removeRvFilterList() {
        binding.rvSearchFilter.visibility = View.GONE
    }

    private fun goToSelectFilters(ctx: Context) {
        val intent = Intent(ctx, FilterActivity::class.java)
        intent.putExtra("from",SEARCH_HOME)
        startActivity(intent)
    }
    companion object{
        const val SEARCH_HOME=100
    }

}