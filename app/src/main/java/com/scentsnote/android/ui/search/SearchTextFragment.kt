package com.scentsnote.android.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.scentsnote.android.R
import com.scentsnote.android.ScentsNoteApplication.Companion.firebaseAnalytics
import com.scentsnote.android.databinding.FragmentSearchTextBinding
import com.scentsnote.android.utils.extension.*
import com.scentsnote.android.utils.extension.closeSelfWithAnimation
import com.scentsnote.android.utils.extension.setOnSafeClickListener
import com.scentsnote.android.utils.extension.setPageViewEvent
import com.scentsnote.android.utils.extension.toast
import com.scentsnote.android.viewmodel.filter.FilterBrandViewModel
import com.scentsnote.android.viewmodel.filter.FilterKeywordViewModel
import com.scentsnote.android.viewmodel.filter.FilterSeriesViewModel
import com.scentsnote.android.viewmodel.search.SearchViewModel

class SearchTextFragment : Fragment() {
    private var _binding: FragmentSearchTextBinding? = null
    private val binding get() = _binding!!

    private val searchViewModel: SearchViewModel by activityViewModels()
    private val filterSeriesViewModel: FilterSeriesViewModel by activityViewModels()
    private val filterBrandViewModel: FilterBrandViewModel by activityViewModels()
    private val filterKeywordViewModel: FilterKeywordViewModel by activityViewModels()
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentSearchTextBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                closeSelfWithAnimation()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onResume() {
        super.onResume()

        firebaseAnalytics.setPageViewEvent("SearchWindow", this::class.java.name)
    }

    override fun onDetach() {
        super.onDetach()
        onBackPressedCallback.remove()
    }

    private fun setOnClickListener() {
        binding.btnSearch.setOnSafeClickListener {
            searchPerfumeWithText()
        }

        binding.btnBack.setOnSafeClickListener {
            closeSelfWithAnimation()

            firebaseAnalytics.setClickEvent("SearchBack")
        }

        binding.edtSearch.setOnEditorActionListener { _, actionId, _ ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                searchPerfumeWithText()
                handled = true
            }
            handled
        }
    }

    private fun searchPerfumeWithText(){
        val searchText = binding.edtSearch.text.toString()
        if (searchText.isNotBlank()) {
            searchViewModel.sendSearchText(searchText)
            filterSeriesViewModel.clearSelectedList()
            filterBrandViewModel.clearSelectedList()
            filterKeywordViewModel.clearSelectedList()
            closeSelfWithAnimation()
            firebaseAnalytics.setClickEvent("SearchLoupeButton")
        }else{
            requireContext().toast(getString(R.string.txt_search_text_null))
        }
    }

    companion object {
        fun newInstance(): SearchTextFragment = SearchTextFragment()
    }
}