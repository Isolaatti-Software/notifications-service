package com.isolaatti.notifications_service.messaging

import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory

object Rabbitmq {
    val connection: Connection by lazy {
        val connectionFactory = ConnectionFactory()

        connectionFactory.apply {
            username = "erik"
            password = "password"
            virtualHost = "/"
            host = "10.0.0.12"
            port = 5672
        }
        connectionFactory.newConnection("notifications_service")
    }
}