package com.scentsnote.android.ui.note

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.scentsnote.android.databinding.FragmentKeywordBottomSheetBinding
import com.scentsnote.android.utils.NoteKeywordAdapter
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.scentsnote.android.viewmodel.note.NoteViewModel
import com.scentsnote.android.utils.extension.setOnSafeClickListener

class KeywordBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentKeywordBottomSheetBinding
    private val viewModel: NoteViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentKeywordBottomSheetBinding.inflate(layoutInflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.btnNoteKeywordCancel.setOnSafeClickListener {
            dismiss()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRvKeyword(context)
    }

    private fun initRvKeyword(ctx: Context?) {
        val flexboxLayoutManager = FlexboxLayoutManager(ctx).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            alignItems = AlignItems.STRETCH
        }

        val keywordAdapter =
            NoteKeywordAdapter(0) { index, add -> viewModel.addKeywordList(index, add) }

        binding.rvNoteKeyword.apply {
            layoutManager = flexboxLayoutManager
            adapter = keywordAdapter
        }

        keywordAdapter.notifyDataSetChanged()
    }
}