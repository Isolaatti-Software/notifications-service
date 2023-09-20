package com.isolaatti.notifications_service

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope
import com.isolaatti.notifications_service.messaging.Rabbitmq
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.jvm.optionals.getOrNull

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        if(args.size < 2) {
            println("Must pass 2 argument: first argument -> json config file path. Second argument -> google-services.json path")
            return
        }

        val configFilePath = args.first()
        val googleServicesPath = args[1]
        val configFile = File(configFilePath)

        val googleServicesFile = File(googleServicesPath)

        if(!configFile.exists()) {
            System.err.println("File $configFilePath does not exist")
            return
        }

        if(!googleServicesFile.exists()) {
            System.err.println("File $googleServicesPath does not exist")
            return
        }

        val configJson = configFile.readText()

        Config.config = Json.decodeFromString(configJson)

        val firebaseAppOptions = FirebaseOptions.builder()
        firebaseAppOptions.setCredentials(GoogleCredentials.fromStream(googleServicesFile.inputStream()))
        FirebaseApp.initializeApp(firebaseAppOptions.build())

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
