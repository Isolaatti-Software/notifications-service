package com.isolaatti.notifications_service.push_notifications

interface NotificationSender {
    fun sendNotificationToSingleUser(pushNotificationDto: PushNotificationDto)

    fun sendNotificationToUsers(userIds: Array<Int>, jsonData: String)
}