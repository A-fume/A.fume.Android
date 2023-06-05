package com.scentsnote.android.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.scentsnote.android.R
import com.scentsnote.android.ScentsNoteApplication
import com.scentsnote.android.data.vo.request.FilterInfoP
import com.scentsnote.android.data.vo.request.FilterType
import com.scentsnote.android.data.vo.request.SendFilter
import com.scentsnote.android.databinding.FragmentSearchBinding
import com.scentsnote.android.ui.filter.FilterFragment
import com.scentsnote.android.utils.base.BaseWebViewActivity
import com.scentsnote.android.viewmodel.search.SearchViewModel
import com.scentsnote.android.utils.extension.setOnSafeClickListener
import com.scentsnote.android.viewmodel.filter.FilterBrandViewModel
import com.scentsnote.android.viewmodel.filter.FilterKeywordViewModel
import com.scentsnote.android.viewmodel.filter.FilterSeriesViewModel

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by activityViewModels()
    private val filterSeriesViewModel: FilterSeriesViewModel by activityViewModels()
    private val filterBrandViewModel: FilterBrandViewModel by activityViewModels()
    private val filterKeywordViewModel: FilterKeywordViewModel by activityViewModels()
    private lateinit var rvFilterAdapter: SelectedFilterRecyclerViewAdapter
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return initBinding(inflater, container)
    }

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRvPerfumeList()
        initRvFilterList()
        initToolbar()

        binding.fabFilter.setOnSafeClickListener { openSelectFilters() }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.postSearchResultPerfume()
        }
        observeFilter()
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

    override fun onAttach(context: Context) {
        super.onAttach(context)

        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                when (viewModel.fragmentType.value) {
                    SearchFragmentType.RESULT -> {
                        onBackPressed()
                    }

                    else -> {
                        findNavController().popBackStack()
                    }
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onDetach() {
        super.onDetach()
        onBackPressedCallback.remove()
    }

    private fun initToolbar() {
        binding.toolbarBtnSearch.setOnSafeClickListener {
            openSearchTextFragment()
        }

        binding.toolbarBtnBack.setOnSafeClickListener {
            onBackPressed()
        }
    }

    private fun initRvPerfumeList() {
        val rvPerfumeAdapter =
            DefaultPerfumeRecyclerViewAdapter(requireContext(), parentFragmentManager) { idx ->
                viewModel.postPerfumeLike(idx, context)
            }
        binding.rvSearchPerfume.adapter = rvPerfumeAdapter
    }

    private fun observeFilter() {
        viewModel.filter.observe(viewLifecycleOwner) {
            if (it.filterInfoPList.isNullOrEmpty()) {
                goBackToHome()
            }
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.postSearchResultPerfume()
            }
        }
    }

    private fun onBackPressed() {
        clearFilterList()
        goBackToHome()
    }

    private fun goBackToHome() {
        viewModel.setPageType(SearchFragmentType.HOME)
    }

    private fun initRvFilterList() {
        rvFilterAdapter = SelectedFilterRecyclerViewAdapter { filterInfoP ->
            viewModel.cancelBtnFilter(filterInfoP)
            removeFilter(filterInfoP)
        }

        binding.rvSearchFilter.adapter = rvFilterAdapter
    }

    private fun removeFilter(filterInfoP: FilterInfoP) {
        when (filterInfoP.type) {
            FilterType.Ingredient -> {
                filterSeriesViewModel.removeFromSelectedList(filterInfoP)
            }

            FilterType.Brand -> {
                filterBrandViewModel.removeFromSelectedList(filterInfoP)
            }

            FilterType.Keyword -> {
                filterKeywordViewModel.removeFromSelectedList(filterInfoP)
            }

            else -> Unit
        }
    }

    private fun clearFilterList() {
        viewModel.filter.value = SendFilter(null, null)
        filterSeriesViewModel.clearSelectedList()
        filterBrandViewModel.clearSelectedList()
        filterKeywordViewModel.clearSelectedList()
    }

    private fun openSelectFilters() {
        childFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_up,
                R.anim.slide_down,
                R.anim.slide_up,
                R.anim.slide_down
            )
            .add(R.id.fragment_container, FilterFragment.newInstance())
            .commitAllowingStateLoss()
    }

    private fun openSearchTextFragment() {
        childFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_up,
                R.anim.slide_down,
                R.anim.slide_up,
                R.anim.slide_down
            )
            .add(R.id.fragment_container, SearchTextFragment.newInstance())
            .commitAllowingStateLoss()
    }

    fun onClickTipOffBtn(view: View) {
        val intent = Intent(requireContext(), BaseWebViewActivity::class.java)
        intent.putExtra("url", "tipOff")
        startActivity(intent)
    }
}