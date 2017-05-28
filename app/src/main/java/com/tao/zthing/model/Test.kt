package com.tao.zthing.model

import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tao.zthing.R

/**
 * author:zhangtao on 2017/5/26 16:54
 */

class Test internal constructor(string: String) : BaseQuickAdapter<MenuDetails.Details, BaseViewHolder>(R.layout.item_menu_list, null) {

    init {
        LogUtils.e(string)
    }


    override fun convert(helper: BaseViewHolder, item: MenuDetails.Details) {

    }
}
