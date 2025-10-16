package com.karantech.karantech.service

import com.karantech.karantech.data.ChatHistory
import com.karantech.karantech.repo.ChatRepo
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import kotlin.collections.get

@Service
class ChatService(
    private val customWebClient: WebClient,
    private val chatRepo: ChatRepo
) {

    @Value("\${openai.api.key}")
    lateinit var apiKey: String

    fun generateText(prompt: String): Mono<String> {
        val requestBody = mapOf(
            "model" to "gpt-4",
            "messages" to listOf(mapOf("role" to "user", "content" to prompt))
        )

        return customWebClient.post()
            .uri("https://api.openai.com/v1/chat/completions")
            .header("Authorization", "Bearer $apiKey")
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(Map::class.java)
            .map { body ->
                val text = ((body["choices"] as List<Map<String, Any>>)[0]["message"] as Map<String, String>)["content"]
                text ?: "No response"
            }.onErrorMap { error -> error.cause ?: error  }
    }

    fun generateImage(prompt: String): Mono<String> {
        val requestBody = mapOf(
            "prompt" to prompt,
            "size" to "512x512"
        )

        return customWebClient.post()
            .uri("https://api.openai.com/v1/images/generations")
            .header("Authorization", "Bearer $apiKey")
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(Map::class.java)
            .map { body ->
                val data = body["data"] as List<Map<String, String>>
                data[0]["url"] ?: "No Image URL"
            }
    }

    fun saveHistory(prompt: String, response: String, imageUrl: String?, zipcode: String?) {
        chatRepo.save(ChatHistory(prompt = prompt, response = response, imageUrl = imageUrl, zipcode = zipcode))
    }
}
