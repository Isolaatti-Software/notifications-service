package com.isolaatti.notifications_service.messaging

import com.isolaatti.notifications_service.Config
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory

object Rabbitmq {
    private const val CONNECTION_NAME = "notifications_service"

    val connection: Connection by lazy {
        val connectionFactory = ConnectionFactory()

        val rabbitmqConfig = Config.config.rabbitmqConfig
        connectionFactory.apply {
            username = rabbitmqConfig.username
            password = rabbitmqConfig.password
            virtualHost = rabbitmqConfig.virtualHost
            host = rabbitmqConfig.host
            port = rabbitmqConfig.port
        }
        connectionFactory.newConnection(CONNECTION_NAME)
    }
}