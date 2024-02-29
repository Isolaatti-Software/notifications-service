package com.isolaatti.notifications_service.push_notifications

import kotlinx.serialization.Serializable

data class FcmEntity(
    val id: Long,
    val userId: Int,
    val sessionId: String,
    val token: String
)

@Serializable
data class FcmDto(
    val userId: Int,
    val sessionId: String,
    val token: String
)