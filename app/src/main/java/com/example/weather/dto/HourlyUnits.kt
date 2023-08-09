package com.example.weather.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class HourlyUnits (

    @SerializedName("time"           ) var time          : String? = null,
    @SerializedName("temperature_2m" ) var temperature2m : String? = null

):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(time)
        parcel.writeString(temperature2m)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HourlyUnits> {
        override fun createFromParcel(parcel: Parcel): HourlyUnits {
            return HourlyUnits(parcel)
        }

        override fun newArray(size: Int): Array<HourlyUnits?> {
            return arrayOfNulls(size)
        }
    }
}