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
        val stringBody = body?.decodeToString() ?: return
        val dto = Json.decodeFromString<PushNotificationDto>(stringBody)
        notificationSender.sendNotificationToSingleUser(dto.userId, dto.sessionId, dto.title, dto.body, dto.imageUrl, dto.url)
    }
}