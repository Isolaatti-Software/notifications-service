package com.isolaatti.notifications_service.email.service

import kotlinx.serialization.Serializable

@Serializable
data class EmailDto(
    val fromAddress: String,
    val fromName: String,
    val toAddress: String,
    val toName: String,
    val subject: String,
    val htmlBody: String
)