package com.isolaatti.notifications_service

import com.rabbitmq.client.AMQP
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope
import com.isolaatti.notifications_service.messaging.Rabbitmq
import kotlin.jvm.optionals.getOrNull

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val channel = Rabbitmq.connection.openChannel().getOrNull()

        channel?.exchangeDeclare("default_exchange", "direct", true)

        channel?.queueDeclare("notification_send_queue", true, false, false, null)

        channel?.queueBind("notification_send_queue", "default_exchange", "routing")

        channel?.basicConsume("notification_send_queue", false, object: DefaultConsumer(channel) {
            override fun handleDelivery(
                consumerTag: String?,
                envelope: Envelope?,
                properties: AMQP.BasicProperties?,
                body: ByteArray?
            ) {
                val routingKey = envelope?.routingKey
                val contentType = properties?.contentType
                val deliveryTag = envelope?.deliveryTag
                println("llega algo")
                if (deliveryTag != null) {
                    channel.basicAck(deliveryTag, false)
                }
            }
        })
    }


}
