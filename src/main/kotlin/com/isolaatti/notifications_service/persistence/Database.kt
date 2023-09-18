package com.isolaatti.notifications_service.persistence

import org.apache.commons.dbcp2.BasicDataSource
import java.sql.Connection


object Database {
    private val connectionSource: BasicDataSource = BasicDataSource().apply {
        username = "erik"
        password = "password"
        url = "jdbc:postgresql://10.0.0.12/isolaatti-notifications-service"
        minIdle = 5
        maxIdle = 20
        maxOpenPreparedStatements = 100
    }

    fun getConnection(): Connection {
        return connectionSource.connection
    }
}