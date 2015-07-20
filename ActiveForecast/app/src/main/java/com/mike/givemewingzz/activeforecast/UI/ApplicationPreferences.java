package com.mike.givemewingzz.activeforecast.UI;

import android.content.Context;
import android.content.SharedPreferences;

public class ApplicationPreferences
{
    static class PreferencesKey
    {

        public static final String CLOUDS_KEY = "clouds";
        public static final String DEGREE_KEY = "deg";
        public static final String DESCRIPTION_KEY = "description";
        public static final String HUMIDITY_KEY = "humidity";
        public static final String ICON_ID_KEY = "id";
        public static final String ICON_KEY = "icon";
        public static final String MAIN_KEY = "main";
        public static final String PRESSURE_KEY = "pressure";
        public static final String RAIN_KEY = "rain";
        public static final String SPEED_KEY = "speed";
        public static final String TEMP_KEY = "temp";
        public static final String TEMP_MAX_KEY = "temp_max";
        public static final String TEMP_MIN_KEY = "temp_min";
        public static final String TIME_STAMP_KEY = "dt";
        public static final String WIND_KEY = "wind";

    }


    public static final String SHARED_PREFS = "SHARED_PREFS";
    private Context _context;
    private SharedPreferences appPrefs;
    public String clouds;
    public String degree;
    public String descriptionID;
    public String humidity;
    public String icon;
    public String iconID;
    public String main;
    public String pressure;
    public String rain;
    public String speedKey;
    public String temp;
    public String temp_max;
    public String temp_min;
    public String timeStamp;
    public String wind;

    public ApplicationPreferences()
    {
        appPrefs = CoreApplication.getInstance().getSharedPreferences("SHARED_PREFS", 0);
    }

    public String getClouds()
    {
        return appPrefs.getString("clouds", "");
    }

    public String getDegree()
    {
        return appPrefs.getString("deg", "");
    }

    public String getDescriptionID()
    {
        return appPrefs.getString("description", "");
    }

    public SharedPreferences.Editor getEditor()
    {
        return appPrefs.edit();
    }

    public String getHumidity()
    {
        return appPrefs.getString("humidity", "");
    }

    public String getIcon()
    {
        return appPrefs.getString("icon", "");
    }

    public String getIconID()
    {
        return appPrefs.getString("id", "");
    }

    public String getMain()
    {
        return appPrefs.getString("main", "");
    }

    public String getPressure()
    {
        return appPrefs.getString("pressure", "");
    }

    public String getRain()
    {
        return appPrefs.getString("rain", "");
    }

    public String getSpeedKey()
    {
        return appPrefs.getString("speed", "");
    }

    public String getTemp()
    {
        return appPrefs.getString("temp", "");
    }

    public String getTemp_max()
    {
        return appPrefs.getString("temp_max", "");
    }

    public String getTemp_min()
    {
        return appPrefs.getString("temp_min", "");
    }

    public String getTimeStamp()
    {
        return appPrefs.getString("dt", "");
    }

    public String getWind()
    {
        return appPrefs.getString("wind", "");
    }

    public void setClouds(String s)
    {
        clouds = s;
        getEditor().putString("clouds", s).commit();
    }

    public void setDegree(String s)
    {
        degree = s;
        getEditor().putString("deg", s).commit();
    }

    public void setDescriptionID(String s)
    {
        getEditor().putString("description", s).commit();
    }

    public void setHumidity(String s)
    {
        humidity = s;
        getEditor().putString("humidity", s).commit();
    }

    public void setIcon(String s)
    {
        icon = s;
        getEditor().putString("icon", s).commit();
    }

    public void setIconID(String s)
    {
        getEditor().putString("id", s).commit();
    }

    public void setMain(String s)
    {
        main = s;
        getEditor().putString("main", s).commit();
    }

    public void setPressure(String s)
    {
        pressure = s;
        getEditor().putString("pressure", s).commit();
    }

    public void setRain(String s)
    {
        rain = s;
        getEditor().putString("rain", s).commit();
    }

    public void setSpeedKey(String s)
    {
        speedKey = s;
        getEditor().putString("speed", s).commit();
    }

    public void setTemp(String s)
    {
        temp = s;
        getEditor().putString("temp", s).commit();
    }

    public void setTemp_max(String s)
    {
        temp_max = s;
        getEditor().putString("temp_max", s).commit();
    }

    public void setTemp_min(String s)
    {
        temp_min = s;
        getEditor().putString("temp_min", s).commit();
    }

    public void setTimeStamp(String s)
    {
        timeStamp = s;
        getEditor().putString("dt", s).commit();
    }

    public void setWind(String s)
    {
        wind = s;
        getEditor().putString("wind", s).commit();
    }
}
