package push_notifications.data

import persistence.Database
import java.sql.SQLException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PushNotificationsDao {

    fun getFcmTokens(userId: Int): List<String>  {
        val list: MutableList<String> = mutableListOf()
        try {
            Database.getConnection().use {connection ->
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