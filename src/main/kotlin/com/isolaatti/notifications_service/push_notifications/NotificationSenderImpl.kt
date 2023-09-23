package com.isolaatti.notifications_service.push_notifications

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.MulticastMessage
import com.google.firebase.messaging.Notification

class NotificationSenderImpl(private val pushNotificationsDao: PushNotificationsDao) : NotificationSender {

    private val cloudMessaging = FirebaseMessaging.getInstance()
    override fun sendNotificationToSingleUser(
        userId: Int,
        sessionId: String,
        title: String,
        body: String,
        imageUrl: String?,
        url: String
    ) {
        val tokens = pushNotificationsDao.getFcmTokens(userId)
        val notification = Notification.builder().apply {
            setTitle(title)
            setBody(body)
            if(imageUrl != null) {
                setImage(imageUrl)
            }
        }.build()

        val message = MulticastMessage.builder()
            .setNotification(notification)
            .putData("url", url)
            .addAllTokens(tokens)
            .build()

        cloudMessaging.sendEachForMulticast(message)
    }

    override fun sendNotificationToUsers(userIds: Array<Int>, jsonData: String) {
        TODO("Not yet implemented")
    }
}