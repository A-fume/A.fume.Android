package com.afume.afume_android.ui.my.myperfume

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.afume.afume_android.databinding.FragmentMyPerfumeBinding
import com.afume.afume_android.ui.my.MyViewModel


class MyPerfumeFragment : Fragment() {
    private lateinit var binding : FragmentMyPerfumeBinding
    private lateinit var myPerfumeAdapter:MyPerfumeRecyclerViewAdapter
    private val myViewModel: MyViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentMyPerfumeBinding.inflate(layoutInflater,container,false)
        binding.lifecycleOwner=this
        binding.vm=myViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRvMyPerfume()
        observeMyPerfume()
    }

    private fun setInvisible(){
        //시향 노트가 있을 때 사용
        binding.imgMypurfumeNull.visibility=View.INVISIBLE
        binding.txtMyperfumeNull.visibility=View.INVISIBLE
    }

    private fun initRvMyPerfume(){
        myPerfumeAdapter=MyPerfumeRecyclerViewAdapter()
        binding.rvMyPerfume.adapter=myPerfumeAdapter
        //binding.rvMyPerfume.setNestedScrollingEnabled(false)
        myPerfumeAdapter.notifyDataSetChanged()
        initShelf()
    }

    private fun initShelf(){
        if(myPerfumeAdapter.data.isNotEmpty()) setInvisible()
        Log.e("adapter",myPerfumeAdapter.data.toString())
        binding.txtCountMyPerfume.text="기록된 향수 ${myPerfumeAdapter.data.size}개"
        val shelfRecyclerViewAdapter=ShelfRecyclerViewAdapter(myPerfumeAdapter.data.size)
        binding.rvMyPerfumeShelf.adapter=shelfRecyclerViewAdapter
        shelfRecyclerViewAdapter.notifyDataSetChanged()
    }

    private fun observeMyPerfume(){
        myViewModel.myPerfumeList.observe(viewLifecycleOwner, Observer { list ->
            myPerfumeAdapter.data=list
            initShelf()
        })
    }






}