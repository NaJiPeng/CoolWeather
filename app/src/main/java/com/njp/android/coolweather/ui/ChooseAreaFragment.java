package com.njp.android.coolweather.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.njp.android.coolweather.R;
import com.njp.android.coolweather.db.DistrictDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NJP on 2018/2/4.
 */

public class ChooseAreaFragment extends Fragment {

    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_DISTRICT = 2;

    private String province;
    private Toolbar mToolbar;
    private ListView mListView;
    private ArrayAdapter<String> mAdapter;
    private List<String> dataList = new ArrayList<>();

    private int currentLevel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area, container, false);

        mToolbar = view.findViewById(R.id.toolbar);
        mListView = view.findViewById(R.id.list_view);

        mAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, dataList);
        mListView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (currentLevel) {
                    case LEVEL_PROVINCE:
                        queryCity(dataList.get(position));
                        break;
                    case LEVEL_CITY:
                        queryDistrict(dataList.get(position));
                        break;
                    case LEVEL_DISTRICT:
                        //TODO
                        break;
                    default:
                        break;

                }
            }
        });

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (currentLevel) {
                    case LEVEL_DISTRICT:
                        queryCity(province);
                        break;
                    case LEVEL_CITY:
                        queryProvince();
                    case LEVEL_PROVINCE:
                        //nullZ
                        break;
                    default:
                        break;
                }
            }
        });

        queryProvince();

    }

    private void queryProvince() {
        currentLevel = LEVEL_PROVINCE;
        mToolbar.setTitle("中国");
        mToolbar.setNavigationIcon(null);
        dataList.clear();
        List<String> list = DistrictDao.queryProvince();
        dataList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    private void queryCity(String province) {
        currentLevel = LEVEL_CITY;
        this.province = province;
        mToolbar.setTitle(province);
        mToolbar.setNavigationIcon(R.drawable.back);
        dataList.clear();
        List<String> list = DistrictDao.queryCity(province);
        dataList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    private void queryDistrict(String city) {
        currentLevel = LEVEL_DISTRICT;
        mToolbar.setTitle(city);
        mToolbar.setNavigationIcon(R.drawable.back);
        dataList.clear();
        List<String> list = DistrictDao.queryDistrict(city);
        dataList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }


}
