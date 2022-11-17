package com.scentsnote.android.ui.my.wishlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.scentsnote.android.ScentsNoteApplication
import com.scentsnote.android.databinding.FragmentWishListBinding
import com.scentsnote.android.databinding.LayoutPleaseLoginBinding
import com.scentsnote.android.ui.my.MyViewModel
import com.scentsnote.android.ui.signin.SignHomeActivity
import com.scentsnote.android.util.extension.setOnSafeClickListener
import kotlinx.coroutines.launch


class WishListFragment : Fragment() {
    private lateinit var loginBinding: LayoutPleaseLoginBinding
    private lateinit var wishListBinding: FragmentWishListBinding
    private lateinit var wishListAdapter: WishListAdapter
    private val myViewModel: MyViewModel by viewModels({requireParentFragment()})

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflateView(container)
    }

    override fun onResume() {
        super.onResume()

        if(ScentsNoteApplication.prefManager.haveToken()){
            lifecycleScope.launch {
                myViewModel.getLikedPerfume()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (ScentsNoteApplication.prefManager.haveToken()) initRvWishList(wishListBinding)
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
            wishListBinding.viewModel = myViewModel
            wishListBinding.root
        }
    }

    private fun setPleaseLoginView(binding: LayoutPleaseLoginBinding) {
        binding.btnGoToLogin.setOnSafeClickListener {
            val intent = Intent(requireContext(), SignHomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initRvWishList(binding: FragmentWishListBinding) {
        wishListAdapter = WishListAdapter()
        binding.rvWishlist.adapter = wishListAdapter
        wishListAdapter.notifyDataSetChanged()
    }

}