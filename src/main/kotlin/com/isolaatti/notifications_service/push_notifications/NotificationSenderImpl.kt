package com.isolaatti.notifications_service.push_notifications

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.MulticastMessage
import com.google.firebase.messaging.Notification

class NotificationSenderImpl(private val pushNotificationsDao: PushNotificationsDao) : NotificationSender {

    private val cloudMessaging = FirebaseMessaging.getInstance()
    override fun sendNotificationToSingleUser(pushNotificationDto: PushNotificationDto) {
        val tokens = pushNotificationsDao.getFcmTokens(pushNotificationDto.userId)
        val notification = Notification.builder().build()

        val message = MulticastMessage.builder()
            .setNotification(notification)
            .putAllData(pushNotificationDto.toMap())
            .addAllTokens(tokens)
            .build()

        val response = cloudMessaging.sendEachForMulticast(message)
    }

    override fun sendNotificationToUsers(userIds: Array<Int>, jsonData: String) {
        TODO("Not yet implemented")
    }
}