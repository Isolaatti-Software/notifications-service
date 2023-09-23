package com.isolaatti.notifications_service.push_notifications

data class FcmEntity(
    val id: Long,
    val userId: Int,
    val token: String
)