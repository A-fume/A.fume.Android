package com.afume.afume_android.ui.survey

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.RvCircleData
import com.afume.afume_android.databinding.FragmentSurveyIncenseBinding

class SurveyIncenseFragment : Fragment() {
    private lateinit var binding: FragmentSurveyIncenseBinding
    private lateinit var incenseAdapter: CircleRecyclerViewAdapter
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
    }

    private fun initRvIncense() {
        incenseAdapter = CircleRecyclerViewAdapter(type = "incense")
        binding.rvSurveyIncense.adapter = incenseAdapter
        incenseAdapter.data = listOf(
            RvCircleData(
                R.drawable.dummy_example_2,
                "그린"
            ), RvCircleData(
                R.drawable.dummy_example_3,
                "머스키"
            ), RvCircleData(
                R.drawable.dummy_example_1,
                "구르망"
            ), RvCircleData(
                R.drawable.dummy_example_3,
                "머스키"
            ), RvCircleData(
                R.drawable.dummy_example_2,
                "그린"
            ), RvCircleData(
                R.drawable.dummy_example_1,
                "구르망"
            ), RvCircleData(
                R.drawable.dummy_example_2,
                "그린"
            ), RvCircleData(
                R.drawable.dummy_example_3,
                "머스키"
            ), RvCircleData(
                R.drawable.dummy_example_1,
                "구르망"
            ), RvCircleData(
                R.drawable.dummy_example_3,
                "머스키"
            ),
            RvCircleData(
                R.drawable.dummy_example_2,
                "그린"
            ), RvCircleData(
                R.drawable.dummy_example_1,
                "구르망"
            )
        )
        incenseAdapter.notifyDataSetChanged()


    }

}