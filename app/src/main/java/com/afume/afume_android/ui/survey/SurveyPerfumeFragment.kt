package com.afume.afume_android.ui.survey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.response.PerfumeInfo
import com.afume.afume_android.databinding.FragmentSurveyPerfumeBinding

class SurveyPerfumeFragment : Fragment() {
    private lateinit var binding: FragmentSurveyPerfumeBinding
    private lateinit var surveyPerfumeAdapter: CircleRecyclerViewAdapter
    private val viewModel: SurveyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSurveyPerfumeBinding.inflate(layoutInflater, container, false)
        binding.vm=viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
//        observeList()
    }

    private fun initRv() {
        surveyPerfumeAdapter = CircleRecyclerViewAdapter(0, add = { index->viewModel.addPerfumeList(index)},remove = {index:Int->viewModel.removePerfumeList(index)})
        binding.rvItemSurveyPerfume.adapter = surveyPerfumeAdapter

        surveyPerfumeAdapter.perfumeData = mutableListOf(
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
        surveyPerfumeAdapter.notifyDataSetChanged()
    }
//
//    fun observeList(){
//        viewModel.perfumeList.observe( viewLifecycleOwner, Observer {
//            list ->
//            Log.e("observe",list.toString())
//            viewModel.setPerfumeList(list)
//        })
//    }

    override fun onResume() {
        super.onResume()
        surveyPerfumeAdapter.notifyDataSetChanged()
    }

}