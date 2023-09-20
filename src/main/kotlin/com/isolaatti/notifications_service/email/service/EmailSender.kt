package com.isolaatti.notifications_service.email.service

interface EmailSender {

    fun sendEmailToAddress(address: String, subject: String, username: String, htmlBody: String, plainTextBody: String)
}