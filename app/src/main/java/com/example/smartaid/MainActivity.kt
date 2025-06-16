package com.example.smartaid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.smartaid.chatbot.ChatMessage
import com.example.smartaid.chatbot.ChatRequest
import com.example.smartaid.chatbot.ChatCompletionResponse
import com.example.smartaid.chatbot.RetrofitInstance
import com.example.smartaid.chatbot.MessageAdapter
import com.example.smartaid.chatbot.Message
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.inputmethod.EditorInfo
class MainActivity : AppCompatActivity() {

    private lateinit var inputEditText: EditText
    private lateinit var sendButton: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageAdapter: MessageAdapter
    private val messages = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputEditText = findViewById(R.id.inputEditText)
        sendButton = findViewById(R.id.sendButton)
        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.recyclerView)

        messageAdapter = MessageAdapter(messages)
        recyclerView.adapter = messageAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        inputEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val userInput = inputEditText.text.toString().trim()
                if (userInput.isNotEmpty()) {
                    addMessage(userInput, true)
                    inputEditText.setText("")
                    getChatResponse(userInput)
                } else {
                    Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show()
                }
                true
            } else {
                false
            }
        }


        sendButton.setOnClickListener {
            val userInput = inputEditText.text.toString().trim()
            if (userInput.isNotEmpty()) {
                addMessage(userInput, true)
                inputEditText.setText("")
                getChatResponse(userInput)
            } else {
                Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show()
            }
        }
    }



    private fun addMessage(content: String, isUser: Boolean) {
        messages.add(Message(content, isUser))
        messageAdapter.notifyItemInserted(messages.size - 1)
        recyclerView.scrollToPosition(messages.size - 1)
    }

    private fun getChatResponse(prompt: String) {
        val messagesPayload = listOf(ChatMessage(role = "user", content = prompt))
        val request = ChatRequest(
            messages = messagesPayload,
            model = "mistralai/Magistral-Small-2506",
            stream = false
        )

        progressBar.visibility = View.VISIBLE

        RetrofitInstance.api.getChatCompletion(request).enqueue(object : Callback<ChatCompletionResponse> {
            override fun onResponse(
                call: Call<ChatCompletionResponse>,
                response: Response<ChatCompletionResponse>
            ) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful && response.body() != null) {
                    val reply = response.body()!!.choices?.get(0)?.message?.content
                    if (reply != null) {
                        addMessage(reply, false)
                    } else {
                        Toast.makeText(this@MainActivity, "No chat reply found", Toast.LENGTH_LONG).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("MainActivity", "API error: ${response.code()}, $errorBody")
                    Toast.makeText(this@MainActivity, "Failed to get response", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ChatCompletionResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                Log.e("MainActivity", "API call failed: ${t.message}", t)
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}