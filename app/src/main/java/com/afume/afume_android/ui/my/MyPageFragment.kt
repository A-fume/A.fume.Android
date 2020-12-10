package com.afume.afume_android.ui.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.afume.afume_android.R
import com.afume.afume_android.databinding.FragmentMypageBinding
import com.afume.afume_android.ui.filter.AfumeViewPagerAdapter
import com.afume.afume_android.ui.my.myperfume.MyPerfumeFragment

class MyPageFragment : Fragment() {
    private lateinit var binding: FragmentMypageBinding
    private lateinit var myPagePagerAdapter: AfumeViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBind()
        initVp()
        setUpTabWithViewPager()

    }
    private fun initBind(){
        
        binding.toolbarMypage.toolbartxt="마이"
        //바궈야함
        binding.toolbarMypage.toolbar=R.drawable.icon_btn_sidebar

    }

    private fun initVp(){
        myPagePagerAdapter=
            AfumeViewPagerAdapter(
                childFragmentManager
            )
        myPagePagerAdapter.fragments= listOf(
            MyPerfumeFragment(),
            WishListFragment()
        )
        binding.vpMypage.adapter=myPagePagerAdapter

    }

    private fun setUpTabWithViewPager(){
        binding.tabMypage.setupWithViewPager(binding.vpMypage)
        binding.tabMypage.apply {
            getTabAt(0)?.text="마이"
            getTabAt(1)?.text="위시 리스트"
        }
    }




}