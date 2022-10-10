package com.scentsnote.android.ui.detail.note

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.scentsnote.android.R
import com.scentsnote.android.databinding.FragmentDetailNoteBinding
import com.scentsnote.android.ui.detail.PerfumeDetailViewModel
import com.scentsnote.android.util.view.BaseFragment

class DetailNoteFragment(val perfumeIdx: Int) : BaseFragment<FragmentDetailNoteBinding>(R.layout.fragment_detail_note){

    lateinit var noteAdapter: DetailNoteAdapter
    private val detailViewModel: PerfumeDetailViewModel by activityViewModels()

    override fun onBindData() {
        super.onBindData()

        binding.apply {
            viewModel = detailViewModel
        }
    }

    override fun onResume() {
        super.onResume()

        detailViewModel.getPerfumeInfoWithReview(perfumeIdx)
    }

    override fun initView() {
        super.initView()

        initNoteList()
        detailViewModel.getPerfumeInfoWithReview(perfumeIdx)
    }

    override fun initObserver() {
        super.initObserver()

        detailViewModel.perfumeDetailWithReviewData.observe(requireActivity(), Observer {
            noteAdapter.replaceAll(ArrayList(it))
            noteAdapter.notifyDataSetChanged()
        })

        detailViewModel.isValidNoteList.observe(requireActivity(), Observer {
            if(it){
                binding.rvDetailNote.visibility = View.VISIBLE
                binding.txtDetailReviewList.visibility = View.GONE
            }else{
                binding.rvDetailNote.visibility = View.GONE
                binding.txtDetailReviewList.visibility = View.VISIBLE
            }
        })
    }

    private fun initNoteList(){
        noteAdapter = DetailNoteAdapter(requireContext(),detailViewModel,parentFragmentManager,perfumeIdx){ idx -> detailViewModel.postReviewLike(idx)}
        binding.rvDetailNote.adapter = noteAdapter

        noteAdapter.notifyDataSetChanged()

    }
}