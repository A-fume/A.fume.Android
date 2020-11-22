package com.afume.afume_android.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.afume.afume_android.R
import com.afume.afume_android.databinding.FragmentHomeBinding
import com.afume.afume_android.databinding.FragmentMypageBinding

class MyPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

}