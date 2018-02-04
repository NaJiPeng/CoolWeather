package com.njp.android.coolweather.bean;

import java.util.List;

/**
 * Created by NJP on 2018/2/3.
 */

public class Weather {

    /**
     * error : 0
     * status : success
     * date : 2018-02-02
     * results : [{"currentCity":"北京","pm25":"29","index":[{"des":"天气寒冷，建议着厚羽绒服、毛皮大衣加厚毛衣等隆冬服装。年老体弱者尤其要注意保暖防冻。","tipt":"穿衣指数","title":"穿衣","zs":"寒冷"},{"des":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。","tipt":"洗车指数","title":"洗车","zs":"较适宜"},{"des":"天气较凉，较易发生感冒，请适当增加衣服。体质较弱的朋友尤其应该注意防护。","tipt":"感冒指数","title":"感冒","zs":"较易发"},{"des":"天气较好，但考虑天气寒冷，风力较强，推荐您进行室内运动，若户外运动请注意保暖并做好准备活动。","tipt":"运动指数","title":"运动","zs":"较不宜"},{"des":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。","tipt":"紫外线强度指数","title":"紫外线强度","zs":"弱"}],"weather_data":[{"date":"周五 02月02日 (实时：-2℃)","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/qing.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/qing.png","weather":"晴","wind":"北风3-4级","temperature":"-1 ~ -9℃"},{"date":"周六","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/duoyun.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/duoyun.png","weather":"多云","wind":"北风微风","temperature":"0 ~ -9℃"},{"date":"周日","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/qing.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/qing.png","weather":"晴","wind":"西南风微风","temperature":"1 ~ -8℃"},{"date":"周一","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/qing.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/qing.png","weather":"晴","wind":"东北风微风","temperature":"0 ~ -10℃"}]}]
     */

    private int error;
    private String status;
    private String date;
    private List<ResultsBean> results;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * currentCity : 北京
         * pm25 : 29
         * index : [{"des":"天气寒冷，建议着厚羽绒服、毛皮大衣加厚毛衣等隆冬服装。年老体弱者尤其要注意保暖防冻。","tipt":"穿衣指数","title":"穿衣","zs":"寒冷"},{"des":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。","tipt":"洗车指数","title":"洗车","zs":"较适宜"},{"des":"天气较凉，较易发生感冒，请适当增加衣服。体质较弱的朋友尤其应该注意防护。","tipt":"感冒指数","title":"感冒","zs":"较易发"},{"des":"天气较好，但考虑天气寒冷，风力较强，推荐您进行室内运动，若户外运动请注意保暖并做好准备活动。","tipt":"运动指数","title":"运动","zs":"较不宜"},{"des":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。","tipt":"紫外线强度指数","title":"紫外线强度","zs":"弱"}]
         * weather_data : [{"date":"周五 02月02日 (实时：-2℃)","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/qing.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/qing.png","weather":"晴","wind":"北风3-4级","temperature":"-1 ~ -9℃"},{"date":"周六","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/duoyun.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/duoyun.png","weather":"多云","wind":"北风微风","temperature":"0 ~ -9℃"},{"date":"周日","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/qing.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/qing.png","weather":"晴","wind":"西南风微风","temperature":"1 ~ -8℃"},{"date":"周一","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/qing.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/qing.png","weather":"晴","wind":"东北风微风","temperature":"0 ~ -10℃"}]
         */

        private String currentCity;
        private String pm25;
        private List<IndexBean> index;
        private List<WeatherDataBean> weather_data;

        public String getCurrentCity() {
            return currentCity;
        }

        public void setCurrentCity(String currentCity) {
            this.currentCity = currentCity;
        }

        public String getPm25() {
            return pm25;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        public List<IndexBean> getIndex() {
            return index;
        }

        public void setIndex(List<IndexBean> index) {
            this.index = index;
        }

        public List<WeatherDataBean> getWeather_data() {
            return weather_data;
        }

        public void setWeather_data(List<WeatherDataBean> weather_data) {
            this.weather_data = weather_data;
        }

        public static class IndexBean {
            /**
             * des : 天气寒冷，建议着厚羽绒服、毛皮大衣加厚毛衣等隆冬服装。年老体弱者尤其要注意保暖防冻。
             * tipt : 穿衣指数
             * title : 穿衣
             * zs : 寒冷
             */

            private String des;
            private String tipt;
            private String title;
            private String zs;

            public String getDes() {
                return des;
            }

            public void setDes(String des) {
                this.des = des;
            }

            public String getTipt() {
                return tipt;
            }

            public void setTipt(String tipt) {
                this.tipt = tipt;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getZs() {
                return zs;
            }

            public void setZs(String zs) {
                this.zs = zs;
            }
        }

        public static class WeatherDataBean {
            /**
             * date : 周五 02月02日 (实时：-2℃)
             * dayPictureUrl : http://api.map.baidu.com/images/weather/day/qing.png
             * nightPictureUrl : http://api.map.baidu.com/images/weather/night/qing.png
             * weather : 晴
             * wind : 北风3-4级
             * temperature : -1 ~ -9℃
             */

            private String date;
            private String dayPictureUrl;
            private String nightPictureUrl;
            private String weather;
            private String wind;
            private String temperature;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getDayPictureUrl() {
                return dayPictureUrl;
            }

            public void setDayPictureUrl(String dayPictureUrl) {
                this.dayPictureUrl = dayPictureUrl;
            }

            public String getNightPictureUrl() {
                return nightPictureUrl;
            }

            public void setNightPictureUrl(String nightPictureUrl) {
                this.nightPictureUrl = nightPictureUrl;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public String getWind() {
                return wind;
            }

            public void setWind(String wind) {
                this.wind = wind;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }
        }
    }
}
