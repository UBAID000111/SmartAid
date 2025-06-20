package com.example.smartaid.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.credentials.provider.Action
import androidx.recyclerview.widget.RecyclerView
import com.example.smartaid.R
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter(private var historyList: List<HistoryEntity>,
    private val onDeleteClick: (HistoryEntity) -> Unit
) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val typeText: TextView = itemView.findViewById(R.id.tvType)
        val messageText: TextView = itemView.findViewById(R.id.tvMessage)
        val timeText: TextView = itemView.findViewById(R.id.tvTimestamp)

        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = historyList[position]
        holder.typeText.text = item.type
        holder.messageText.text = item.message
        holder.timeText.text = formatTimestamp(item.timestamp)

        holder.deleteButton.setOnClickListener {
            onDeleteClick(item)
        }
    }

    override fun getItemCount() = historyList.size

    fun setData(newList: List<HistoryEntity>) {
        historyList = newList
        notifyDataSetChanged()
    }

    private fun formatTimestamp(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}