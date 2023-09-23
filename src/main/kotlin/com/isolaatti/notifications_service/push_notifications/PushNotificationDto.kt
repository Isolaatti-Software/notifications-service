package com.isolaatti.notifications_service.push_notifications

data class PushNotificationDto(
    val userId: Int,
    val sessionId: String,
    val title: String,
    val body: String,
    val imageUrl: String?,
    val url: String
)