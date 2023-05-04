package com.example.localieapp.model

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
)

