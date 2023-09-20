package com.isolaatti.notifications_service.push_notifications.service

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.MulticastMessage
import com.isolaatti.notifications_service.push_notifications.data.PushNotificationsDao

class NotificationSenderImpl(private val pushNotificationsDao: PushNotificationsDao) : NotificationSender {

    private val cloudMessaging = FirebaseMessaging.getInstance()
    override fun sendNotificationToSingleUser(userId: Int, jsonData: String) {
        val tokens = pushNotificationsDao.getFcmTokens(userId)
        val message = MulticastMessage.builder()
            .putData("json", jsonData)
            .addAllTokens(tokens)
            .build()

        cloudMessaging.sendEachForMulticast(message)
    }

    override fun sendNotificationToUsers(userIds: Array<Int>, jsonData: String) {
        TODO("Not yet implemented")
    }
}