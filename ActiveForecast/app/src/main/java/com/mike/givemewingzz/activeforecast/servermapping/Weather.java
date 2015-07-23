package com.mike.givemewingzz.activeforecast.servermapping;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="Weather")
public class Weather extends WeatherModel
{

    @Column(name = "IconID")
    @PropMap(serverFieldName = "id")
    public int iconID;

    @Column(name = "WeatherCondition")
    @PropMap(serverFieldName = "main")
    public String weatherCondition;

    @Column(name = "WeatherDescription")
    @PropMap(serverFieldName = "description")
    public String weatherDescription;

    @Column(name = "WeatherIconID")
    @PropMap(serverFieldName = "icon")
    public String weatherIcon;

}
