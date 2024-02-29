import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.isolaatti.notifications_service.Config
import com.isolaatti.notifications_service.email.service.EmailQueueConsumer
import com.isolaatti.notifications_service.messaging.Rabbitmq
import com.isolaatti.notifications_service.push_notifications.PushNotificationsQueueConsumer
import com.isolaatti.notifications_service.push_notifications.RegisterDeviceQueueConsumer
import com.rabbitmq.client.BuiltinExchangeType
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.jvm.optionals.getOrNull


const val EXCHANGE = "default_exchange"
const val PUSH_NOTIFICATION_SEND_QUEUE = "notification_send_queue"
const val EMAIL_SEND_QUEUE = "email_send_queue"
const val EMAIL_ROUTING_KEY = "routing_email"
const val PUSH_NOTIFICATIONS_ROUTING_KEY = "routing_push_notifications"
const val PUSH_NOTIFICATIONS_REGISTER_DEVICE_QUEUE = "push_notifications_register_device_queue"
const val PUSH_NOTIFICATIONS_REGISTER_DEVICE_ROUTING_KEY = "routing_push_notifications_register_device"
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

    channel?.exchangeDeclare(EXCHANGE, BuiltinExchangeType.DIRECT, true)

    channel?.queueDeclare(EMAIL_SEND_QUEUE, true, false, false, null)
    channel?.queueBind(EMAIL_SEND_QUEUE, EXCHANGE, EMAIL_ROUTING_KEY)
    channel?.basicConsume(EMAIL_SEND_QUEUE, true, EmailQueueConsumer(channel))

    channel?.queueDeclare(PUSH_NOTIFICATION_SEND_QUEUE, true, false, false, null)
    channel?.queueBind(PUSH_NOTIFICATION_SEND_QUEUE, EXCHANGE, PUSH_NOTIFICATIONS_ROUTING_KEY)
    channel?.basicConsume(PUSH_NOTIFICATION_SEND_QUEUE, true, PushNotificationsQueueConsumer(channel))

    channel?.queueDeclare(PUSH_NOTIFICATIONS_REGISTER_DEVICE_QUEUE, true, false, false, null)
    channel?.queueBind(PUSH_NOTIFICATIONS_REGISTER_DEVICE_QUEUE, EXCHANGE, PUSH_NOTIFICATIONS_REGISTER_DEVICE_ROUTING_KEY)
    channel?.basicConsume(PUSH_NOTIFICATIONS_REGISTER_DEVICE_QUEUE, true, RegisterDeviceQueueConsumer(channel))
}
