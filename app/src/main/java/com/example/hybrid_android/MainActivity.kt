package com.example.hybrid_android

import MessageData
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hybrid_android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.includes.buttonFirst.setOnClickListener {
            startActivityForResult(
                FlutterHybridActivity.newIntent(
                    this,
                    MessageData(title = "Je suis un titre", subtitle = "Je suis un sous-titre")
                ),
                2000
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data !=null) {
            val messageData = FlutterHybridActivity.getFromResultIntent(data)
            binding.includes.textviewFirst.text = "${messageData.title} ${messageData.subtitle}"
        }
    }
}