package com.tao.zthing.model;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.tao.zthing.model.MenuDetails.Details.Recipe.RecipeMethod;

/**
 * Created by ZT on 2017/5/29.
 */

public class Test {
    Test() {
        ArrayList<MenuDetails.Details.Recipe.RecipeMethod> list =
                new Gson().fromJson("", new TypeToken<List<MenuDetails.Details.Recipe.RecipeMethod>>() {
                }.getType());
    }

    private ArrayList<RecipeMethod> list = null;

    private BaseQuickAdapter adapter = new BaseQuickAdapter<RecipeMethod, BaseViewHolder>(0, list) {

        @Override
        protected void convert(BaseViewHolder helper, RecipeMethod item) {

        }
    };
}
