package com.example.localieapp.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

class User(
    var name: String,
    var last: String,
    var cart: Array<String>,
    var win: Array<String>,
    var ageRange: String,
    var email: String,
    var permissions: String,
    var uID: String
) {
}