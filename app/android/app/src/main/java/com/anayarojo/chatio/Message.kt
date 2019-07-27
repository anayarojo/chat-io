package com.anayarojo.chatio

class Message(avatar: String, user: String, text: String, date: String) {

    var avatar: String = "1"
    var user: String = "Anónimo"
    var text: String = ""
    var date: String = ""

    init {
        this.avatar = avatar
        this.user = user
        this.text = text
        this.date = date
    }
}