package com.example.localieapp.model

import android.os.Parcel
import android.os.Parcelable

class Coupon(
    var coordinate: Int,
    val url: String?,
    val productName: String?,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(coordinate)
        parcel.writeString(url)
        parcel.writeString(productName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Coupon> {
        override fun createFromParcel(parcel: Parcel): Coupon {
            return Coupon(parcel)
        }

        override fun newArray(size: Int): Array<Coupon?> {
            return arrayOfNulls(size)
        }
    }
}