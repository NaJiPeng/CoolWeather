package com.njp.android.coolweather.utils;

import com.google.gson.Gson;
import com.njp.android.coolweather.bean.Weather;

import java.io.IOException;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 公共的回调接口
 */

public abstract class WeatherCallback implements Callback {

    @Override
    public void onFailure(Call call, IOException e) {
        onError(e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final String string = response.body().string();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext(string);
            }
        }).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Weather weather = new Gson().fromJson(s, Weather.class);
                        if (weather.getError() == 0 && "success".equals(weather.getStatus())) {
                            onSuccess(weather);
                        } else {
                            onError(new RuntimeException(weather.getStatus()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public abstract void onError(Exception e);

    public abstract void onSuccess(Weather weather);

}
