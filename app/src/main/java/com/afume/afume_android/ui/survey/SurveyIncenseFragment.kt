package com.afume.afume_android.ui.survey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.response.PerfumeInfo
import com.afume.afume_android.databinding.FragmentSurveyIncenseBinding

class SurveyIncenseFragment : Fragment() {
    private lateinit var binding: FragmentSurveyIncenseBinding
    private lateinit var incenseAdapter: CircleRecyclerViewAdapter
    private val viewModel: SurveyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSurveyIncenseBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRvIncense()
        viewModel.getSeriesList()
    }

    private fun initRvIncense() {
        incenseAdapter = CircleRecyclerViewAdapter(type = "incense",
            add = { index->viewModel.addSeriesList(index)},remove = {index:Int->viewModel.removeSeriesList(index)})
        binding.rvSurveyIncense.adapter = incenseAdapter
        incenseAdapter.data = mutableListOf(
            PerfumeInfo(
                brandName = "LE LABO",
                name = "아너다 13",
                perfumeIdx = 1,
                imageUrl = R.drawable.dummy_perfume_image,
                isLiked = false
            ), PerfumeInfo(
                brandName = "Jo Malone London",
                name = "블랙베리 앤",
                perfumeIdx = 2,
                imageUrl = R.drawable.img_le_labo_13_sample,
                isLiked = false
            ), PerfumeInfo(
                imageUrl = R.drawable.img_le_labo_13_sample,
                brandName = "Jo Malone London",
                name="라임 앤 바질",
                perfumeIdx = 3,
                isLiked = false
            ), PerfumeInfo(
                imageUrl = R.drawable.dummy_img_diptyque,
                brandName = "diptyque",
                name="도손 오 드 퍼퓸",
                perfumeIdx = 4,
                isLiked = false
            ), PerfumeInfo(
                imageUrl = R.drawable.dummy_perfume_image,
                brandName = "LE LABO",
                name="상탈 33",
                perfumeIdx = 5,
                isLiked = false
            ), PerfumeInfo(
                imageUrl = R.drawable.dummy_img_diptyque,
                brandName="diptyque",
                name="도손 오 드 퍼퓸",
                perfumeIdx = 6,
                isLiked = false
            )
        )
        incenseAdapter.notifyDataSetChanged()


    }

    override fun onResume() {
        super.onResume()
        incenseAdapter.setData(viewModel.perfumeList.value)
    }

}