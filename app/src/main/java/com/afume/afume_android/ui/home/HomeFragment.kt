package com.afume.afume_android.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.afume.afume_android.AfumeApplication
import com.afume.afume_android.R
import com.afume.afume_android.databinding.FragmentHomeBinding
import com.afume.afume_android.ui.home.adapter.NewListAdapter
import com.afume.afume_android.ui.home.adapter.PopularListAdapter
import com.afume.afume_android.ui.home.adapter.RecentListAdapter
import com.afume.afume_android.ui.home.adapter.RecommendListAdapter
import java.util.*


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
        binding.viewModel = homeViewModel

        initInfo()

        binding.btnHomeMore.setOnClickListener {
            val moreNewPerfumeIntent = Intent(context,MoreNewPerfumeActivity::class.java)

            startActivity(moreNewPerfumeIntent)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observe()

        initRecommendList()
        initPopularList()
        initRecentList()
        initNewList()
    }

    @SuppressLint("SetTextI18n")
    private fun initInfo(){
        if(AfumeApplication.prefManager.haveToken()){
            binding.txtHomeNameTitle.text = AfumeApplication.prefManager.userNickname+ getString(R.string.txt_home_title)
            binding.txtHomeAgeTitle.text = getAgeGroupInfo().toString() + "대 " + getGenderInfo()+getString(R.string.txt_home_age)
            binding.txtHomeNameSubtitle.text = AfumeApplication.prefManager.userNickname+getString(R.string.txt_home_subtitle)
        }
    }

    // 나이 구하기
    private fun getAgeGroupInfo() : Int{
        val age = getYear() - AfumeApplication.prefManager.userAge + 1

        return (age/10)*10
    }

    // 현재 년도 구하기
    private fun getYear(): Int{
        val instance = Calendar.getInstance()
        return instance.get(Calendar.YEAR)
    }


    private fun getGenderInfo(): String{
        return if(AfumeApplication.prefManager.userGender == "MAN"){
            "남성"
        }else{
            "여성"
        }
    }

    private fun initRecommendList(){
        recommendAdapter =
            RecommendListAdapter(
                requireContext()
            )

        binding.vpHomeRecommend.adapter = recommendAdapter
    }

    private fun observe(){
        homeViewModel.recommendPerfumeList.observe(requireActivity(), androidx.lifecycle.Observer {

            recommendAdapter.run {
                replaceAll(ArrayList(it))
                notifyDataSetChanged()
            }

            binding.indicatorHome.setViewPager(binding.vpHomeRecommend)
        })
    }

    private fun initPopularList(){
        popularAdapter =
            PopularListAdapter(parentFragmentManager) { idx -> homeViewModel.postPerfumeLike(0, idx)}
        binding.rvHomePopular.adapter = popularAdapter

        popularAdapter.notifyDataSetChanged()

    }

    private fun initRecentList(){
        recentAdapter =
            RecentListAdapter(parentFragmentManager) { idx -> homeViewModel.postPerfumeLike(1, idx)}
        binding.rvHomeRecent.adapter = recentAdapter

        recentAdapter.notifyDataSetChanged()

    }

    private fun initNewList(){
        newAdapter =
            NewListAdapter(parentFragmentManager) { idx -> homeViewModel.postPerfumeLike(2, idx)}
        binding.rvHomeNew.adapter = newAdapter

        newAdapter.notifyDataSetChanged()
    }

}