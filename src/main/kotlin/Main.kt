import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.isolaatti.notifications_service.Config
import com.isolaatti.notifications_service.email.service.EmailQueueConsumer
import com.isolaatti.notifications_service.messaging.Rabbitmq
import com.rabbitmq.client.BuiltinExchangeType
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.jvm.optionals.getOrNull


const val EXCHANGE = "default_exchange"
const val NOTIFICATION_SEND_QUEUE = "notification_send_queue"
const val EMAIL_SEND_QUEUE = "email_send_queue"
const val EMAIL_ROUTING_KEY = "routing_email"
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
}
