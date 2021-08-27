package com.glogachev.seminar

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.glogachev.seminar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnHandlerLvl1.setOnClickListener {
            val intent = Intent(this, HandlerLvl1Activity::class.java)
            startActivity(intent)
        }
        binding.btnHandlerLvl2.setOnClickListener {
            val intent = Intent(this, HandlerLvl2Activity::class.java)
            startActivity(intent)
        }
    }
}