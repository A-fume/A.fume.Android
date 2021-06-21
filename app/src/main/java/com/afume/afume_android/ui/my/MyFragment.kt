package com.afume.afume_android.ui.my

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.afume.afume_android.AfumeApplication
import com.afume.afume_android.R
import com.afume.afume_android.databinding.FragmentMypageBinding
import com.afume.afume_android.ui.filter.AfumeViewPagerAdapter
import com.afume.afume_android.ui.my.myperfume.MyPerfumeFragment
import com.afume.afume_android.ui.my.wishlist.WishListFragment
import com.afume.afume_android.ui.setting.EditMyInfoActivity
import com.afume.afume_android.ui.setting.EditPasswordActivity
import com.afume.afume_android.ui.signin.SignInActivity
import com.afume.afume_android.util.TabSelectedListener
import com.afume.afume_android.util.toastLong

class MyFragment : Fragment() {
    private lateinit var binding: FragmentMypageBinding
    private lateinit var myPagePagerAdapter: AfumeViewPagerAdapter
    private val myViewModel: MyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBind()
        initVp()
        setUpTabWithViewPager()
        setNavigation()
    }

    override fun onResume() {
        super.onResume()
        binding.drawerLayout.closeDrawers()

    }

    private fun initBind() {

        binding.toolbarMypage.toolbartxt = "마이"
        //바궈야함
        binding.toolbarMypage.toolbar = R.drawable.icon_btn_sidebar

    }

    private fun initVp() {
        myPagePagerAdapter = AfumeViewPagerAdapter(childFragmentManager)
        myPagePagerAdapter.fragments = listOf(
            MyPerfumeFragment(),
            WishListFragment()
        )
        binding.vpMypage.adapter = myPagePagerAdapter

    }

    private fun setUpTabWithViewPager() {
        binding.tabMypage.setupWithViewPager(binding.vpMypage)
        binding.tabMypage.apply {
            getTabAt(0)?.text = "마이"
            getTabAt(1)?.text = "위시 리스트"
        }
        binding.tabMypage.addOnTabSelectedListener(TabSelectedListener(binding.tabMypage))
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
        if (AfumeApplication.prefManager.haveToken()) binding.myNavigationDrawer.inflateMenu(R.menu.navigation_drawer)
        else {
            val textView = TextView(context)
            textView.text = "                        "
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_btn_up, 0, 0, 0)

            binding.myNavigationDrawer.inflateMenu(R.menu.navigation_drawer_login)
            binding.myNavigationDrawer.menu.getItem(0).actionView = textView
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
                R.id.go_to_login -> intent(SignInActivity::class.java)
                R.id.edit_my_info -> intent(EditMyInfoActivity::class.java)
                R.id.edit_password -> intent(EditPasswordActivity::class.java)
                R.id.logout -> {
                    AfumeApplication.prefManager.clear()
                    requireContext().toastLong("로그아웃되었습니다.")
                    binding.drawerLayout.closeDrawers()
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