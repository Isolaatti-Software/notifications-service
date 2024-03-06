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

        val multicastResponse = cloudMessaging.sendEachForMulticast(message)

        val tokensToRemove: MutableList<String> = mutableListOf()

        multicastResponse.responses.forEachIndexed { index, response ->

            // invalid tokens must be removed
            if(response?.isSuccessful == false) {
                tokensToRemove.add(tokens[index])
            }
        }
        if(tokensToRemove.isNotEmpty()) {
            println("Removing invalid tokens: $tokensToRemove")
            pushNotificationsDao.removeTokens(tokensToRemove.toTypedArray())
        }

    }

    override fun sendNotificationToUsers(userIds: Array<Int>, jsonData: String) {
        TODO("Not yet implemented")
    }
}