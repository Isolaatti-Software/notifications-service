package com.isolaatti.notifications_service.push_notifications

import com.rabbitmq.client.*
import kotlinx.serialization.json.Json

class PushNotificationsQueueConsumer(channel: Channel?) : DefaultConsumer(channel) {

    private val notificationsDao = PushNotificationsDao()
    private val notificationSender: NotificationSender = NotificationSenderImpl(notificationsDao)
    override fun handleDelivery(
        consumerTag: String?,
        envelope: Envelope?,
        properties: AMQP.BasicProperties?,
        body: ByteArray?
    ) {
        try {
            val stringBody = body?.decodeToString() ?: return
            val dto = Json.decodeFromString<PushNotificationDto>(stringBody)
            println(stringBody)
            notificationSender.sendNotificationToSingleUser(dto)
        } catch(jsonException: Exception)  {
            println(jsonException.message)
        }


    }
}