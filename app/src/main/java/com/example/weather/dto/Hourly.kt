package com.example.weather.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Hourly (

    @SerializedName("time"           ) var time          : ArrayList<String> = arrayListOf(),
    @SerializedName("temperature_2m" ) var temperature2m : ArrayList<Double> = arrayListOf()

):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createStringArrayList() ?: arrayListOf(),
        arrayListOf<Double>().apply {
            parcel.readList(this, Double::class.java.classLoader)
        }
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringList(time)
        parcel.writeList(temperature2m)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Hourly> {
        override fun createFromParcel(parcel: Parcel): Hourly {
            return Hourly(parcel)
        }

        override fun newArray(size: Int): Array<Hourly?> {
            return arrayOfNulls(size)
        }
    }
}