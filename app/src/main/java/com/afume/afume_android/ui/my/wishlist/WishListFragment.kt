package com.afume.afume_android.ui.my.wishlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.afume.afume_android.AfumeApplication
import com.afume.afume_android.databinding.FragmentWishListBinding
import com.afume.afume_android.databinding.LayoutPleaseLoginBinding
import com.afume.afume_android.ui.my.MyViewModel
import com.afume.afume_android.ui.signin.SignInActivity


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

        if (AfumeApplication.prefManager.accessToken == "") setPleaseLoginView(loginBinding)
        else setMyWishListView(wishListBinding)

    }

    private fun inflateView(container: ViewGroup?): View {
        return if (AfumeApplication.prefManager.accessToken == "") {
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
            val intent = Intent(requireContext(), SignInActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setMyWishListView(binding: FragmentWishListBinding) {
        initRvWishList(binding)
    }

    private fun initRvWishList(binding: FragmentWishListBinding) {
        wishListAdapter = WishListAdapter()
        binding.rvWishlist.adapter = wishListAdapter
        wishListAdapter.notifyDataSetChanged()
    }


}