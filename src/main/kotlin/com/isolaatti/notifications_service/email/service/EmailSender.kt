package com.isolaatti.notifications_service.email.service

interface EmailSender {

    fun sendEmailToAddress(emailDto: EmailDto)
}