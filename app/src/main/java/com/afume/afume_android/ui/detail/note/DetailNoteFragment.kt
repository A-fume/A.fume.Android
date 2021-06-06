package com.afume.afume_android.ui.detail.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.afume.afume_android.R
import com.afume.afume_android.data.vo.DetailNoteListData
import com.afume.afume_android.databinding.FragmentDetailNoteBinding
import com.afume.afume_android.ui.detail.PerfumeDetailViewModel

class DetailNoteFragment : Fragment() {

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
        viewModel.getPerfumeInfoWithReview(1)
        viewModel.perfumeDetailWithReviewData.observe(requireActivity(), Observer {
            noteAdapter.replaceAll(ArrayList(it))
            noteAdapter.notifyDataSetChanged()
        })
    }

    private fun initNoteList(){
        noteAdapter = DetailNoteAdapter(requireContext())
        binding.rvDetailNote.adapter = noteAdapter

//        noteAdapter.data = mutableListOf(
//            DetailNoteListData(
//                age = "20대 중반",
//                gender = "남자",
//                contents = "남자 분들이 회사 다니면서 \n" +
//                        "뿌리시면 좋을 듯 합니다. \n" +
//                        "친구한테 선물로 줬는데 매우 좋아하네요.",
//                rate = 2f,
//                favorite = 1,
//                like = 234,
//                nickname = "신일"
//            ),
//            DetailNoteListData(
//                age = "20대 중반",
//                gender = "여자",
//                contents = "어퓸에게 선물로 줬는데 \n" +
//                        "다들 잘 쓰네요.",
//                rate = 2.5f,
//                favorite = 0,
//                like = 134,
//                nickname = "다예"
//            ),
//            DetailNoteListData(
//                age = "20대 중반",
//                gender = "남자",
//                contents = "남자 분들이 회사 다니면서 \n" +
//                        "뿌리시면 좋을 듯 합니다. \n" +
//                        "친구한테 선물로 줬는데 매우 좋아하네요.",
//                rate = 2f,
//                favorite = 1,
//                like = 234,
//                nickname = "신일"
//            ),
//            DetailNoteListData(
//                age = "20대 중반",
//                gender = "여자",
//                contents = "어퓸에게 선물로 줬는데 \n" +
//                        "다들 잘 쓰네요.",
//                rate = 2.5f,
//                favorite = 0,
//                like = 134,
//                nickname = "다예"
//            )
//        )
//        noteAdapter.notifyDataSetChanged()

    }
}