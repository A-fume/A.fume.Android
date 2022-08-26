package com.scents.note.ui.my.wishlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.scents.note.ScentsNoteApplication
import com.scents.note.databinding.FragmentWishListBinding
import com.scents.note.databinding.LayoutPleaseLoginBinding
import com.scents.note.ui.my.MyViewModel
import com.scents.note.ui.signin.SignHomeActivity


class WishListFragment : Fragment() {
    private lateinit var loginBinding: LayoutPleaseLoginBinding
    private lateinit var wishListBinding: FragmentWishListBinding
    private lateinit var wishListAdapter: WishListAdapter
    private val myViewModel: MyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflateView(container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (ScentsNoteApplication.prefManager.haveToken()) setMyWishListView(wishListBinding)
        else setPleaseLoginView(loginBinding)
    }

    private fun inflateView(container: ViewGroup?): View {
        return if (ScentsNoteApplication.prefManager.accessToken == "") {
            loginBinding = LayoutPleaseLoginBinding.inflate(layoutInflater, container, false)
            loginBinding.lifecycleOwner = this
            loginBinding.root
        } else {
            wishListBinding = FragmentWishListBinding.inflate(layoutInflater, container, false)
            wishListBinding.lifecycleOwner = this
            wishListBinding.vm = myViewModel
            wishListBinding.root
        }
    }

    private fun setPleaseLoginView(binding: LayoutPleaseLoginBinding) {
        binding.btnGoToLogin.setOnClickListener {
            val intent = Intent(requireContext(), SignHomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setMyWishListView(binding: FragmentWishListBinding) {
        initRvWishList(binding)
        observeWishList(binding)
    }

    private fun initRvWishList(binding: FragmentWishListBinding) {
        wishListAdapter = WishListAdapter()
        binding.rvWishlist.adapter = wishListAdapter
        wishListAdapter.notifyDataSetChanged()
    }

    private fun setInvisible(binding: FragmentWishListBinding) {
        //위시리스트가 있을 때 사용
        if (wishListAdapter.data.isNotEmpty()) {
            binding.imgWishlistNull.visibility = View.INVISIBLE
            binding.txtWishlistNull.visibility = View.INVISIBLE
            binding.rvWishlist.visibility=View.VISIBLE
        }
        else{

            binding.imgWishlistNull.visibility = View.VISIBLE
            binding.txtWishlistNull.visibility = View.VISIBLE
            binding.rvWishlist.visibility=View.INVISIBLE
        }
    }
    private fun observeWishList(binding: FragmentWishListBinding) {
        myViewModel.wishList.observe(viewLifecycleOwner, Observer { list ->
            setInvisible(binding)
        })
    }

}