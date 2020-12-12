package com.afume.afume_android.ui.my.wishlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.RvMyPerfumeData
import com.afume.afume_android.databinding.FragmentWishListBinding


class WishListFragment : Fragment() {
    private lateinit var binding : FragmentWishListBinding
    private lateinit var wishListAdapter:WishListRecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentWishListBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRvWishList()
        dummyWishListPerfume()

    }

    private fun initRvWishList(){
        wishListAdapter=WishListRecyclerViewAdapter()
        binding.rvWishlist.adapter=wishListAdapter
        wishListAdapter.notifyDataSetChanged()
    }
    private fun dummyWishListPerfume(){
        wishListAdapter.data= listOf(
            RvMyPerfumeData(
                img=R.drawable.dummy_img_chanel,
                brandName = "CHANEL",
                perfumeName = "N°5 오 드 빠…"
            ),
            RvMyPerfumeData(
                R.drawable.dummy_perfume_image,
                "LE LABO",
                "아너다 13"
            ),
            RvMyPerfumeData(
                R.drawable.img_le_labo_13_sample,
                "Jo Malone London",
                "블랙베리 앤"
            ),RvMyPerfumeData(
                R.drawable.img_le_labo_13_sample,
                "Jo Malone London",
                "라임 앤 바질"
            ),RvMyPerfumeData(
                R.drawable.dummy_img_diptyque,
                "diptyque",
                "도손 오 드 퍼퓸"
            ),
            RvMyPerfumeData(
                R.drawable.dummy_perfume_image,
                "LE LABO",
                "상탈 33"
            ),RvMyPerfumeData(
                R.drawable.dummy_img_diptyque,
                "diptyque",
                "도손 오 드 퍼퓸"
            )

        )
    }




}