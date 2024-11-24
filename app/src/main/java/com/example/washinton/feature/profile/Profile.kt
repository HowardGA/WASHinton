package com.example.washinton.feature.profile

data class Profile(
    val user_id: Int = 0,
    val first_name: String = "",
    val last_name: String = "",
    val phone: String = "",
    val email: String = "",
    val role: String = "",
    val location_type: String = "",
    val status: String = "",
)