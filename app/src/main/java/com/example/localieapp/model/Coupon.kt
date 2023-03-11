package com.example.localieapp.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

class Coupon(
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int,
    var coordinate: Int,
    val url: String,
    val productName: String,
) {
}