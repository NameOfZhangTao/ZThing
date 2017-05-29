package com.tao.zthing.view.adapter

import android.support.v7.util.DiffUtil
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tao.zthing.R
import com.tao.zthing.model.MenuDetails

/**
 * author:zhangtao on 2017/5/26 16:48
 */
class MenuListAdapter(internal var mList: ArrayList<MenuDetails.Details>) : BaseQuickAdapter<MenuDetails.Details, BaseViewHolder>(R.layout.item_menu_list, mList) {

    override fun convert(helper: BaseViewHolder?, item: MenuDetails.Details?) {
        helper?.let {
            with(it) {
                setText(R.id.textName, item?.name)
                setText(R.id.textTitles, item?.ctgTitles)
                Glide.with(itemView?.context)
                        .load(item?.thumbnail)
                        .thumbnail(0.3f)
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
                        .into(getView<ImageView>(R.id.ivThumbnail))
            }
        }
    }

    class MenuDiffCallback(internal val oldList: List<MenuDetails.Details>?, internal val newList: List<MenuDetails.Details>?) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList?.size ?: 0

        override fun getNewListSize(): Int = newList?.size ?: 0

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldList?.get(oldItemPosition)?.menuId.equals(newList?.get(newItemPosition)?.menuId)

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldList?.get(oldItemPosition)?.name.equals(newList?.get(newItemPosition)?.name) &&
                        oldList?.get(oldItemPosition)?.thumbnail.equals(newList?.get(newItemPosition)?.thumbnail)
    }
}