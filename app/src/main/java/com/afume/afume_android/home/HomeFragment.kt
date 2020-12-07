package com.afume.afume_android.home

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.PagerSnapHelper
import com.afume.afume_android.data.vo.HomePerfumeListData
import com.afume.afume_android.data.vo.RecommendPerfumeListData
import com.afume.afume_android.databinding.FragmentHomeBinding
import com.afume.afume_android.home.adapter.NewListAdapter
import com.afume.afume_android.home.adapter.PopularListAdapter
import com.afume.afume_android.home.adapter.RecentListAdapter
import com.afume.afume_android.home.adapter.RecommendListAdapter


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var recommendAdapter : RecommendListAdapter
    lateinit var popularAdapter : PopularListAdapter
    lateinit var recentAdapter : RecentListAdapter
    lateinit var newAdapter : NewListAdapter
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRecommendList()
        initPopularList()
        initRecentList()
        initNewList()
    }

    private fun initRecommendList(){
        recommendAdapter = RecommendListAdapter(requireContext())
        binding.rvHomeRecommend.adapter = recommendAdapter

        recommendAdapter.data = mutableListOf(
            RecommendPerfumeListData(
                image = null,
                brand = "1번 브랜드",
                name = "1번향수",
                tags = listOf("#세련됨")
            ),
            RecommendPerfumeListData(
                image = null,
                brand = "2번 브랜드",
                name = "2번향수",
                tags = listOf("#세련됨", "#시원함")
            ),
            RecommendPerfumeListData(
                image = null,
                brand = "3번 브랜드",
                name = "3번향수",
                tags = listOf("#시원함", "#여성스러운")
            ),
            RecommendPerfumeListData(
                image = null,
                brand = "4번 브랜드",
                name = "4번향수",
                tags = listOf("#세련됨")
            )
        )
        recommendAdapter.notifyDataSetChanged()

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvHomeRecommend)

        binding.indicatorHome.attachTo(binding.rvHomeRecommend)
    }

    private fun initPopularList(){
        popularAdapter = PopularListAdapter(requireContext())
        binding.rvHomePopular.adapter = popularAdapter

        popularAdapter.data = mutableListOf(
            HomePerfumeListData(
                image = null,
                brand = "1번 브랜드",
                name = "1번향수",
                like = 0
            ),
            HomePerfumeListData(
                image = null,
                brand = "2번 브랜드",
                name = "2번향수",
                like = 1
            ),
            HomePerfumeListData(
                image = null,
                brand = "3번 브랜드",
                name = "3번향수",
                like = 1
            ),
            HomePerfumeListData(
                image = null,
                brand = "4번 브랜드",
                name = "4번향수",
                like = 0
            )
        )
        popularAdapter.notifyDataSetChanged()

    }

    private fun initRecentList(){
        recentAdapter = RecentListAdapter(requireContext())
        binding.rvHomeRecent.adapter = recentAdapter

        recentAdapter.data = mutableListOf(
            HomePerfumeListData(
                image = null,
                brand = "1번 브랜드",
                name = "1번향수",
                like = 0
            ),
            HomePerfumeListData(
                image = null,
                brand = "2번 브랜드",
                name = "2번향수",
                like = 1
            ),
            HomePerfumeListData(
                image = null,
                brand = "3번 브랜드",
                name = "3번향수",
                like = 1
            ),
            HomePerfumeListData(
                image = null,
                brand = "4번 브랜드",
                name = "4번향수",
                like = 0
            )
        )
        recentAdapter.notifyDataSetChanged()

    }

    private fun initNewList(){
        newAdapter = NewListAdapter(requireContext())
        binding.rvHomeNew.adapter = newAdapter

        newAdapter.data = mutableListOf(
            HomePerfumeListData(
                image = null,
                brand = "1번 브랜드",
                name = "1번향수",
                like = 1
            ),
            HomePerfumeListData(
                image = null,
                brand = "2번 브랜드",
                name = "2번향수",
                like = 1
            ),
            HomePerfumeListData(
                image = null,
                brand = "3번 브랜드",
                name = "3번향수",
                like = 1
            ),
            HomePerfumeListData(
                image = null,
                brand = "4번 브랜드",
                name = "4번향수",
                like = 0
            )
        )
        newAdapter.notifyDataSetChanged()

        binding.btnHomeMore.setOnClickListener {
            val moreIntent = Intent(context,MoreNewPerfumeActivity::class.java)

            startActivity(moreIntent)
        }

    }

//    fun moreNewPerfume(view: View){
//        Log.d("명","되나")
//        val moreIntent = Intent(context,MoreNewPerfumeActivity::class.java)
//
//        startActivity(moreIntent)
//    }

}