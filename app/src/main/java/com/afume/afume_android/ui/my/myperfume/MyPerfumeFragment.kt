package com.afume.afume_android.ui.my.myperfume

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.afume.afume_android.AfumeApplication
import com.afume.afume_android.databinding.FragmentMyPerfumeBinding
import com.afume.afume_android.databinding.LayoutPleaseLoginBinding
import com.afume.afume_android.ui.my.MyViewModel
import com.afume.afume_android.ui.signin.SignHomeActivity


class MyPerfumeFragment : Fragment() {
    private lateinit var loginBinding: LayoutPleaseLoginBinding
    private lateinit var perfumeBinding: FragmentMyPerfumeBinding
    private lateinit var myPerfumeAdapter: MyPerfumeRecyclerViewAdapter
    private val myViewModel: MyViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflateView(container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (AfumeApplication.prefManager.accessToken == "") setPleaseLoginView(loginBinding)
        else setMyPerfumeView(perfumeBinding)

    }

    override fun onResume() {
        super.onResume()
        if (AfumeApplication.prefManager.haveToken()) {
            myViewModel.getMyPerfume()
        }
    }


    private fun inflateView(container: ViewGroup?): View {
        return if (AfumeApplication.prefManager.accessToken == "") {
            loginBinding = LayoutPleaseLoginBinding.inflate(layoutInflater, container, false)
            loginBinding.lifecycleOwner = this
            loginBinding.root
        } else {
            perfumeBinding = FragmentMyPerfumeBinding.inflate(layoutInflater, container, false)
            perfumeBinding.lifecycleOwner = this
            perfumeBinding.vm = myViewModel
            perfumeBinding.root
        }
    }

    private fun setPleaseLoginView(binding: LayoutPleaseLoginBinding) {
        binding.btnGoToLogin.setOnClickListener {
            val intent = Intent(requireContext(), SignHomeActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setMyPerfumeView(binding: FragmentMyPerfumeBinding) {
        initRvMyPerfume(binding)
        observeMyPerfume(binding)

    }

    private fun setInvisible(binding: FragmentMyPerfumeBinding) {
        //시향 노트가 있을 때 사용
        binding.imgMypurfumeNull.visibility = View.INVISIBLE
        binding.txtMyperfumeNull.visibility = View.INVISIBLE
    }

    private fun initRvMyPerfume(binding: FragmentMyPerfumeBinding) {
        myPerfumeAdapter = MyPerfumeRecyclerViewAdapter()
        binding.rvMyPerfume.adapter = myPerfumeAdapter
        //binding.rvMyPerfume.setNestedScrollingEnabled(false)
        myPerfumeAdapter.notifyDataSetChanged()
        initShelf(binding)
    }

    private fun initShelf(binding: FragmentMyPerfumeBinding) {
        if (myPerfumeAdapter.data.isNotEmpty()) setInvisible(binding)
        Log.e("adapter", myPerfumeAdapter.data.toString())
        binding.txtCountMyPerfume.text = "기록된 향수 ${myPerfumeAdapter.data.size}개"
        val shelfRecyclerViewAdapter = ShelfRecyclerViewAdapter(myPerfumeAdapter.data.size)
        binding.rvMyPerfumeShelf.adapter = shelfRecyclerViewAdapter
        shelfRecyclerViewAdapter.notifyDataSetChanged()
    }

    private fun observeMyPerfume(binding: FragmentMyPerfumeBinding) {
        myViewModel.myPerfumeList.observe(viewLifecycleOwner, Observer { list ->
            myPerfumeAdapter.data = list
            initShelf(binding)
        })
    }


}