package com.njp.android.coolweather.ui;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.njp.android.coolweather.Config.UrlConfig;
import com.njp.android.coolweather.R;
import com.njp.android.coolweather.bean.Weather;
import com.njp.android.coolweather.utils.ToastUtil;
import com.njp.android.coolweather.utils.WeatherCallback;

import org.w3c.dom.Text;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class WeatherActivity extends AppCompatActivity {
    private static final String TAG = "WeatherActivity";

    public static final int REQUEST_PERMISSION = 1001;

    private OkHttpClient mClient;

    private LocationClient mLocationClient;

    private String mLocation;

    private DrawerLayout mDrawerLayout;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ImageView menu;

    private ImageView settings;

    private ImageView background;

    private ScrollView mScrollView;

    private TextView titleCity;

    private TextView titleUpdateTime;

    private TextView degreeText;

    private TextView weatherInfoText;

    private LinearLayout forecastLayout;

    private TextView pm25Text;

    private TextView windText;

    private LinearLayout suggestLayout;

    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        initView();

        initEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initLocation();
        requestWeather();
    }

    private void initLocation() {
        mLocation = mPreferences.getString("location", null);
        boolean isLocation = mPreferences.getBoolean("isLocation", true);
        if (isLocation) {
            requestLocation();
        }
        if (mLocation == null) {
            mPreferences.edit().putString("location", "").apply();
            mLocation = "";
        }
    }

    private void requestLocation() {
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION);
        } else {
            mLocationClient.start();
        }
    }

    private void requestWeather() {

        Log.i(TAG, "requestWeather: " + mLocation);
        mSwipeRefreshLayout.setRefreshing(true);
        if (mLocation.equals("")) {
            ToastUtil.show("请选择城市");
            mSwipeRefreshLayout.setRefreshing(false);
            return;
        }

        Request request = new Request.Builder()
                .url(UrlConfig.getUrl(mLocation))
                .get()
                .build();
        mClient.newCall(request).enqueue(new WeatherCallback() {
            @Override
            public void onError(Exception e) {
                ToastUtil.show("获取天气失败");
                mSwipeRefreshLayout.setRefreshing(false);
            }


            @Override
            public void onSuccess(Weather weather) {
                Weather.ResultsBean result = weather.getResults().get(0);
                String cityName = result.getCurrentCity();
                String updateTime = weather.getDate();
                String pm25 = result.getPm25();
                List<Weather.ResultsBean.WeatherDataBean> weatherData = result.getWeather_data();
                List<Weather.ResultsBean.IndexBean> index = result.getIndex();
                Weather.ResultsBean.WeatherDataBean dataBean = weatherData.get(0);
                String[] strings = dataBean.getDate().split("：|℃");
                String degree = strings[1];
                String weatherInfo = dataBean.getWeather();
                String wind = dataBean.getWind();

                titleCity.setText(cityName);
                titleUpdateTime.setText(updateTime);
                degreeText.setText(degree + "℃");
                weatherInfoText.setText(weatherInfo);
                pm25Text.setText(pm25);
                windText.setText(wind);

                forecastLayout.removeAllViews();
                for (int i = 1; i < weatherData.size(); i++) {
                    Weather.ResultsBean.WeatherDataBean weatherDataBean = weatherData.get(i);
                    View view = LayoutInflater.from(WeatherActivity.this)
                            .inflate(R.layout.forecast_item, forecastLayout, false);
                    TextView dateText = view.findViewById(R.id.date_text);
                    TextView infoText = view.findViewById(R.id.info_text);
                    TextView tempText = view.findViewById(R.id.temp_text);

                    dateText.setText(weatherDataBean.getDate());
                    infoText.setText(weatherDataBean.getWeather());
                    tempText.setText(weatherDataBean.getTemperature());

                    forecastLayout.addView(view);
                }

                suggestLayout.removeAllViews();
                for (Weather.ResultsBean.IndexBean indexBean : index) {
                    TextView textView = (TextView) LayoutInflater.from(WeatherActivity.this)
                            .inflate(R.layout.suggest_item, suggestLayout, false);

                    StringBuilder builder = new StringBuilder();
                    builder.append(indexBean.getTipt()).append(":")
                            .append(indexBean.getZs()).append("\n")
                            .append(indexBean.getDes());
                    textView.setText(builder.toString());

                    suggestLayout.addView(textView);
                }

                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initView() {
        mScrollView = findViewById(R.id.weather_layout);
        titleCity = findViewById(R.id.title_city);
        titleUpdateTime = findViewById(R.id.title_update_time);
        degreeText = findViewById(R.id.degree_text);
        weatherInfoText = findViewById(R.id.weather_info_text);
        forecastLayout = findViewById(R.id.forecast_layout);
        pm25Text = findViewById(R.id.pm25_text);
        suggestLayout = findViewById(R.id.suggest_layout);
        background = findViewById(R.id.background);
        windText = findViewById(R.id.wind_info_text);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        menu = findViewById(R.id.menu);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        settings = findViewById(R.id.settings);

        Glide.with(background.getContext())
                .setDefaultRequestOptions(new RequestOptions()
                        .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                )
                .load(UrlConfig.PIC_URL)
                .into(background);

        mClient = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .writeTimeout(5000, TimeUnit.MILLISECONDS)
                .build();

        mLocationClient = new LocationClient(this);

        mPreferences = getSharedPreferences("settings", MODE_PRIVATE);

        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);

    }

    public void changeLocation(String location) {

        mDrawerLayout.closeDrawers();
        mLocation = location;
        mPreferences.edit().putString("location", mLocation).apply();
        mSwipeRefreshLayout.setRefreshing(true);
        requestWeather();
    }

    private void initEvent() {

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather();
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });

        mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                String district = bdLocation.getDistrict();
                changeLocation(district);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            ToastUtil.show("获取位置信息失败");
                            return;
                        }
                    }
                    mLocationClient.start();
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }
}
