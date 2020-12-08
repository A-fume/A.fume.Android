package com.afume.afume_android.ui.filter.brand

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import com.afume.afume_android.databinding.FragmentFilterBrandBinding
import com.afume.afume_android.ui.filter.FilterViewModel
import com.afume.afume_android.ui.filter.ItemDetailsLookUp
import com.afume.afume_android.ui.filter.ItemKeyProvider
import com.afume.afume_android.ui.filter.RvBrandData
import com.google.android.material.tabs.TabLayout

class FilterBrandFragment : Fragment() {
    private val brandViewModel: BrandViewModel by viewModels()

    private lateinit var binding : FragmentFilterBrandBinding
    private lateinit var brandAdapter: BrandRecyclerViewAdapter
    private val activityViewModel: FilterViewModel by lazy {
        ViewModelProvider(requireActivity(), object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return FilterViewModel() as T
            }
        }).get(FilterViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentFilterBrandBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBrandTab()
        setTabClickEvent(context)
        initBrandRvItem(context)



    }
    private fun initBrandTab(){
        binding.tabBrand.apply {
            addTab(binding.tabBrand.newTab().setText("ㄱ"))
            addTab(binding.tabBrand.newTab().setText("ㄴ"))
            addTab(binding.tabBrand.newTab().setText("ㄷ"))
            addTab(binding.tabBrand.newTab().setText("ㄹ"))
            addTab(binding.tabBrand.newTab().setText("ㅁ"))
            addTab(binding.tabBrand.newTab().setText("ㅂ"))
            addTab(binding.tabBrand.newTab().setText("ㅅ"))
            addTab(binding.tabBrand.newTab().setText("ㅇ"))
            addTab(binding.tabBrand.newTab().setText("ㅈ"))
            addTab(binding.tabBrand.newTab().setText("ㅊ"))
            addTab(binding.tabBrand.newTab().setText("ㅋ"))
            addTab(binding.tabBrand.newTab().setText("ㅌ"))
            addTab(binding.tabBrand.newTab().setText("ㅍ"))
            addTab(binding.tabBrand.newTab().setText("ㅎ"))
        }

    }
    private fun initBrandRvItem(ctx:Context?){

        brandAdapter=
            BrandRecyclerViewAdapter(ctx)
        binding.rvBrand.apply {
            adapter=brandAdapter
            layoutManager=LinearLayoutManager(ctx)
        }

        val brandSelectionTracker= SelectionTracker.Builder<Long>(
            "brandName",
            binding.rvBrand,
            ItemKeyProvider(binding.rvBrand),
            ItemDetailsLookUp(
                binding.rvBrand,
                "brand"
            ),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(SelectionPredicates.createSelectAnything()).build()
        //brandAdapter.setSelectionTracker(brandSelectionTracker)

//        brandAdapter.data= listOf(
//            BrandViewModel("조말론"),
//            BrandViewModel("딥디크"),
//            BrandViewModel("데메테르"),
//            BrandViewModel("르라보"),
//            BrandViewModel("조말론"),
//        )

    }
    private fun setTabClickEvent(ctx:Context?){
        val tab =binding.tabBrand
        tab.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab?.position==0){
                    brandAdapter.data= listOf(
                        RvBrandData("ㄱ조말론"),
                        RvBrandData("ㄱ딥디크"),
                        RvBrandData("ㄱ데메테르"),
                        RvBrandData("ㄱ르라보"),
                        RvBrandData("ㄱ조말론"),
                    )
                }
                else if(tab?.position==1){
                    brandAdapter.data= listOf(
                        RvBrandData("ㄴ조말론"),
                        RvBrandData("ㄴ딥디크"),
                        RvBrandData("ㄴ데메테르"),
                        RvBrandData("ㄴ르라보"),
                        RvBrandData("ㄴ조말론"),
                    )
                }
                brandAdapter.notifyDataSetChanged()
            }

        })
    }


}