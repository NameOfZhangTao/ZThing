package com.tao.zthing.view.activity

import android.app.Activity
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import com.tao.zthing.R
import com.tao.zthing.model.MenuDetails
import com.tao.zthing.model.MenuDetails.Details
import com.tao.zthing.model.MenuDetails.Details.Recipe.RecipeMethod
import kotlinx.android.synthetic.main.activity_menu_details.*
import org.jetbrains.anko.*

class MenuDetailsActivity : AppCompatActivity() {
    companion object {
        private var menuDetails: MenuDetails.Details? = null
        fun startActivity(activity: Activity, details: Details) {
            menuDetails = details
            activity.startActivity<MenuDetailsActivity>()
        }
    }

    private var adapter: MenuMethodAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_details)
        setSupportActionBar(toolbar)

        toolbarLayout.title = menuDetails?.recipe?.title
        toolbar.setNavigationOnClickListener { finish() }

        Glide.with(this).load(menuDetails?.recipe?.img).into(ivMenuTitle)

        with(fabMenuCollect) {
            setOnClickListener {
                setImageResource(R.drawable.ic_favorite_black_24dp)
            }
        }

        recyclerViewMenuRecipe.layoutManager = LinearLayoutManager(this)

        val mList = Gson().fromJson<ArrayList<RecipeMethod>>(menuDetails?.recipe?.method, object : TypeToken<List<RecipeMethod>>() {}.type)
        adapter = MenuMethodAdapter(mList)
        recyclerViewMenuRecipe.adapter = adapter
        adapter?.setOnItemClickListener { adapter, view, position -> toast("click") }
        adapter?.addHeaderView(with(ctx) {
            linearLayout {
                orientation = LinearLayout.VERTICAL
                horizontalPadding = dip(10)
                topPadding = dip(20)
                textView(menuDetails?.recipe?.sumary) { topPadding = dip(10) }
                val ingredients = Gson().fromJson<ArrayList<String>>(menuDetails?.recipe?.ingredients
                        , object : TypeToken<List<String>>() {}.type)
                textView(ingredients?.joinToString("\n")) {
                    topPadding = dip(10)
                    textColor = 0x26A599.opaque
                }
                textView("步骤") {
                    topPadding = dip(10)
                    setTypeface(typeface, Typeface.BOLD)
                    textSize = 25f
                }
            }
        })
    }

    private class MenuMethodAdapter(mList: ArrayList<RecipeMethod>?) : BaseQuickAdapter<RecipeMethod, BaseViewHolder>
    (R.layout.item_menu_method, mList) {
        override fun convert(helper: BaseViewHolder?, item: RecipeMethod?) {
            helper?.setText(R.id.textMethod, item?.step)
            Glide.with(helper?.itemView?.context)
                    .load(item?.img)
                    .thumbnail(0.3f)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
                    .into(helper?.getView<ImageView>(R.id.ivMethod))
        }
    }
}
