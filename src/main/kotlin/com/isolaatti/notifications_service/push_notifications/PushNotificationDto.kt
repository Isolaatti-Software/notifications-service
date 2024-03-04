package com.isolaatti.notifications_service.push_notifications

import kotlinx.serialization.Serializable
import java.time.ZonedDateTime

@Serializable
data class PushNotificationDto(
    val id: Long,
    val timeStamp: String,
    val userId: Int,
    val read: Boolean,
    val relatedNotifications: List<Long>,
    val data: HashMap<String, String>
) {
    fun toMap(): Map<String, String> {
        return mutableMapOf(
            "id" to id.toString(),
            "timeStamp" to timeStamp.toString(),
            "userId" to userId.toString(),
            "read" to read.toString(),
            "relatedNotifications" to relatedNotifications.toString(),
        ).apply {
            putAll(data)
        }
    }
}