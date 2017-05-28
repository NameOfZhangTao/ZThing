package com.tao.zthing.model

import java.util.*

/**
 * author:zhangtao on 2017/5/24 18:36
 */
class MenuSearch {
    var retCode: Int = 0
    var msg: String? = null
    var result: Result? = null

    class Result {
        var curPage: Int = 0
        /**
         * 总数
         */
        var total: Int = 0
        var list: ArrayList<MenuDetails.Details>? = null
    }
}
