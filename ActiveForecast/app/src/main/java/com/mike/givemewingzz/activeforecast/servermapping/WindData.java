package com.mike.givemewingzz.activeforecast.servermapping;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="WindData")
public class WindData extends WeatherModel
{

    @Column(name = "WindDegree")
    @PropMap(serverFieldName = "deg")
    public int windDegree;

    @Column(name = "WindSpeed")
    @PropMap(serverFieldName = "speed")
    public double windSpeed;

}
