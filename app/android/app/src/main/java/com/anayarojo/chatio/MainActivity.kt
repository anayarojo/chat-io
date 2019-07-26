package com.anayarojo.chatio

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lvMessagesHistory = findViewById<ListView>(R.id.lvMessagesHistory)
        val etMessage = findViewById<EditText>(R.id.etMessage)
        val bnSend = findViewById<Button>(R.id.bnSend)

        val dateFormat = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        var messages: ArrayList<Message> = ArrayList()
        val messageAdapater = MessageAdapter(this, messages)
        lvMessagesHistory.adapter = messageAdapater

        bnSend.setOnClickListener {
            var date: Date = Calendar.getInstance().time
            messages.add(Message(etMessage.text.toString(), dateFormat.format(date)))
            messageAdapater.notifyDataSetChanged();
            etMessage.setText("")
            etMessage.clearFocus()
        }
    }
}
