package com.tao.zthing.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.tao.zthing.R
import com.tao.zthing.manager.MenuManager
import com.tao.zthing.view.BaseActivity
import com.tao.zthing.view.adapter.MenuCategoryAdapter
import com.tao.zthing.view.override.DividerGridItemDecoration
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_menu_category.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.toast

class MenuCategoryActivity : BaseActivity() {

    companion object {
        const val MENU_CATEGORY_SELECT = 10
        const val MENU_CATEGORY_RESULT_KEY = "ctgId"
        const val MENU_CATEGORY_RESULT_NAME_KEY = "ctgTitle"
    }

    private var adapter: MenuCategoryAdapter? = null
    private var mList = arrayListOf<MultiItemEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_category)

        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener { finish() }

        swipeRefreshLayout.isRefreshing = true
        refreshData()

        adapter = MenuCategoryAdapter(mList)
        recyclerView.adapter = adapter
        adapter?.expandAll()

        val manager = GridLayoutManager(this, 3)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (adapter?.getItemViewType(position) == MenuCategoryAdapter.MENU_CATEGORY_ITEM) 1 else manager.spanCount
            }
        }
        recyclerView.layoutManager = manager

        adapter?.setOnItemClickListener { adapter, _, position ->
            val item = adapter.getItem(position) as MenuCategoryAdapter.MenuCategory
            if (adapter.getItemViewType(position) == MenuCategoryAdapter.MENU_CATEGORY_ITEM) {
                val intent = Intent()
                intent.putExtra(MENU_CATEGORY_RESULT_KEY, item.ctgId)
                intent.putExtra(MENU_CATEGORY_RESULT_NAME_KEY, item.name)
                setResult(MENU_CATEGORY_SELECT, intent)
                finish()
            } else {
                if (item.isExpanded) {
                    adapter.collapse(position)
                } else {
                    adapter.expand(position)
                }
            }
        }

        recyclerView.addItemDecoration(DividerGridItemDecoration(this))

        swipeRefreshLayout.onRefresh { refreshData() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_menu_category, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_all_category -> {
                setResult(MENU_CATEGORY_SELECT)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun refreshData() {
        MenuManager.menuService?.getMenuCategory()?.let {
            it.compose(bindToLifecycle())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        it.result?.let {
                            val menuCategory = MenuCategoryAdapter.MenuCategory()
                            with(it) {
                                categoryInfo?.let {
                                    menuCategory.ctgId = it.ctgId
                                    menuCategory.name = it.name
                                }
                                childs?.let {
                                    for (child0 in it) {
                                        val menuCategoryGroup = com.tao.zthing.view.adapter.MenuCategoryAdapter.MenuCategoryGroup()
                                        child0.categoryInfo?.let {
                                            kotlin.with(menuCategoryGroup) {
                                                ctgId = it.ctgId
                                                name = it.name
                                                parentId = it.parentId
                                            }
                                        }
                                        child0.childs?.let {
                                            for (child1 in it) {
                                                val menuCategoryItem = com.tao.zthing.view.adapter.MenuCategoryAdapter.MenuCategoryItem()
                                                child1.categoryInfo?.let {
                                                    kotlin.with(menuCategoryItem) {
                                                        ctgId = it.ctgId
                                                        name = it.name
                                                        parentId = it.parentId
                                                    }
                                                }
                                                menuCategoryGroup.addSubItem(menuCategoryItem)
                                            }
                                        }
                                        menuCategory.addSubItem(menuCategoryGroup)
                                    }
                                }
                            }
                            with(mList) {
                                clear()
                                add(menuCategory)
                            }
                            adapter?.notifyDataSetChanged()
                        }
                        if (it.retCode != 200) {
                            toast(it.msg ?: "未知异常")
                        }
                        swipeRefreshLayout.isRefreshing = false
                    }, {
                        it.message?.let {
                            toast(it)
                            swipeRefreshLayout.isRefreshing = false
                        }
                    })
        }
    }
}
