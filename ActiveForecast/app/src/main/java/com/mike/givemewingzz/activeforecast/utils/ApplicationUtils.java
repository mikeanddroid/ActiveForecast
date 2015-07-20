package com.mike.givemewingzz.activeforecast.utils;


public class ApplicationUtils
{
    static class ApiEndpoints
    {

        public static String CURRENT_WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
        public static String FORECAST_WEATHER_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";


        ApiEndpoints()
        {
        }
    }

    public static class DbConstants
    {

        public static final String DB_NAME = "activeweather";
        public static final String DB_TABLE = "weather_contents";
        public static final int DB_VERSION = 1;

        public DbConstants()
        {
        }
    }

    public static class Extra
    {

        public static final String KEY_DATA = "KEY_DATA";
        public static final String KEY_FAILURE_LITE = "KEY_FAILURE_LITE";
        public static final String KEY_IN_MARKET = "KEY_IN_MARKET";
        public static final String KEY_NUM_FAVES = "KEY_NUM_FAVES";

        public Extra()
        {
        }
    }

    public static class IntentKey
    {

        public static final String REQUEST_KEY = "REQUEST_KEY";
        public static final String REQUEST_SUCCESS_KEY = "REQUEST_SUCCESS_KEY";

        public IntentKey()
        {
        }
    }

    public static class JSONKeys
    {

        public static final String CLOUDS = "clouds";
        public static final String CURRENT_CORD = "coord";
        public static final String CURRENT_MAIN_DATA = "main";
        public static final String CURRENT_SYS = "sys";
        public static final String CURRENT_WEATHER = "weather";
        public static final String CURRENT_WIND = "wind";
        public static final String CURRENT_ZIP = "zip";
        public static final String DEGREE = "deg";
        public static final String DESCRIPTION = "description";
        public static final String HUMIDITY = "humidity";
        public static final String ICON = "icon";
        public static final String ICON_ID = "id";
        public static final String MAIN = "main";
        public static final String PRESSURE = "pressure";
        public static final String RAIN = "rain";
        public static final String SPEED = "speed";
        public static final String TEMP = "temp";
        public static final String TEMP_MAX = "temp_max";
        public static final String TEMP_MIN = "temp_min";
        public static final String TIME_STAMP = "dt";
        public static final String WIND = "wind";

        public JSONKeys()
        {
        }
    }

    public static class Receivers
    {

        public static final String CURRENT_WEATHER_DATA = "CURRENT_WEATHER_DATA";
        public static final String FORECAST_WEATHER_DATA = "FORECAST_WEATHER_DATA";
        public static final String HISTORIC_WEATHER_DATA = "HISTORIC_WEATHER_DATA";
        public static final String HOURLY_WEATHER_DATA = "HOURLY_WEATHER_DATA";

        public Receivers()
        {
        }
    }

    public static class RequestType
    {

        public static final int CURRENT_WEATHER_DATA = 100;
        public static final int FORECAST_WEATHER_DATA = 102;
        public static final int HISTORIC_WEATHER_DATA = 103;
        public static final int HOURLY_WEATHER_DATA = 101;

        public RequestType()
        {
        }
    }


    public ApplicationUtils()
    {
    }

    public static String getReceiverForType(int i)
    {
        switch (i)
        {
        default:
            return null;

        case 100: // 'd'
            return "CURRENT_WEATHER_DATA";

        case 101: // 'e'
            return "HOURLY_WEATHER_DATA";

        case 102: // 'f'
            return "FORECAST_WEATHER_DATA";

        case 103: // 'g'
            return "HISTORIC_WEATHER_DATA";
        }
    }
}