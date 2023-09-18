package com.isolaatti.notifications_service.push_notifications.domain

import com.isolaatti.notifications_service.push_notifications.data.PushNotificationsDao

class NotificationSender(private val pushNotificationsDao: PushNotificationsDao) {

    fun notifyUser(user: Int) {
        val fcmTokens = pushNotificationsDao.getFcmTokens(user)

        println(fcmTokens)
    }
}