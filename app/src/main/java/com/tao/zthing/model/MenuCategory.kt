package com.tao.zthing.model

/**
 * author:zhangtao on 2017/5/25 09:46
 */
class MenuCategory {
    var retCode: Int = 0
    var msg: String? = null
    var result: Result? = null

    open class Result {
        var categoryInfo: CategoryInfo? = null
        var childs: ArrayList<Childs>? = null

        class CategoryInfo {
            var ctgId: String? = null
            var name: String? = null

            var parentId: String? = null
        }

        class Childs {
            var categoryInfo: CategoryInfo? = null
            var childs: ArrayList<Childses>? = null

            class Childses {
                var categoryInfo: CategoryInfo? = null
            }
        }

    }
}