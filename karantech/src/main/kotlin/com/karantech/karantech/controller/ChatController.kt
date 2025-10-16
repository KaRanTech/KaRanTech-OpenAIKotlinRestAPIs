package com.karantech.karantech.controller

import com.karantech.karantech.service.ChatService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

data class RequestPayload(
    val prompt: String,
    val zipcode: String? = null
)

@RestController
@RequestMapping("/api/chat")
class ChatController(private val chatService: ChatService) {

    @PostMapping("/text")
    fun generateText(@RequestBody payload: RequestPayload): Mono<ResponseEntity<String>> {
        return chatService.generateText(payload.prompt)
            .flatMap { response ->
                // Save history asynchronously
                chatService.saveHistory(payload.prompt, response, null, payload.zipcode)
                Mono.just(ResponseEntity.ok(response))
            }
            .onErrorResume { e ->
                Mono.just(ResponseEntity.status(500).body("Error generating text: ${e.message}"))
            }
    }

    @PostMapping("/image")
    fun generateImage(@RequestBody payload: RequestPayload): Mono<ResponseEntity<String>> {
        return chatService.generateImage(payload.prompt)
            .flatMap { imageUrl ->
                // Save history asynchronously
                chatService.saveHistory(payload.prompt, "Image Generated", imageUrl, payload.zipcode)
                Mono.just(ResponseEntity.ok(imageUrl))
            }
            .onErrorResume { e ->
                Mono.just(ResponseEntity.status(500).body("Error generating image: ${e.message}"))
            }
    }
}
