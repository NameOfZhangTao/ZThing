package com.tao.zthing.view.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.EditText
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.tao.zthing.R
import com.tao.zthing.manager.MenuManager
import com.tao.zthing.model.MenuDetails
import com.tao.zthing.view.BaseFragment
import com.tao.zthing.view.activity.MenuCategoryActivity
import com.tao.zthing.view.adapter.MenuListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_main_menu.*
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivityForResult
import org.jetbrains.anko.support.v4.toast

/**
 * author:zhangtao on 2017/5/23 16:04
 */
class MainMenuFragment : BaseFragment() {
    companion object {
        const internal val size = 20
    }

    internal var spUtils = SPUtils(MenuCategoryActivity.toString())

    internal var ctgid: String? = null
    internal var name: String? = null
    internal var page = 1

    internal var adapter = MenuListAdapter(arrayListOf<MenuDetails.Details>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        ctgid = spUtils.getString(MenuCategoryActivity.MENU_CATEGORY_RESULT_KEY)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater?.inflate(R.layout.fragment_main_menu, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewMenu.adapter = adapter
        recyclerViewMenu.layoutManager = LinearLayoutManager(activity)
        refreshMenuList.onRefresh {
            initData()
            onLoad()
        }
        with(adapter) {
            setOnLoadMoreListener({ onLoad() }, recyclerViewMenu)
            isFirstOnly(false)
            openLoadAnimation(BaseQuickAdapter.SCALEIN)
        }
        onLoading()
    }

    internal fun onLoading() {
        refreshMenuList.isRefreshing = true
        onLoad()
    }

    internal fun initData() {
        adapter.mList.clear()
        page = 1
    }

    internal fun onLoad() {
        LogUtils.e("$ctgid-size:${adapter.mList.size}")
        MenuManager.menuService?.getMenuSearch(ctgid, name, page, size)?.let {
            it.compose(bindToLifecycle())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        val total = it.result?.total ?: 0
                        it.result?.list?.let {
                            with(adapter) {
                                val size = mList.size
                                mList.addAll(it)
                                if (mList.size == total) loadMoreEnd()
                                else loadMoreComplete()
                                if (page == 1) {
                                    notifyItemRangeChanged(0, mList.size)
                                    notifyItemRangeRemoved(mList.size, size)
                                } else notifyItemRangeInserted(size, mList.size - size)
                            }
                        }
                        if (it.retCode != 200) {
                            toast(it.msg ?: "未知异常")
                            adapter.loadMoreFail()
                        } else page++
                        refreshMenuList.isRefreshing = false
                    }, {
                        it.message?.let { toast("网络异常") }
                        refreshMenuList.isRefreshing = false
                        adapter.loadMoreFail()
                    })
        }
    }

    internal var editText: EditText? = null

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_search -> {
                alert("请输入搜索内容") {
                    customView {
                        frameLayout {
                            cardView {
                                cardElevation = 2f
                                useCompatPadding = true
                                editText = editText {
                                    hint = "菜谱名称"
                                    padding = dip(10)
                                    textSize = 16f
                                    backgroundColor = 0xff.gray.opaque
                                    textColor = 0x64.gray.opaque
                                    hintTextColor = 0x88.gray.opaque
                                }
                            }.lparams(width = matchParent) {
                                horizontalMargin = dip(10)
                                topMargin = dip(10)
                            }
                        }
                    }
                    yesButton {
                        initData()
                        name = editText?.text?.toString()
                        onLoad()
                    }
                    noButton {}
                }.show()
                return true
            }
            R.id.menu_category -> {
                startActivityForResult<MenuCategoryActivity>(MenuCategoryActivity.MENU_CATEGORY_SELECT)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_main, menu)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MenuCategoryActivity.MENU_CATEGORY_SELECT && resultCode == MenuCategoryActivity.MENU_CATEGORY_SELECT) {
            initData()
            ctgid = data?.getStringExtra(MenuCategoryActivity.MENU_CATEGORY_RESULT_KEY)
            onLoading()
            spUtils.put(MenuCategoryActivity.MENU_CATEGORY_RESULT_KEY, ctgid)
        }
    }
}