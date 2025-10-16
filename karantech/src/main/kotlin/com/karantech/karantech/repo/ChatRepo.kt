package com.karantech.karantech.repo

import com.karantech.karantech.data.ChatHistory

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatRepo: ReactiveCrudRepository<ChatHistory, Long> {
}