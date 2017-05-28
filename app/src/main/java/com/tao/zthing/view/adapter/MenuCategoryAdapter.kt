package com.tao.zthing.view.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.AbstractExpandableItem
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.tao.zthing.R

/**
 * author:zhangtao on 2017/5/25 15:32
 */


class MenuCategoryAdapter(data: List<MultiItemEntity>) : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {

    init {
        addItemType(MENU_CATEGORY, R.layout.item_menu_category)
        addItemType(MENU_CATEGORY_GROUP, R.layout.item_menu_category_group)
        addItemType(MENU_CATEGORY_ITEM, R.layout.item_menu_category_item)
    }

    override fun convert(holder: BaseViewHolder, item: MultiItemEntity) {
        holder.setText(R.id.text_category, (item as MenuCategory).name)
        if (holder.itemViewType != MENU_CATEGORY_ITEM) {
            holder.setImageResource(R.id.iv_group_state, if (item.isExpanded)
                R.drawable.ic_expand_more_black_24dp else R.drawable.ic_keyboard_arrow_right_black_24dp)
        }
    }

    open class MenuCategory : AbstractExpandableItem<MenuCategoryGroup>(), MultiItemEntity {
        var ctgId: String? = null
        var name: String? = null
        override fun getLevel(): Int = 0
        override fun getItemType(): Int = MENU_CATEGORY
    }

    open class MenuCategoryGroup : MenuCategory() {
        var parentId: String? = null
        override fun getLevel(): Int = 1
        override fun getItemType(): Int = MENU_CATEGORY_GROUP
    }

    class MenuCategoryItem : MenuCategoryGroup() {
        override fun getItemType(): Int = MENU_CATEGORY_ITEM
    }

    companion object {
        val MENU_CATEGORY = 0
        val MENU_CATEGORY_GROUP = 1
        val MENU_CATEGORY_ITEM = 2
    }
}
