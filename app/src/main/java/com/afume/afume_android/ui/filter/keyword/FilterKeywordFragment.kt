package com.afume.afume_android.ui.filter.keyword

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import com.afume.afume_android.databinding.FragmentFilterKeywordBinding
import com.afume.afume_android.ui.filter.FilterViewModel
import com.afume.afume_android.ui.filter.FilterViewModelFactory
import com.afume.afume_android.ui.filter.ItemDetailsLookUp
import com.afume.afume_android.ui.filter.ItemKeyProvider
import com.afume.afume_android.util.FlexboxRecyclerViewAdapter
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

class FilterKeywordFragment : Fragment() {
    private lateinit var binding: FragmentFilterKeywordBinding
    private val viewModel: FilterViewModel by activityViewModels(){
        FilterViewModelFactory.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initBinding(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initKeywordRv(context)
        observeBlockClickMoreThan5()
    }

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?):View{
        binding = FragmentFilterKeywordBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }

    private fun initKeywordRv(ctx: Context?){
        val flexboxLayoutManager= FlexboxLayoutManager(ctx).apply {
            flexDirection= FlexDirection.ROW
            flexWrap= FlexWrap.WRAP
            alignItems = AlignItems.STRETCH
        }

        val keywordAdapter = FlexboxRecyclerViewAdapter(
                { index,add -> viewModel.addKeywordList(index,add) },
                { index,add -> viewModel.countBadges(index,add) }
            )

        binding.rvKeyword.apply {
            layoutManager=flexboxLayoutManager
            adapter=keywordAdapter
        }

        val keywordSelectionTracker= SelectionTracker.Builder<Long>(
            "incense",
            binding.rvKeyword,
            ItemKeyProvider(binding.rvKeyword),
            ItemDetailsLookUp(
                binding.rvKeyword,
                "flexbox"
            ),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(SelectionPredicates.createSelectAnything()).build()
        keywordAdapter.setSelectionTracker(keywordSelectionTracker)
    }

    fun observeBlockClickMoreThan5(){
        viewModel.badgeCount.observe(viewLifecycleOwner, Observer {
                viewModel.blockClickKeywordMoreThan5()
            })
    }

}