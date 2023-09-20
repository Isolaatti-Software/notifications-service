package com.isolaatti.notifications_service.email.service

import com.isolaatti.notifications_service.Config
import com.sendgrid.Method
import com.sendgrid.Request
import com.sendgrid.SendGrid
import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.Content
import com.sendgrid.helpers.mail.objects.Email
import java.io.IOException

class EmailSenderImpl : EmailSender {
    companion object {
        const val EMAIL_ADDRESS = "notifications@isolaatti.com"
        const val EMAIL_NAME = "Isolaatti"
    }

    private val sendgrid = SendGrid(Config.config.sendgridApiKey)
    override fun sendEmailToAddress(address: String, subject: String, username: String, htmlBody: String, plainTextBody: String) {
        val to = Email(address, username)
        val from = Email(EMAIL_ADDRESS, EMAIL_NAME)
        val content = Content("text/html", htmlBody)
        val mail = Mail(from, subject, to, content)

        val request = Request()

        try {
            request.method = Method.POST
            request.endpoint = "mail/send"
            request.body = mail.build()

            sendgrid.api(request)
        } catch(_: IOException) {
            // TODO log this
        }
    }
}