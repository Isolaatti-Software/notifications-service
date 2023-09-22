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

    private val sendgrid = SendGrid(Config.config.sendgridApiKey)
    override fun sendEmailToAddress(emailDto: EmailDto) {
        val to = Email(emailDto.toAddress, emailDto.toName)
        val from = Email(emailDto.fromAddress, emailDto.fromName)
        val content = Content("text/html", emailDto.htmlBody)
        val mail = Mail(from, emailDto.subject, to, content)

        val request = Request()

        try {
            request.method = Method.POST
            request.endpoint = "mail/send"
            request.body = mail.build()

            sendgrid.api(request)
        } catch(e: IOException) {
            println("Error sending email")
            e.printStackTrace()
        }
    }
}