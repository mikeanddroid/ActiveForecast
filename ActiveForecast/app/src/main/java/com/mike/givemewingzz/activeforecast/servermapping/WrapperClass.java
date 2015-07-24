package com.mike.givemewingzz.activeforecast.servermapping;

import com.activeandroid.Model;

public class WrapperClass
{

    public static final int ACTUAL_DATA_WEATHER = 1;
    public static final int COORDINATES_DATA_WEATHER = 6;
    public static final int CURRENT_DATA_WEATHER = 2;
    public static final int DAILY_FORECAST = 8;
    public static final int HOURLY_FORECAST = 7;
    public static final int OTHER_DATA_WEATHER = 5;
    public static final int THREE_DAYS_FORECAST = 9;
    public static final int WEATHER_DATA_WEATHER = 3;
    public static final int WIND_DATA_WEATHER = 4;
    public static final int ITUNES_WRAPPER_DATA = 10;
    private int idType;
    private Model object;

    public WrapperClass(Model model, int i)
    {
        idType = i;
        object = model;
    }

    public int getIdType()
    {
        return idType;
    }

    public Model getObject()
    {
        return object;
    }

    public void setObject(Model model)
    {
        object = model;
    }
}
