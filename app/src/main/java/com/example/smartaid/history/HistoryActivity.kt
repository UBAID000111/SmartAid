package com.example.smartaid.history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartaid.R
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var historyList: List<HistoryEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        historyRecyclerView = findViewById(R.id.rvHistory)
        historyRecyclerView.layoutManager = LinearLayoutManager(this)
        loadHistory()

    }

    private fun loadHistory() {

        val db = AppDatabase.getDatabase(this)
        db.historyDao().getAllHistory().observe(this) { historyItems ->
            historyAdapter = HistoryAdapter(historyItems) { historyItem ->
                lifecycleScope.launch {
                    db.historyDao().delete(historyItem)
                }
            }
            historyRecyclerView.adapter = historyAdapter
        }
    }
}