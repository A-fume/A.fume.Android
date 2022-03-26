package com.afume.afume_android.ui.detail.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.afume.afume_android.R
import com.afume.afume_android.databinding.FragmentDetailNoteBinding
import com.afume.afume_android.ui.detail.PerfumeDetailViewModel

class DetailNoteFragment(val perfumeIdx: Int) : Fragment() {

    lateinit var binding: FragmentDetailNoteBinding
    lateinit var noteAdapter: DetailNoteAdapter
    private val viewModel: PerfumeDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_note, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initNoteList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPerfumeInfoWithReview(perfumeIdx)
        viewModel.perfumeDetailWithReviewData.observe(requireActivity(), Observer {
            noteAdapter.replaceAll(ArrayList(it))
            noteAdapter.notifyDataSetChanged()
        })

        viewModel.isValidNoteList.observe(requireActivity(), Observer {
            if(it){
                binding.rvDetailNote.visibility = View.VISIBLE
                binding.txtDetailReviewList.visibility = View.GONE
            }else{
                binding.rvDetailNote.visibility = View.GONE
                binding.txtDetailReviewList.visibility = View.VISIBLE
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getPerfumeInfoWithReview(perfumeIdx)
    }

    private fun initNoteList(){
        noteAdapter = DetailNoteAdapter(requireContext(),viewModel,parentFragmentManager){idx -> viewModel.postReviewLike(idx)}
        binding.rvDetailNote.adapter = noteAdapter

        noteAdapter.notifyDataSetChanged()

    }
}