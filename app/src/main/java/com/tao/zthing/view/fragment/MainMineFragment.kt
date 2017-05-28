package com.tao.zthing.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.tao.zthing.R
import com.tao.zthing.view.BaseFragment
import com.tao.zthing.view.getSelectableItemBackground
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.ctx

/**
 * author:zhangtao on 2017/5/23 15:47
 */
class MainMineFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return with(ctx) {
            verticalLayout {
                val ID_UPDATE = 1
                val ID_ABOUT = 2

                verticalLayout {
                    textView("检查更新") {
                        id = ID_UPDATE
                        setOnClickListener { toast("暂无更新！") }
                    }

                    view { backgroundColor = 0xbb.gray.opaque }.lparams(width = matchParent, height = dip(0.5f)) { leftMargin = dip(50) }

                    textView("关于我们") {
                        id = ID_ABOUT
                        setOnClickListener { toast("暂无！") }
                    }

                    view { backgroundColor = 0xbb.gray.opaque }.lparams(width = matchParent, height = dip(0.5f)) { leftMargin = dip(50) }

                }.lparams(weight = 1f, width = matchParent).applyRecursively { view ->
                    when (view) {
                        is TextView -> {
                            with(view) {
                                padding = dip(15)
                                compoundDrawablePadding = dip(10)
                                gravity = Gravity.CENTER_VERTICAL
                                textColor = 0x64.gray.opaque
                                backgroundResource = getSelectableItemBackground()
                                var leftDrawableResId: Int = 0
                                when (id) {
                                    ID_UPDATE -> leftDrawableResId = R.drawable.ic_app_update
                                    ID_ABOUT -> leftDrawableResId = R.drawable.ic_app_about
                                }
                                setCompoundDrawablesWithIntrinsicBounds(leftDrawableResId, 0, R.drawable.ic_keyboard_arrow_right_black_24dp, 0)
                            }
                        }
                    }
                }

                include<Button>(R.layout.button) {
                    text = "退出"
                    textSize = 16f
                    textColor = Color.RED
                    verticalPadding = dip(20)
                    setOnClickListener {
                        alert("是否退出？", "提示") {
                            yesButton { activity.finish() }
                            noButton {}
                        }.show()
                    }
                }.lparams(width = matchParent) {
                    margin = dip(10)
                }
            }
        }
    }
}