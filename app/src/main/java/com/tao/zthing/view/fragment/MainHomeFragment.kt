package com.tao.zthing.view.fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tao.zthing.view.BaseFragment
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.textView

/**
 * author:zhangtao on 2017/5/23 15:33
 */
class MainHomeFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return with(ctx) {
            frameLayout {
                textView("首页正在紧张建设中···").lparams { gravity = Gravity.CENTER }
            }
        }
    }
}