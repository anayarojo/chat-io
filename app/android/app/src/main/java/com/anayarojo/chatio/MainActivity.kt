package com.anayarojo.chatio

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import kotlin.collections.ArrayList
import io.socket.client.IO
import io.socket.client.Socket
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var connected: Boolean = false
    private lateinit var socket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lvMessagesHistory = findViewById<ListView>(R.id.lvMessagesHistory)
        val etMessage = findViewById<EditText>(R.id.etMessage)
        val bnSend = findViewById<Button>(R.id.bnSend)

        prepareMessagesHistory(lvMessagesHistory)
        prepareChat(etMessage, bnSend)
    }

    private fun prepareMessagesHistory(lvMessagesHistory: ListView) {
        var messages: ArrayList<Message> = ArrayList()
        val dateFormat = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val messageAdapter = MessageAdapter(this, messages)
        lvMessagesHistory.adapter = messageAdapter

        try {
            socket = IO.socket("https://socket-io-chat-test-20190724.herokuapp.com")
            socket.on(Socket.EVENT_CONNECT) {
                connected = true
                runOnUiThread {
                    Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show()
                }
            }
            socket.on("chat message") {
                args ->
                runOnUiThread {
                    var date: Date = Calendar.getInstance().time
                    messages.add(Message(args[0].toString(), dateFormat.format(date)))
                    messageAdapter.notifyDataSetChanged()
                }
            }
            socket.connect()
        } catch (ex: Exception) {
            Toast.makeText(this, ex.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun prepareChat(etMessage: EditText, bnSend: Button){
        bnSend.setOnClickListener {
            if(connected) {
                socket.emit("chat message", etMessage.text.toString())
                etMessage.setText("")
            } else {
                Toast.makeText(this, "No connection :(", Toast.LENGTH_LONG).show()
            }
        }
    }
}
