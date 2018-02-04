package com.njp.android.coolweather;

import android.app.Application;

import com.njp.android.coolweather.db.DistrictDao;
import com.njp.android.coolweather.utils.CopyFileUtil;
import com.njp.android.coolweather.utils.ToastUtil;


/**
 * Created by NJP on 2018/2/2.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CopyFileUtil.copy(this,"districts.db");
        DistrictDao.init(this);
        ToastUtil.init(this);
    }
}
