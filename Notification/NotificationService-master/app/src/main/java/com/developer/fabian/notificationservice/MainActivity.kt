package com.developer.fabian.notificationservice

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(view: View) {
        val text = findViewById<EditText>(R.id.texto_mensaje)

        val intent = Intent(this, MessageService::class.java)
        intent.putExtra(MessageService.MESSAGE_EXTRA, text.text.toString())
        startService(intent)
    }
}
