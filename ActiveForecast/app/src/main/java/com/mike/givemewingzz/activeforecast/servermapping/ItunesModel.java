package com.mike.givemewingzz.activeforecast.servermapping;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by GiveMeWingzz on 7/23/2015.
 */

@Table(name = "ActualData")
public class ItunesModel extends WeatherModel {

    @Column(name = "WrapperType")
    @PropMap(serverFieldName = "wrapperType")
    public String wrapperType;

    @Column(name = "Kind")
    @PropMap(serverFieldName = "kind")
    public String kind;

    @Column(name = "Artist_ID")
    @PropMap(serverFieldName = "artistId")
    public String artistId;

    @Column(name = "Collection_ID")
    @PropMap(serverFieldName = "collectionId")
    public String collectionId;

    @Column(name = "TrackID")
    @PropMap(serverFieldName = "trackId")
    public String trackId;

    @Column(name = "ArtistName")
    @PropMap(serverFieldName = "artistName")
    public String artistName;

    @Column(name = "CollectionName")
    @PropMap(serverFieldName = "collectionName")
    public String collectionName;

    @Column(name = "TrackName")
    @PropMap(serverFieldName = "trackName")
    public String trackName;

    @Column(name = "CollectionCensoreName")
    @PropMap(serverFieldName = "collectionCensoredName")
    public String collectionCensoredName;

    @Column(name = "TrackCensoredName")
    @PropMap(serverFieldName = "trackCensoredName")
    public String trackCensoredName;

    @Column(name = "ArtistViewUrl")
    @PropMap(serverFieldName = "artistViewUrl")
    public String artistViewUrl;

    @Column(name = "CollectionViewURL")
    @PropMap(serverFieldName = "collectionViewUrl")
    public String collectionViewUrl;

    @Column(name = "TrackViewUrl")
    @PropMap(serverFieldName = "trackViewUrl")
    public String trackViewUrl;

    @Column(name = "PriviewUrl")
    @PropMap(serverFieldName = "previewUrl")
    public String previewUrl;

    @Column(name = "ArtWork_30")
    @PropMap(serverFieldName = "artworkUrl30")
    public String artworkUrl30;

    @Column(name = "ArtWork_60")
    @PropMap(serverFieldName = "artworkUrl60")
    public String artworkUrl60;

    @Column(name = "ArtWork_100")
    @PropMap(serverFieldName = "artworkUrl100")
    public String artworkUrl100;

    @Column(name = "CollectionPrice")
    @PropMap(serverFieldName = "collectionPrice")
    public String collectionPrice;

    @Column(name = "TrackPrice")
    @PropMap(serverFieldName = "trackPrice")
    public String trackPrice;

    @Column(name = "ReleaseDate")
    @PropMap(serverFieldName = "releaseDate")
    public String releaseDate;

    @Column(name = "CollectionExplicitness")
    @PropMap(serverFieldName = "collectionExplicitness")
    public String collectionExplicitness;

    @Column(name = "TrackExplicitness")
    @PropMap(serverFieldName = "trackExplicitness")
    public String trackExplicitness;

    @Column(name = "Discount")
    @PropMap(serverFieldName = "discCount")
    public String discCount;

    @Column(name = "DiscNumber")
    @PropMap(serverFieldName = "discNumber")
    public String discNumber;

    @Column(name = "TrackCount")
    @PropMap(serverFieldName = "trackCount")
    public String trackCount;

    @Column(name = "TrackNumber")
    @PropMap(serverFieldName = "trackNumber")
    public String trackNumber;

    @Column(name = "TrackInMills")
    @PropMap(serverFieldName = "trackTimeMillis")
    public String trackTimeMillis;

    @Column(name = "Country")
    @PropMap(serverFieldName = "country")
    public String country;

    @Column(name = "Currency")
    @PropMap(serverFieldName = "currency")
    public String currency;

    @Column(name = "PrimaryGenreName")
    @PropMap(serverFieldName = "primaryGenreName")
    public String primaryGenreName;

    @Column(name = "RadioStation")
    @PropMap(serverFieldName = "radioStationUrl")
    public String radioStationUrl;

    @Column(name = "Streamable")
    @PropMap(serverFieldName = "isStreamable")
    public String isStreamable;

}
