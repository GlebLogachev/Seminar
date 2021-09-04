package com.glogachev.seminar

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import com.glogachev.seminar.databinding.ActivityHandlerLvl1Binding

class HandlerLvl2Activity : AppCompatActivity() {
    private val binding by lazy {
        ActivityHandlerLvl1Binding.inflate(layoutInflater)
    }
//обработка на стороне самого handler
    private val handler = Handler(Looper.getMainLooper()) {
        when (it.what) {
            TOGGLE_MESSAGE -> toggleButtonState()
            NEXT_COLOR_MESSAGE -> nextRandomColor()
            TOAST_MESSAGE -> showToast()

        }
    // сообщаем системе, что сообщение успешно обработали
        return@Handler true
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.root.forEach {
            if (it is Button) {
                it.setOnClickListener(buttonsClickListener)
            }
        }
    }

    private val buttonsClickListener = View.OnClickListener {
        Thread {
            when (it.id) {
                R.id.btnEnableDisable -> {
                    handler.sendMessage(Message().apply {
                        what = TOGGLE_MESSAGE
                    })
                }
                R.id.btnChangeColor -> {
                    handler.obtainMessage(NEXT_COLOR_MESSAGE).also {
                        handler.sendMessage(it)
                    }
                }

                R.id.btnEnableDisableDelay -> {
                    val message = Message.obtain(handler, TOGGLE_MESSAGE)
                    handler.sendMessageDelayed(message, DELAY)
                }
                R.id.btnChangeColorDelay -> {
                    val message = Message.obtain(handler) {
                        Log.d(TAG, "callback called")
                        nextRandomColor()
                    }
                    handler.sendMessageDelayed(message, DELAY)
                }

                R.id.btnChangeColorDelayToken -> {
                    val message = handler.obtainMessage(NEXT_COLOR_MESSAGE)
                    message.obj = TOKEN
                    handler.sendMessageDelayed(message, DELAY)
                }
                R.id.btnShowToastDelay -> {
                    val message = handler.obtainMessage(TOAST_MESSAGE)
                    message.obj = TOKEN
                    handler.sendMessageDelayed(message, DELAY)
                }
                R.id.btnCancel -> handler.removeCallbacksAndMessages(TOKEN)
            }
        }.start()

    }

    private fun toggleButtonState() {
        binding.btnTest.isEnabled = !binding.btnTest.isEnabled
    }

    private fun nextRandomColor() {
        val randomColor = colors.random()
        binding.imageViewTest.setBackgroundColor(randomColor)
    }

    private fun showToast() {
        Toast.makeText(this, "Toast", Toast.LENGTH_LONG).show()
    }
}