
package com.mike.givemewingzz.activeforecast.servermapping;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="CurrentWeather")
public class CurrentWeather extends WeatherModel
{

    @Column(name = "CityName")
    @PropMap(serverFieldName = "name")
    public String cityName;

    @Column(name = "WeatherBase")
    @PropMap(serverFieldName = "base")
    public String weatherBase;

    @Column(name = "WeatherDate")
    @PropMap(serverFieldName = "dt")
    public String weatherDate;

    @Column(name = "WeatherID")
    @PropMap(serverFieldName = "id")
    public int weatherID;

}
