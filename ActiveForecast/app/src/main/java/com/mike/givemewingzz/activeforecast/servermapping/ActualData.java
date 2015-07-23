
package com.mike.givemewingzz.activeforecast.servermapping;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "ActualData")
public class ActualData extends WeatherModel {

    @Column(name = "CurrentTemperature")
    @PropMap(serverFieldName = "temp")
    public String currentTemp;

    @Column(name = "CurrentTempCel")
    @PropMap(serverFieldName = "temp")
    public String currentTempInCelsius;

    @Column(name = "CurrentTempFar")
    @PropMap(serverFieldName = "temp")
    public String currentTempInFarenheit;

    @Column(name = "Humidity")
    @PropMap(serverFieldName = "humidity")
    public String humidity;

    @Column(name = "MaxTemperature")
    @PropMap(serverFieldName = "temp_max")
    public double maxTemp;

    @Column(name = "MinTemperature")
    @PropMap(serverFieldName = "temp_min")
    public double minTemp;

    @Column(name = "Pressure")
    @PropMap(serverFieldName = "pressure")
    public String pressure;

}
