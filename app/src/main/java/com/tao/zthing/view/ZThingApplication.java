package com.tao.zthing.view;

import android.app.Application;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.tao.zthing.manager.MenuManager;

/**
 * author:zhangtao on 2017/5/26 17:18
 */

public class ZThingApplication extends Application {

    public static final RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE);

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        FileUtils.createOrExistsDir(MenuManager.INSTANCE.getCACHE_PATH());
    }
}
