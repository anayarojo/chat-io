package com.anayarojo.chatio

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    val avatar_tag = "com.anayarojo.chatio.tags.AVATAR"
    val user_tag = "com.anayarojo.chatio.tags.USER"

    private var avatarIndex: Int = 0
    private var userName: String = "Anónimo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val ivAvatar = findViewById<ImageView>(R.id.login_ivAvatar)
        val etUserName = findViewById<EditText>(R.id.login_etName)
        val bnChangeAvatar = findViewById<Button>(R.id.login_bnChangeAvatar)
        val bnJoin = findViewById<Button>(R.id.login_bnJoin)
        val bnContinue = findViewById<Button>(R.id.login_bnContinue)

        changeAvatar(ivAvatar)
        bnChangeAvatar.setOnClickListener {
            changeAvatar(ivAvatar)
        }

        bnJoin.setOnClickListener {
            login(etUserName.text.toString())
        }

        bnContinue.setOnClickListener {
            login("")
        }
    }

    private fun changeAvatar(ivAvatar: ImageView){
        this.avatarIndex = (1..20).random()
        val resourceImage = this.getResources().getIdentifier("ic_user_profile_${this.avatarIndex}", "drawable", this.getPackageName())
        ivAvatar.setImageResource(resourceImage)
    }

    private fun login(userName: String) {

        if(userName != ""){
            this.userName = userName
            Toast.makeText(this, "Bienvenido ${this.userName}", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(this, "Has ingresado como anónimo", Toast.LENGTH_SHORT).show()
        }

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(this.avatar_tag, this.avatarIndex)
        intent.putExtra(this.user_tag, this.userName)
        startActivity(intent)
    }
}
