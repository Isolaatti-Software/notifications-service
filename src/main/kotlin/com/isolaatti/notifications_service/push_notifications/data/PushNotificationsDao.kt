package com.isolaatti.notifications_service.push_notifications.data

import com.isolaatti.notifications_service.persistence.Database
import java.sql.SQLException

class PushNotificationsDao {

    fun getFcmTokens(userId: Int): List<String>  {
        val list: MutableList<String> = mutableListOf()
        try {
            Database.getConnection().use { connection ->
                val query = "SELECT token FROM fcm WHERE userId = ?"
                val preparedStatement = connection.prepareStatement(query)
                preparedStatement.setInt(1,userId)

                preparedStatement.executeQuery().use { resultSet ->
                    while (resultSet.next()) {
                        list.add(resultSet.getString("token"))
                    }
                }
            }
        } catch(sqlException: SQLException) {
            System.err.println("Error in getFcmToken($userId) ${sqlException.message}")
            sqlException.printStackTrace()
            return listOf()
        }

        return list
    }
}