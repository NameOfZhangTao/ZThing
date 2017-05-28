package com.tao.zthing.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.TypedValue
import com.tao.zthing.R

/**
 * author:zhangtao on 2017/5/23 15:28
 */

var selectableItemBackground: Int = 0

fun Context.getSelectableItemBackground(): Int {
    if (selectableItemBackground == 0) {
        val typedValue = TypedValue()
        theme.resolveAttribute(R.attr.selectableItemBackground, typedValue, true)
        selectableItemBackground = typedValue.resourceId
    }
    return selectableItemBackground
}

fun Context.startActivity(cls: Class<*>) {
    startActivity(Intent(this, cls))
}

fun Activity.startActivityFade(cls: Class<*>) {
    startActivity(cls)
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
}

fun Activity.startActivitySlide(cls: Class<*>) {
    startActivity(cls)
    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
}