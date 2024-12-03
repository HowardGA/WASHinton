package com.example.washinton.feature.profile

data class Profile(
    val name: String = "",
    val phone: String = "",
    val email: String = "",
    val role: Role = Role(),
    val locationType: String = "",
    val status: String = "",
    val FBID: String = ""
)

data class Role(
    val name: String = ""
)
