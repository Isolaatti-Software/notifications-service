package com.isolaatti.notifications_service.persistence

import com.isolaatti.notifications_service.Config
import org.apache.commons.dbcp2.BasicDataSource
import java.sql.Connection


object Database {
    private val databaseConfig = Config.config.databaseConfig
    private val connectionSource: BasicDataSource = BasicDataSource().apply {
        username = databaseConfig.username
        password = databaseConfig.password
        url = databaseConfig.jdbcConnectionString
        minIdle = 5
        maxIdle = 20
        maxOpenPreparedStatements = 100
    }

    fun getConnection(): Connection {
        return connectionSource.connection
    }
}