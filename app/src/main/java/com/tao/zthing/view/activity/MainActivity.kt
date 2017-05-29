package com.tao.zthing.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.FragmentUtils.addFragment
import com.blankj.utilcode.util.FragmentUtils.getTopShowFragment
import com.tao.zthing.R
import com.tao.zthing.view.fragment.MainHomeFragment
import com.tao.zthing.view.fragment.MainMenuFragment
import com.tao.zthing.view.fragment.MainMineFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    private var homeFragment: MainHomeFragment? = null
    private var menuFragment: MainMenuFragment? = null
    private var mineFragment: MainMineFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbarMain)
        savedInstanceState ?: addFragment(supportFragmentManager, MainHomeFragment(), R.id.flContent)
        navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                navigation.selectedItemId -> return@setOnNavigationItemSelectedListener true

                R.id.navigation_home -> {
                    toolbarMain.title = getString(R.string.title_home)
                    homeFragment = homeFragment ?: FragmentUtils.findFragment(supportFragmentManager, MainHomeFragment::class.java) as MainHomeFragment?
                    homeFragment = homeFragment ?: addFragment(supportFragmentManager, MainHomeFragment(), R.id.flContent) as MainHomeFragment?
                    homeFragment?.let { FragmentUtils.hideShowFragment(getTopShowFragment(supportFragmentManager), it) }
                }

                R.id.navigation_menu -> {
                    toolbarMain.title = getString(R.string.title_menu)
                    menuFragment = menuFragment ?: FragmentUtils.findFragment(supportFragmentManager, MainMenuFragment::class.java) as MainMenuFragment?
                    menuFragment = menuFragment ?: addFragment(supportFragmentManager, MainMenuFragment(), R.id.flContent) as MainMenuFragment?
                    menuFragment?.let { FragmentUtils.hideShowFragment(getTopShowFragment(supportFragmentManager), it) }
                }

                R.id.navigation_mine -> {
                    toolbarMain.title = getString(R.string.title_mine)
                    mineFragment = mineFragment ?: FragmentUtils.findFragment(supportFragmentManager, MainMineFragment::class.java) as MainMineFragment?
                    mineFragment = mineFragment ?: addFragment(supportFragmentManager, MainMineFragment(), R.id.flContent) as MainMineFragment?
                    mineFragment?.let { FragmentUtils.hideShowFragment(getTopShowFragment(supportFragmentManager), it) }
                }

                else -> return@setOnNavigationItemSelectedListener false
            }
            true
        }
    }

    private var currentTimeMillis: Long = 0
    override fun onBackPressed() {
        if (System.currentTimeMillis() - currentTimeMillis < 1000) {
            super.onBackPressed()
        } else {
            toast("再按就退出咯···")
            currentTimeMillis = System.currentTimeMillis()
        }
    }
}
