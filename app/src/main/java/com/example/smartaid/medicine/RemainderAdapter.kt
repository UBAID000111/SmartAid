package com.example.smartaid.medicine

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartaid.databinding.ItemReminderBinding

class ReminderAdapter(
    private val reminders: List<ReminderModel>,
    private val onDeleteClick: (Int, Int) -> Unit
) : RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    inner class ReminderViewHolder(val binding: ItemReminderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val binding = ItemReminderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReminderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder = reminders[position]
        holder.binding.tvTag.text = reminder.tag
        holder.binding.tvTime.text = reminder.time

        holder.binding.btnDelete.setOnClickListener {
            onDeleteClick(reminder.requestCode, position)
        }
    }

    override fun getItemCount() = reminders.size
}