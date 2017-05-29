package com.tao.zthing.model

import java.util.*

/**
 * author:zhangtao on 2017/5/25 10:06
 */
class MenuDetails {

    var retCode: Int = 0
    var msg: String? = null
    var result: Details? = null

    class Details {
        var ctgIds: ArrayList<String>? = null
        var ctgTitles: String? = null
        var menuId: String? = null
        var name: String? = null
        var thumbnail: String? = null
        /**
         * 食谱
         */
        var recipe: Recipe? = null

        class Recipe {
            var img: String? = null
            var ingredients: String? = null
            var method: String? = null
            /**
             * 综述
             */
            var sumary: String? = null
            var title: String? = null

            class RecipeMethod {
                var img: String? = null
                var step: String? = null
            }
        }
    }
}