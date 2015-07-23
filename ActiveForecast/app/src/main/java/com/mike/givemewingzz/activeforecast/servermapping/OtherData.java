
package com.mike.givemewingzz.activeforecast.servermapping;


import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="OtherData")
public class OtherData extends WeatherModel
{

    @Column(name = "Country")
    @PropMap(serverFieldName = "country")
    public String country;

    @Column(name = "_ID")
    @PropMap(serverFieldName = "id")
    public int id;

    @Column(name = "Message")
    @PropMap(serverFieldName = "message")
    public double message;

    @Column(name = "Sunrise")
    @PropMap(serverFieldName = "sunrise")
    public long sunrise;

    @Column(name = "Sunset")
    @PropMap(serverFieldName = "sunset")
    public long sunset;

    @Column(name = "Type")
    @PropMap(serverFieldName = "type")
    public int type;

}
