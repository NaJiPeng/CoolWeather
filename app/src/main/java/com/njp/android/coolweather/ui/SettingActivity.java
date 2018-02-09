package com.njp.android.coolweather.ui;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.njp.android.coolweather.R;
import com.njp.android.coolweather.utils.ToastUtil;

public class SettingActivity extends AppCompatActivity {

    private ViewGroup mViewGroup;

    private Toolbar mToolbar;

    private Switch mSwLocation;

    private Switch mSwUpdate;

    private RelativeLayout mRlUpdateTime;

    private TextView mTvUpdateTime;

    private RelativeLayout mRlUpdate;

    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
        initEvent();
    }

    private void initEvent() {

        mSwLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mPreferences.edit().putBoolean("isLocation", isChecked).apply();
            }
        });

        mRlUpdateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopMenu();
            }
        });

        mRlUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("暂无更新版本");
            }
        });
    }

    private void showPopMenu() {

        View view = LayoutInflater.from(this).inflate(R.layout.popmenu, null);
        ListView listView = view.findViewById(R.id.lv_time);
        final String[] times = {
                "1小时", "2小时", "3小时", "4小时", "5小时", "6小时"
        };
        listView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, times));
        final PopupWindow popupWindow = new PopupWindow(mViewGroup,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        popupWindow.setAnimationStyle(R.style.popup_window_anim);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
        int[] location = new int[2];
        mViewGroup.getLocationOnScreen(location);
        popupWindow.showAtLocation(mViewGroup,
                Gravity.LEFT | Gravity.BOTTOM, 0, -location[1]);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mTvUpdateTime.setText(times[position]);
                popupWindow.dismiss();
            }
        });

    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mSwLocation = findViewById(R.id.sw_location);
        mSwUpdate = findViewById(R.id.sw_update);
        mTvUpdateTime = findViewById(R.id.tv_update_time);
        mRlUpdateTime = findViewById(R.id.rl_update_time);
        mRlUpdate = findViewById(R.id.rl_update);
        mViewGroup = findViewById(R.id.view_group);

        mPreferences = getSharedPreferences("settings", MODE_PRIVATE);

        mSwLocation.setChecked(mPreferences.getBoolean("isLocation", true));

        mToolbar.setTitle("设置");
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
