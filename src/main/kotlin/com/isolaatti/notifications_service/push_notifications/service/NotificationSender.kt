package com.isolaatti.notifications_service.push_notifications.service

interface NotificationSender {
    fun sendNotificationToSingleUser(userId: Int, jsonData: String)

    fun sendNotificationToUsers(userIds: Array<Int>, jsonData: String)
}