package com.njp.android.coolweather.Config;

/**
 * URL信息
 */

public class UrlConfig {

    public static final String BASE_URL = "http://api.map.baidu.com/telematics/v3/weather";

    private static final String AK = "6tYzTvGZSOpYB5Oc2YGGOKt8";

    private static final String OUTPUT = "json";

    public static final String getUrl(String location) {
        StringBuilder builder = new StringBuilder(BASE_URL);
        builder.append("?location=").append(location)
                .append("&output=").append(OUTPUT)
                .append("&ak=").append(AK);
        return builder.toString();
    }

}
