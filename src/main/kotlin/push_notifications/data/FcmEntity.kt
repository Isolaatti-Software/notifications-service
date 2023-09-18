package push_notifications.data

data class FcmEntity(
    val id: Long,
    val userId: Int,
    val token: String
)