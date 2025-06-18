package com.example.smartaid.medicine

import okhttp3.Request

data class ReminderModel(
    val requestCode: Int,
    val tag: String,
    val time: String
)
