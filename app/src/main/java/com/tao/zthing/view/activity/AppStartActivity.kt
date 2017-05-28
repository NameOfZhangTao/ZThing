package com.tao.zthing.view.activity

import android.os.Bundle
import android.widget.ImageView
import com.tao.zthing.R
import com.tao.zthing.view.BaseActivity
import com.tao.zthing.view.startActivityFade
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.imageView
import org.jetbrains.anko.matchParent
import java.util.concurrent.TimeUnit

class AppStartActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        frameLayout {
            imageView(R.mipmap.ic_launcher_round) {
                scaleType = ImageView.ScaleType.CENTER
            }.lparams {
                width = matchParent
                height = matchParent
            }
        }
        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    startActivityFade(MainActivity::class.java)
                    finish()
                })
    }
}