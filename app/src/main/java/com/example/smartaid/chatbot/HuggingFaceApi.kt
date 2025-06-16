package com.example.smartaid.chatbot


import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.Call

interface HuggingFaceApi {
    @Headers (
        "Authorization: Bearer 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",
        "Content-Type: application/json"
    )
    @POST("featherless-ai/v1/chat/completions")
    fun getChatCompletion(@Body request: ChatRequest): Call<ChatCompletionResponse>
}



