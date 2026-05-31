package com.example.businessdir.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Company(
    val id: Int = 0,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val email: String,
    val telephone: String,
    val website: String,
    @SerialName("is_industry") val isIndustry: Boolean,
    @SerialName("is_fun") val isFun: Boolean,
    @SerialName("is_education") val isEducation: Boolean,
    @SerialName("is_services") val isServices: Boolean
)