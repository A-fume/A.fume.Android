package com.afume.afume_android.ui.note

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import com.afume.afume_android.databinding.FragmentKeywordBottomSheetBinding
import com.afume.afume_android.ui.filter.ItemDetailsLookUp
import com.afume.afume_android.ui.filter.ItemKeyProvider
import com.afume.afume_android.util.FlexboxRecyclerViewAdapter
import com.afume.afume_android.util.setBottomHeight
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class KeywordBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding : FragmentKeywordBottomSheetBinding
    private val viewModel : NoteViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentKeywordBottomSheetBinding.inflate(layoutInflater,container,false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        this.setBottomHeight()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRvKeyword(context)
    }

    private fun initRvKeyword(ctx: Context?){
        val flexboxLayoutManager= FlexboxLayoutManager(ctx).apply {
            flexDirection= FlexDirection.ROW
            flexWrap= FlexWrap.WRAP
            alignItems = AlignItems.STRETCH
        }
        val keywordAdapter =
            FlexboxRecyclerViewAdapter(
                { index -> viewModel.addKeywordList(index) },
                { index -> viewModel.removeKeywordList(index) }
            )
        binding.rvNoteKeyword.apply {
            layoutManager=flexboxLayoutManager
            adapter=keywordAdapter
        }

        val keywordSelectionTracker= SelectionTracker.Builder<Long>(
            "note_keyword",
            binding.rvNoteKeyword,
            ItemKeyProvider(binding.rvNoteKeyword),
            ItemDetailsLookUp(
                binding.rvNoteKeyword,
                "flexbox"
            ),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(SelectionPredicates.createSelectAnything()).build()
        keywordAdapter.setSelectionTracker(keywordSelectionTracker)

        keywordAdapter.notifyDataSetChanged()
    }
}