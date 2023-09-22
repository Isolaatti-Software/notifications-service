package com.isolaatti.notifications_service.email.service

import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Channel
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope
import kotlinx.serialization.json.Json

class EmailQueueConsumer(channel: Channel?) : DefaultConsumer(channel) {
    private val emailSender: EmailSender = EmailSenderImpl()

    override fun handleDelivery(
        consumerTag: String?,
        envelope: Envelope?,
        properties: AMQP.BasicProperties?,
        body: ByteArray?
    ) {
        super.handleDelivery(consumerTag, envelope, properties, body)
        val stringBody = body?.decodeToString() ?: return
        val dto = Json.decodeFromString<EmailDto>(stringBody)
        emailSender.sendEmailToAddress(dto)
    }
}