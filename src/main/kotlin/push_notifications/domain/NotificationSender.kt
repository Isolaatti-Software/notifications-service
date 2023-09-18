package push_notifications.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import push_notifications.data.PushNotificationsDao

class NotificationSender(private val pushNotificationsDao: PushNotificationsDao) {

    fun notifyUser(user: Int) {
        val fcmTokens = pushNotificationsDao.getFcmTokens(user)

        println(fcmTokens)
    }
}