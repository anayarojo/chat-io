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
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    val avatar_tag = "com.anayarojo.chatio.tags.AVATAR"
    val user_tag = "com.anayarojo.chatio.tags.USER"

    private var connected: Boolean = false
    private var avatarIndex: Int = 0
    private var userName: String = "Anónimo"

    private lateinit var socket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lvMessagesHistory = findViewById<ListView>(R.id.main_lvMessagesHistory)
        val etMessage = findViewById<EditText>(R.id.main_etMessage)
        val bnSend = findViewById<Button>(R.id.main_bnSend)

        avatarIndex = intent.getIntExtra(avatar_tag, 0)
        userName = intent.getStringExtra(user_tag)

        prepareMessagesHistory(lvMessagesHistory)
        prepareChat(etMessage, bnSend)
    }

    private fun prepareMessagesHistory(lvMessagesHistory: ListView) {
        var messages: ArrayList<Message> = ArrayList()
        val messageAdapter = MessageAdapter(this, messages)
        lvMessagesHistory.adapter = messageAdapter

        try {
            socket = IO.socket("https://socket-io-chat-test-20190724.herokuapp.com")
            socket.on(Socket.EVENT_CONNECT) {
                connected = true
                runOnUiThread {
                    Toast.makeText(this, "Conectado", Toast.LENGTH_LONG).show()
                }
            }
            socket.on("chat message object") {
                args ->
                runOnUiThread {
                    val json = args[0] as JSONObject
                    val message = Message(
                        json.getString("avatar"),
                        json.getString("user"),
                        json.getString("text"),
                        json.getString("date")
                    )
                    messages.add(message)
                    messageAdapter.notifyDataSetChanged()
                }
            }
            socket.connect()
        } catch (ex: Exception) {
            Toast.makeText(this, ex.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun prepareChat(etMessage: EditText, bnSend: Button){
        val dateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm:ss a")
        bnSend.setOnClickListener {
            if(connected) {
                val message = JSONObject()
                val date = Calendar.getInstance().time
                message.put("avatar", this.avatarIndex.toString())
                message.put("user", this.userName)
                message.put("text", etMessage.text.toString())
                message.put("date", dateFormat.format(date))
                socket.emit("chat message object", message)
                etMessage.setText("")
            } else {
                Toast.makeText(this, "Desconectado :(", Toast.LENGTH_LONG).show()
            }
        }
    }
}
