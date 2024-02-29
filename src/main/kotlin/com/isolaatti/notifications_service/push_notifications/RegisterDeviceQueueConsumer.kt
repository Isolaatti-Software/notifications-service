package com.isolaatti.notifications_service.push_notifications

import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Channel
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope
import kotlinx.serialization.json.Json

class RegisterDeviceQueueConsumer(channel: Channel) : DefaultConsumer(channel) {

    val pushNotificationsDao = PushNotificationsDao()
    override fun handleDelivery(
        consumerTag: String?,
        envelope: Envelope?,
        properties: AMQP.BasicProperties?,
        body: ByteArray?
    ) {
        val stringBody = body?.decodeToString() ?: return

        try {
            val dto = Json.decodeFromString<FcmDto>(stringBody)
            System.out.println("Register device: $dto")
            pushNotificationsDao.insertFcmToken(dto.userId, dto.sessionId, dto.token)
        } catch (e: Exception) {
            System.err.println(e.message)
        }
    }
}