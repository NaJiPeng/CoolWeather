package com.njp.android.coolweather.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.njp.android.coolweather.Config.UrlConfig;
import com.njp.android.coolweather.R;
import com.njp.android.coolweather.bean.Weather;
import com.njp.android.coolweather.utils.ToastUtil;
import com.njp.android.coolweather.utils.WeatherCallback;

import org.w3c.dom.Text;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class WeatherActivity extends AppCompatActivity {


    private OkHttpClient mClient = new OkHttpClient();

    private String location;

    private ScrollView mScrollView;

    private TextView titleCity;

    private TextView titleUpdateTime;

    private TextView degreeText;

    private TextView weatherInfoText;

    private LinearLayout forecastLayout;

    private TextView pm25Text;

    private LinearLayout suggestLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        initView();
        requestWeather();

    }

    private void requestWeather() {
        final Request request = new Request.Builder()
                .url(UrlConfig.getUrl("抚顺"))
                .get()
                .build();
        mClient.newCall(request).enqueue(new WeatherCallback() {
            @Override
            public void onError(Exception e) {
                ToastUtil.show("获取天气失败");
            }

            @Override
            public void onSuccess(final Weather weather) {
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

                titleCity.setText(cityName);
                titleUpdateTime.setText(updateTime);
                degreeText.setText(degree + "℃");
                weatherInfoText.setText(weatherInfo);
                pm25Text.setText(pm25);

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
    }
}
