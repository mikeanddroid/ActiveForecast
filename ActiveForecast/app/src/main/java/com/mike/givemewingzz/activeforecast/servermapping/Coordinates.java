
package com.mike.givemewingzz.activeforecast.servermapping;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="Coordinates")
public class Coordinates extends WeatherModel
{

    @Column(name = "Latitude")
    @PropMap(serverFieldName = "lat")
    public double latitude;

    @Column(name = "Longitude")
    @PropMap(serverFieldName = "lon")
    public double longitude;

}
