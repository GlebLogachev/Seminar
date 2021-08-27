package com.glogachev.seminar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.glogachev.seminar.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            btnDownload.setOnClickListener {
                loadLargeFile()
            }
            btnUpdateUi.setOnClickListener {
                updateUiInMainThread()
            }

            btnRight.setOnClickListener {
                updateUi()
            }
        }
    }

    // anr error
    private fun loadLargeFile() {
        TimeUnit.SECONDS.sleep(20)
    }

    // crash app
    private fun updateUiInMainThread() {
        Thread {
            binding.tvExampleText.text = "try to updateUI"
        }.start()
    }

    private fun updateUi() {
        Thread {
            TimeUnit.SECONDS.sleep(5)
            binding.tvExampleText.post {
                binding.tvExampleText.text = "success"
            }
        }.start()
    }
}