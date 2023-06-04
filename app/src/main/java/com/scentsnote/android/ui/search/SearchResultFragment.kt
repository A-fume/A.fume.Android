package com.scentsnote.android.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.scentsnote.android.R
import com.scentsnote.android.databinding.FragmentSearchBinding
import com.scentsnote.android.ui.MainActivity
import com.scentsnote.android.ui.filter.FilterFragment
import com.scentsnote.android.viewmodel.search.SearchViewModel
import com.scentsnote.android.utils.base.BaseWebViewActivity
import com.scentsnote.android.utils.extension.setOnSafeClickListener

class SearchResultFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by activityViewModels()
    private lateinit var rvFilterAdapter: SelectedFilterRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return initBinding(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRvPerfumeList(context)
        initRvFilterList()
        initToolbar()

        binding.fabFilter.setOnSafeClickListener { goToSelectFilters() }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.postSearchResultPerfume()
        }
        observeFilter()
    }

    override fun onResume() {
        super.onResume()
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.postSearchResultPerfume()
        }
    }


    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.fragment = this
        return binding.root
    }

    fun initToolbar() {
        binding.toolbarBtnSearch.setOnSafeClickListener {
            val intent = Intent(context, SearchTextActivity::class.java)
            startActivity(intent)
        }

        binding.toolbarSearchTxt.text = "검색 결과"
        binding.toolbarBtnBack.setOnSafeClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initRvPerfumeList(context: Context?) {
        val rvPerfumeAdapter =
            DefaultPerfumeRecyclerViewAdapter(requireContext(), parentFragmentManager) { idx ->
                viewModel.postPerfumeLike(
                    idx,
                    context
                )
            }
        binding.rvSearchPerfume.adapter = rvPerfumeAdapter
        rvPerfumeAdapter.notifyDataSetChanged()
    }

    private fun initRvFilterList() {
        rvFilterAdapter = SelectedFilterRecyclerViewAdapter { filterInfoP ->
            viewModel.cancelBtnFilter(filterInfoP)
        }

        binding.rvSearchFilter.adapter = rvFilterAdapter
        rvFilterAdapter.notifyDataSetChanged()
    }

    private fun goToSelectFilters() {
        childFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_up,
                R.anim.slide_down,
                R.anim.slide_up,
                R.anim.slide_down
            )
            .replace(R.id.fragment_container, FilterFragment.newInstance())
            .commitAllowingStateLoss()
    }

    private fun observeFilter() {
        viewModel.filter.observe(viewLifecycleOwner, Observer {
            Log.d("searchResultFragment", "searchResultFragment observer")
            if (it.filterInfoPList == null || it.filterInfoPList!!.size == 0) {

                var activity = activity as MainActivity
                activity.getBackSearchHome()
            }
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.postSearchResultPerfume()
            }
        })
    }

    fun onClickTipOffBtn(view: View) {
        val intent = Intent(requireContext(), BaseWebViewActivity::class.java)
        intent.putExtra("url", "tipOff")
        startActivity(intent)
    }
}