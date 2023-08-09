package com.example.weather.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class WeatherTime (

    @SerializedName("latitude"              ) var latitude             : Double?         = null,
    @SerializedName("longitude"             ) var longitude            : Double?         = null,
    @SerializedName("generationtime_ms"     ) var generationtimeMs     : Double?         = null,
    @SerializedName("utc_offset_seconds"    ) var utcOffsetSeconds     : Int?            = null,
    @SerializedName("timezone"              ) var timezone             : String?         = null,
    @SerializedName("timezone_abbreviation" ) var timezoneAbbreviation : String?         = null,
    @SerializedName("elevation"             ) var elevation            : Int?            = null,
    @SerializedName("current_weather"       ) var currentWeather       : CurrentWeather? = CurrentWeather(),
    @SerializedName("hourly_units"          ) var hourlyUnits          : HourlyUnits?    = HourlyUnits(),
    @SerializedName("hourly"                ) var hourly               : Hourly?         = Hourly()

):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readParcelable(CurrentWeather::class.java.classLoader),
        parcel.readParcelable(HourlyUnits::class.java.classLoader),
        parcel.readParcelable(Hourly::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(latitude)
        parcel.writeValue(longitude)
        parcel.writeValue(generationtimeMs)
        parcel.writeValue(utcOffsetSeconds)
        parcel.writeString(timezone)
        parcel.writeString(timezoneAbbreviation)
        parcel.writeValue(elevation)
        parcel.writeParcelable(currentWeather, flags)
        parcel.writeParcelable(hourlyUnits, flags)
        parcel.writeParcelable(hourly, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WeatherTime> {
        override fun createFromParcel(parcel: Parcel): WeatherTime {
            return WeatherTime(parcel)
        }

        override fun newArray(size: Int): Array<WeatherTime?> {
            return arrayOfNulls(size)
        }
    }
}