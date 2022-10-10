package com.scentsnote.android.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.scentsnote.android.ScentsNoteApplication
import com.scentsnote.android.R
import com.scentsnote.android.databinding.FragmentHomeBinding
import com.scentsnote.android.ui.home.adapter.NewListAdapter
import com.scentsnote.android.ui.home.adapter.PopularListAdapter
import com.scentsnote.android.ui.home.adapter.RecentListAdapter
import com.scentsnote.android.ui.home.adapter.RecommendListAdapter
import com.scentsnote.android.util.extension.setOnSafeClickListener
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

        binding.btnHomeMore.setOnSafeClickListener {
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

    override fun onResume() {
        super.onResume()

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            homeViewModel.getHomePerfumeList()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initInfo(){
        if(ScentsNoteApplication.prefManager.haveToken()){
            binding.txtHomeNameTitle.text = ScentsNoteApplication.prefManager.userNickname+ getString(R.string.txt_home_title)
            binding.txtHomeAgeTitle.text = getAgeGroupInfo().toString() + "대 " + getGenderInfo()+getString(R.string.txt_home_age)
            binding.txtHomeNameSubtitle.text = ScentsNoteApplication.prefManager.userNickname+getString(R.string.txt_home_subtitle)
        }
    }

    // 나이 구하기
    private fun getAgeGroupInfo() : Int{
        val age = getYear() - ScentsNoteApplication.prefManager.userAge + 1

        return (age/10)*10
    }

    // 현재 년도 구하기
    private fun getYear(): Int{
        val instance = Calendar.getInstance()
        return instance.get(Calendar.YEAR)
    }


    private fun getGenderInfo(): String{
        return if(ScentsNoteApplication.prefManager.userGender == "MAN"){
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
            PopularListAdapter(requireContext(),parentFragmentManager) { idx -> homeViewModel.postPerfumeLike(0, idx)}
        binding.rvHomePopular.adapter = popularAdapter

        popularAdapter.notifyDataSetChanged()

    }

    private fun initRecentList(){
        recentAdapter =
            RecentListAdapter(requireContext(), parentFragmentManager) { idx -> homeViewModel.postPerfumeLike(1, idx)}
        binding.rvHomeRecent.adapter = recentAdapter

        recentAdapter.notifyDataSetChanged()

    }

    private fun initNewList(){
        newAdapter =
            NewListAdapter(requireContext(), parentFragmentManager) { idx -> homeViewModel.postPerfumeLike(2, idx)}
        binding.rvHomeNew.adapter = newAdapter

        newAdapter.notifyDataSetChanged()
    }

}