package com.scentsnote.android.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.scentsnote.android.databinding.FragmentSearchTextBinding
import com.scentsnote.android.utils.extension.closeSelfWithAnimation
import com.scentsnote.android.utils.extension.setOnSafeClickListener
import com.scentsnote.android.viewmodel.search.SearchViewModel

class SearchTextFragment : Fragment() {
    private var _binding: FragmentSearchTextBinding? = null
    private val binding get() = _binding!!

    private val searchViewModel: SearchViewModel by activityViewModels()
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

    override fun onDetach() {
        super.onDetach()
        onBackPressedCallback.remove()
    }

    private fun setOnClickListener() {
        binding.btnSearch.setOnSafeClickListener {
            val searchText = binding.edtSearch.text.toString()
            searchViewModel.sendSearchText(searchText)
            closeSelfWithAnimation()
        }

        binding.btnBack.setOnSafeClickListener {
            closeSelfWithAnimation()
        }
    }

    companion object {
        fun newInstance(): SearchTextFragment = SearchTextFragment()
    }
}