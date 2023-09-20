package com.isolaatti.notifications_service

import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val databaseConfig: DatabaseConfig,
    val rabbitmqConfig: RabbitmqConfig,
    val sendgridApiKey: String
) {

    @Serializable
    data class DatabaseConfig(
        val username: String,
        val password: String,
        val jdbcConnectionString: String,
    )

    @Serializable
    data class RabbitmqConfig(
        val username: String,
        val password: String,
        val virtualHost: String,
        val host: String,
        val port: Int
    )

    companion object {
        lateinit var config: Config
    }
}
