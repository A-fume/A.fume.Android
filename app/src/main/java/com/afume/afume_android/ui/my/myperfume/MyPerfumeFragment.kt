package com.afume.afume_android.ui.my.myperfume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.RvMyPerfumeData
import com.afume.afume_android.databinding.FragmentMyPerfumeBinding


class MyPerfumeFragment : Fragment() {
    private lateinit var binding : FragmentMyPerfumeBinding
    private lateinit var myPerfumeAdapter:MyPerfumeRecyclerViewAdapter
    private lateinit var perfumeDataList:List<RvMyPerfumeData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentMyPerfumeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRvMyPerfume()
        dummyDataMyPerfume()
        initShelf()
    }

    private fun setInvisible(){
        //시향 노트가 있을 때 사용
        binding.imgMypurfume.visibility=View.INVISIBLE
        binding.textView.visibility=View.INVISIBLE
    }

    private fun initRvMyPerfume(){
        myPerfumeAdapter=MyPerfumeRecyclerViewAdapter()
        binding.rvMyPerfume.adapter=myPerfumeAdapter
        //binding.rvMyPerfume.setNestedScrollingEnabled(false)
        myPerfumeAdapter.notifyDataSetChanged()
    }
    private fun dummyDataMyPerfume(){
        perfumeDataList = listOf(
            RvMyPerfumeData(
                R.drawable.dummy_img_chanel,
                "CHANEL",
                "N°5 오 드 빠…",
                3f
            ),
            RvMyPerfumeData(
                R.drawable.dummy_perfume_image,
                "LE LABO",
                "아너다 13",
                4f
            ),
            RvMyPerfumeData(
                R.drawable.img_le_labo_13_sample,
                "Jo Malone London",
                "블랙베리 앤",
                4f
            ),RvMyPerfumeData(
                R.drawable.img_le_labo_13_sample,
                "Jo Malone London",
                "라임 앤 바질",
                4f
            ),RvMyPerfumeData(
                R.drawable.dummy_img_diptyque,
                "diptyque",
                "도손 오 드 퍼퓸",
                4.5f
            ),
            RvMyPerfumeData(
                R.drawable.dummy_perfume_image,
                "LE LABO",
                "상탈 33",
                3f
            ),RvMyPerfumeData(
                    R.drawable.dummy_img_diptyque,
            "diptyque",
            "도손 오 드 퍼퓸",
            4.5f
            )
        )
        myPerfumeAdapter.data=perfumeDataList

        if(myPerfumeAdapter.data.isNotEmpty()) setInvisible()
        binding.myPerfume="기록된 향수 ${myPerfumeAdapter.data.size}개"

    }
    private fun initShelf(){
        val shelfRecyclerViewAdapter=ShelfRecyclerViewAdapter(perfumeDataList.size)
        binding.rvMyPerfumeShelf.adapter=shelfRecyclerViewAdapter
        shelfRecyclerViewAdapter.notifyDataSetChanged()
    }




}