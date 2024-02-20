package com.scentsnote.android.ui.detail

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import com.scentsnote.android.R
import com.scentsnote.android.databinding.FragmentLowestPriceBinding
import com.scentsnote.android.utils.extension.closeSelfWithAnimation
import com.scentsnote.android.utils.extension.setOnSafeClickListener

class LowestPriceFragment : Fragment() {
    private var _binding: FragmentLowestPriceBinding? = null
    private val binding get() = _binding!!

    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentLowestPriceBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView(){
        binding.toolbarLowestPrice.apply {
            toolbar = R.drawable.icon_btn_cancel
            toolbartxt = "가격 비교"

            toolbarBtn.setOnSafeClickListener {
                closeSelfWithAnimation()
            }
        }

        binding.wvLowestPrice.apply {
            webViewClient = WebViewClient()
            loadUrl("https://www.naver.com/")
        }
    }

    companion object {
        fun newInstance(): LowestPriceFragment = LowestPriceFragment()
    }
}