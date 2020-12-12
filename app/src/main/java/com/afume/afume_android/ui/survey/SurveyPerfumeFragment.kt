package com.afume.afume_android.ui.survey

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.RvCircleData
import com.afume.afume_android.data.vo.RvMyPerfumeData
import com.afume.afume_android.databinding.FragmentSurveyIncenseBinding
import com.afume.afume_android.databinding.FragmentSurveyPerfumeBinding

class SurveyPerfumeFragment : Fragment() {
    private lateinit var binding: FragmentSurveyPerfumeBinding
    private lateinit var surveyPerfumeAdapter: CircleRecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSurveyPerfumeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
    }

    private fun initRv() {
        surveyPerfumeAdapter = CircleRecyclerViewAdapter("perfume")
        binding.rvItemSurveyPerfume.adapter = surveyPerfumeAdapter

        surveyPerfumeAdapter.data = listOf(

            RvCircleData(
                R.drawable.dummy_perfume_image,
                "LE LABO",
                "아너다 13"
            ),
            RvCircleData(
                R.drawable.img_le_labo_13_sample,
                "Jo Malone London",
                "블랙베리 앤"
            ), RvCircleData(
                R.drawable.img_le_labo_13_sample,
                "Jo Malone London",
                "라임 앤 바질"
            ), RvCircleData(
                R.drawable.dummy_img_diptyque,
                "diptyque",
                "도손 오 드 퍼퓸"
            ),
            RvCircleData(
                R.drawable.dummy_perfume_image,
                "LE LABO",
                "상탈 33"
            ), RvCircleData(
                R.drawable.dummy_img_diptyque,
                "diptyque",
                "도손 오 드 퍼퓸"
            ),RvCircleData(
                R.drawable.dummy_perfume_image,
                "LE LABO",
                "아너다 13"
            ),
            RvCircleData(
                R.drawable.img_le_labo_13_sample,
                "Jo Malone London",
                "블랙베리 앤"
            ), RvCircleData(
                R.drawable.img_le_labo_13_sample,
                "Jo Malone London",
                "라임 앤 바질"
            ), RvCircleData(
                R.drawable.dummy_img_diptyque,
                "diptyque",
                "도손 오 드 퍼퓸"
            ),
            RvCircleData(
                R.drawable.dummy_perfume_image,
                "LE LABO",
                "상탈 33"
            ), RvCircleData(
                R.drawable.dummy_img_diptyque,
                "diptyque",
                "도손 오 드 퍼퓸"
            )
        )
        surveyPerfumeAdapter.notifyDataSetChanged()
    }

}