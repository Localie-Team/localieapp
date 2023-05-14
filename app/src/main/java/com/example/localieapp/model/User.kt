package com.example.localieapp.model

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

data class User(
    val name: String? = null,
    val last: String? = null,
    val cart: List<String>? = null,
    val win: List<String>? = null,
    val age: String? = null,
    var email: String? = null,
    val permissions: String? = null,
    val profile_pic: String? = null,
    val region: String? = null,
    val UID: String? = null
): Parcelable {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        mutableListOf<String>().apply{
                                     parcel.readList(this, String::class.java.classLoader)
        },
        mutableListOf<String>().apply{
            parcel.readList(this, String::class.java.classLoader)
        },
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(last)
        parcel.writeList(cart)
        parcel.writeList(win)
        parcel.writeString(age)
        parcel.writeString(email)
        parcel.writeString(permissions)
        parcel.writeString(profile_pic)
        parcel.writeString(region)
        parcel.writeString(UID)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}

