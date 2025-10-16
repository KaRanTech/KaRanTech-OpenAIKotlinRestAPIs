package com.karantech.karantech.data

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("chat_history")
data class ChatHistory(
    @Id
    val id: Long? = null,
    val prompt: String,
    val response: String,
    val imageUrl: String? = null,
    val zipcode: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
