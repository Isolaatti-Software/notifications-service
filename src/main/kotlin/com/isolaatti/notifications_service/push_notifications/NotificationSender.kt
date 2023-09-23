package com.isolaatti.notifications_service.push_notifications

interface NotificationSender {
    fun sendNotificationToSingleUser(userId: Int, sessionId: String, title: String, body: String, imageUrl: String?, url: String)

    fun sendNotificationToUsers(userIds: Array<Int>, jsonData: String)
}