package com.scentsnote.android.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.scentsnote.android.ScentsNoteApplication
import com.scentsnote.android.R
import com.scentsnote.android.ScentsNoteApplication.Companion.firebaseAnalytics
import com.scentsnote.android.databinding.FragmentHomeBinding
import com.scentsnote.android.ui.home.adapter.NewListAdapter
import com.scentsnote.android.ui.home.adapter.PopularListAdapter
import com.scentsnote.android.ui.home.adapter.RecentListAdapter
import com.scentsnote.android.ui.home.adapter.RecommendListAdapter
import com.scentsnote.android.utils.extension.setClickEvent
import com.scentsnote.android.utils.extension.setHeartBtnClickEvent
import com.scentsnote.android.viewmodel.home.HomeViewModel
import com.scentsnote.android.utils.extension.setOnSafeClickListener
import com.scentsnote.android.utils.extension.setPageViewEvent
import kotlinx.coroutines.launch
import java.util.*

/**
 * 홈 화면
 *
 * 개인 맞춤 추천, 일반 추천(성별 나이 반영), 최근 조회한 향수, 비슷한 향수 추천, 새로운 향수 제공
 */
class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var recommendAdapter: RecommendListAdapter
    lateinit var popularAdapter: PopularListAdapter
    lateinit var recentAdapter: RecentListAdapter
    lateinit var newAdapter: NewListAdapter
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = homeViewModel

        initInfo()

        binding.btnHomeMore.setOnSafeClickListener {
            firebaseAnalytics.setClickEvent("NewRegisterButton")

            val moreNewPerfumeIntent = Intent(context, MoreNewPerfumeActivity::class.java)

            startActivity(moreNewPerfumeIntent)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initObserve()

        initRecommendList()
        initPopularList()
        initRecentList()
        initNewList()
    }

    override fun onResume() {
        super.onResume()

        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.run {
                getHomePerfumeList()
                getNewPerfumeList(10)
            }
        }

        firebaseAnalytics.setPageViewEvent("HomePage",this::class.java.name)
    }

    @SuppressLint("SetTextI18n")
    private fun initInfo() {
        if (ScentsNoteApplication.prefManager.haveToken()) {
            binding.txtHomeNameTitle.text =
                ScentsNoteApplication.prefManager.userNickname + getString(R.string.txt_home_title)
            binding.txtHomeAgeTitle.text =
                getAgeGroupInfo().toString() + "대 " + getGenderInfo() + getString(R.string.txt_home_age)
            binding.txtHomeNameSubtitle.text = setSubTitle()
        }
    }

    private fun setSubTitle(): String {
        return if (ScentsNoteApplication.prefManager.userAge == getYear() && ScentsNoteApplication.prefManager.userGender?.isEmpty() == false) {
            getString(R.string.txt_home_subtitle_age_null)
        } else if (ScentsNoteApplication.prefManager.userAge != getYear() && ScentsNoteApplication.prefManager.userGender?.isEmpty() == true) {
            getString(R.string.txt_home_subtitle_gender_null)
        } else if (ScentsNoteApplication.prefManager.userAge == getYear() && ScentsNoteApplication.prefManager.userGender?.isEmpty() == true) {
            getString(R.string.txt_home_subtitle_age_gender_null)
        } else ScentsNoteApplication.prefManager.userNickname + getString(R.string.txt_home_subtitle)
    }

    // 나이 구하기
    private fun getAgeGroupInfo(): Int {
        return if (ScentsNoteApplication.prefManager.userAge == getYear()) {
            20
        } else {
            val age = getYear() - ScentsNoteApplication.prefManager.userAge + 1
            val group = (age / 10) * 10
            if (group == 0) 10
            else group
        }
    }

    // 현재 년도 구하기
    private fun getYear(): Int {
        val instance = Calendar.getInstance()
        return instance.get(Calendar.YEAR)
    }


    private fun getGenderInfo(): String {
        return if (ScentsNoteApplication.prefManager.userGender == "MAN") {
            "남성"
        } else {
            "여성"
        }
    }

    private fun initRecommendList() {
        recommendAdapter =
            RecommendListAdapter(
                requireContext()
            )

        binding.vpHomeRecommend.adapter = recommendAdapter
    }

    private fun initObserve() {
        homeViewModel.recommendPerfumeList.observe(requireActivity()){

            recommendAdapter.run {
                replaceAll(ArrayList(it))
                notifyDataSetChanged()
            }

            binding.indicatorHome.setViewPager(binding.vpHomeRecommend)
        }

        homeViewModel.recentPerfumeList.observe(requireActivity()) {
            binding.clHomeRecent.visibility =
                if(it.isNotEmpty()) View.VISIBLE
                else View.GONE
        }
    }

    private fun initPopularList() {
        popularAdapter =
            PopularListAdapter(
                requireContext(),
                parentFragmentManager
            ){ idx -> homeViewModel.postPerfumeLike(0, idx)}

        popularAdapter.setOnItemClickListener(object  : PopularListAdapter.OnItemClickListener{
            override fun firebaseClickEvent(like: Boolean) {
                firebaseAnalytics.setHeartBtnClickEvent("home_recommend", like)
            }
        })

        binding.rvHomePopular.adapter = popularAdapter

        popularAdapter.notifyDataSetChanged()
    }

    private fun initRecentList() {
        recentAdapter =
            RecentListAdapter(
                requireContext(),
                parentFragmentManager
            ) { idx -> homeViewModel.postPerfumeLike(1, idx) }

        recentAdapter.setOnItemClickListener(object  : RecentListAdapter.OnItemClickListener{
            override fun firebaseClickEvent(like: Boolean) {
                firebaseAnalytics.setHeartBtnClickEvent("home_recently", like)
            }
        })

        binding.rvHomeRecent.adapter = recentAdapter

        recentAdapter.notifyDataSetChanged()
    }

    private fun initNewList() {
        newAdapter =
            NewListAdapter(
                requireContext(),
                parentFragmentManager
            ) { idx -> homeViewModel.postPerfumeLike(2, idx) }

        newAdapter.setOnItemClickListener(object  : NewListAdapter.OnItemClickListener{
            override fun firebaseClickEvent(like: Boolean) {
                firebaseAnalytics.setHeartBtnClickEvent("home_new", like)
            }
        })

        binding.rvHomeNew.adapter = newAdapter

        newAdapter.notifyDataSetChanged()
    }
}