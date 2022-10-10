package com.scentsnote.android.ui.my

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.scentsnote.android.ScentsNoteApplication
import com.scentsnote.android.R
import com.scentsnote.android.databinding.FragmentMypageBinding
import com.scentsnote.android.ui.MainActivity
import com.scentsnote.android.ui.filter.ScentsNoteViewPagerAdapter
import com.scentsnote.android.ui.my.myperfume.MyPerfumeFragment
import com.scentsnote.android.ui.my.wishlist.WishListFragment
import com.scentsnote.android.ui.setting.EditMyInfoActivity
import com.scentsnote.android.ui.setting.EditPasswordActivity
import com.scentsnote.android.ui.signin.SignHomeActivity
import com.scentsnote.android.util.extension.TabSelectedListener
import com.scentsnote.android.util.extension.changeTabsFont
import com.scentsnote.android.util.refreshFragment
import com.scentsnote.android.util.toastLong

class MyFragment : Fragment() {
    private lateinit var binding: FragmentMypageBinding
    private lateinit var myPagePagerAdapter: ScentsNoteViewPagerAdapter
    private lateinit var callback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
      return initBinding(layoutInflater,container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initVp()
        setUpTabWithViewPager()
        setNavigation()
    }

    override fun onResume() {
        super.onResume()
        binding.drawerLayout.closeDrawers()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(binding.drawerLayout.isDrawerOpen(GravityCompat.END)){
                    binding.drawerLayout.closeDrawers()
                }else{
                    // 백스택 구현
                    super.remove()
                    (activity as MainActivity).onBackPressed()
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    private fun initBinding( inflater: LayoutInflater,container: ViewGroup?):View {
        binding = FragmentMypageBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun initToolbar() {

        binding.toolbarMypage.toolbartxt = "마이"
        //바궈야함
        binding.toolbarMypage.toolbar = R.drawable.icon_btn_sidebar

    }

    private fun initVp() {
        myPagePagerAdapter = ScentsNoteViewPagerAdapter(childFragmentManager)
        myPagePagerAdapter.fragments = listOf(
            MyPerfumeFragment(),
            WishListFragment()
        )
        binding.vpMypage.adapter = myPagePagerAdapter
    }

    private fun setUpTabWithViewPager() {
        binding.tabMypage.setupWithViewPager(binding.vpMypage)
        binding.tabMypage.apply {
            getTabAt(0)?.text = "마이 퍼퓸"
            getTabAt(1)?.text = "위시 리스트"
        }
        binding.tabMypage.addOnTabSelectedListener(TabSelectedListener(binding.tabMypage))
        binding.tabMypage.changeTabsFont(0)
    }

    private fun setNavigation() {
        binding.toolbarMypage.toolbarBtn.setOnClickListener {
            binding.drawerLayout.openDrawer(binding.myNavigationDrawer)
        }

        inflateMenu()
        goToLoginOrEditInfo()
        clickBtnCancel()
    }

    private fun inflateMenu() {
        if (ScentsNoteApplication.prefManager.haveToken()) binding.myNavigationDrawer.inflateMenu(R.menu.navigation_drawer)
        else {
            binding.myNavigationDrawer.menu.removeGroup(R.id.group_my_info)
            binding.myNavigationDrawer.menu.removeGroup(R.id.group_password)
            binding.myNavigationDrawer.menu.removeGroup(R.id.group_feedback)
            binding.myNavigationDrawer.menu.removeGroup(R.id.group_logout)
            binding.myNavigationDrawer.menu.removeGroup(R.id.group_withdrawal)
            binding.myNavigationDrawer.menu.removeGroup(R.id.group_divider)

            binding.myNavigationDrawer.inflateMenu(R.menu.navigation_drawer_login)
            binding.myNavigationDrawer.menu.getItem(0).setActionView(R.layout.menu_item_login)
        }

    }

    private fun clickBtnCancel(){
        binding.myNavigationDrawer.getHeaderView(0).findViewById<ImageView>(R.id.btn_cancle)
            .setOnClickListener {
                binding.drawerLayout.closeDrawers()
            }

    }

    private fun goToLoginOrEditInfo(){
        binding.myNavigationDrawer.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            when (menuItem.itemId) {
                R.id.go_to_login -> intent(SignHomeActivity::class.java)
                R.id.edit_my_info -> intent(EditMyInfoActivity::class.java)
                R.id.edit_password -> intent(EditPasswordActivity::class.java)
                R.id.logout -> {
                    ScentsNoteApplication.prefManager.clear()
                    requireContext().toastLong("로그아웃되었습니다.")
                    inflateMenu()
                    refreshFragment(myPagePagerAdapter.fragments[0],childFragmentManager)
                    refreshFragment(myPagePagerAdapter.fragments[1],childFragmentManager)
                    binding.drawerLayout.closeDrawers()
                }
                else->{
                    val intent = Intent(requireContext(), MyInquiryActivity::class.java)
                    if(menuItem.itemId == R.id.feedback){
                        intent.putExtra("url", "feedback")
                    }else{
                        intent.putExtra("url", "withdrawal")
                    }
                    startActivity(intent)
                }
            }
            true
        }
    }

    fun intent(activity: Class<*>) {
        val intent = Intent(requireContext(), activity)
        startActivity(intent)
    }
}