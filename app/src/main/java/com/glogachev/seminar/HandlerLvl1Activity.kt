package com.glogachev.seminar

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import com.glogachev.seminar.databinding.ActivityHandlerLvl1Binding

class HandlerLvl1Activity : AppCompatActivity() {
    private val binding by lazy {
        ActivityHandlerLvl1Binding.inflate(layoutInflater)
    }
    private val handler by lazy {
        Handler(Looper.getMainLooper())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.root.forEach {
            if (it is Button) {
                it.setOnClickListener(buttonsClickListener)
            }
        }
        // рассмотриваем отдельно, т.к хотим выполняться в main потоке
        binding.btnTest.setOnClickListener {
            btnTestClicked()
        }
    }

    // post - позволяет выполнить код внутри метода в потоке, к которому привязан handler
    private val buttonsClickListener = View.OnClickListener {
        Thread {
            when (it.id) {
//     * 1 *            R.id.btnEnableDisable -> postWithRunnable()
                R.id.btnEnableDisable -> handler.post { toggleButtonState() }
                R.id.btnChangeColor -> handler.post { nextRandomColor() }

                R.id.btnEnableDisableDelay -> handler.postDelayed({ toggleButtonState() }, DELAY)
                R.id.btnChangeColorDelay -> handler.postDelayed({ nextRandomColor() }, DELAY)

                R.id.btnChangeColorDelayToken -> {
                    handler.postDelayed({ nextRandomColor() }, TOKEN, DELAY)
                }
                R.id.btnShowToastDelay -> handler.postDelayed({ showToast() }, TOKEN, DELAY)
                R.id.btnCancel -> handler.removeCallbacksAndMessages(TOKEN)
//                * 3 * Альтернативный кэнселинг без токена
//                R.id.btnCancel -> {
//                    cancelWithoutToken()
//                }
            }
        }.start()

    }

    // * 3 * p.s не забыть удалить токен и заменить runnable-ы в вызовах
    private fun cancelWithoutToken() {
        handler.removeCallbacks(nextRandomColorRunnable)
        handler.removeCallbacks(showToastRunnable)
    }

    // * 3 *
    private val nextRandomColorRunnable = Runnable {
        nextRandomColor()
    }
    private val showToastRunnable = Runnable {
        showToast()
    }

    private fun showToast() {
        Toast.makeText(this, "Toast", Toast.LENGTH_LONG).show()
    }

    private fun nextRandomColor() {
        val randomColor = colors.random()
        binding.imageViewTest.setBackgroundColor(randomColor)
    }

    private fun toggleButtonState() {
        binding.btnTest.isEnabled = !binding.btnTest.isEnabled
    }

    // * 1 * метод post с помощью Runnable obj
    private fun postWithRunnable() {
        val myRun: Runnable = MyRun()
        handler.post(myRun)
    }

    inner class MyRun() : Runnable {
        override fun run() {
            toggleButtonState()
        }
    }


    private fun btnTestClicked() {
        Thread {
            runOnUiThread {
                showToast()
            }
            handler.post { Log.d(TAG, "post") }
            handler.postAtFrontOfQueue { Log.d(TAG, "postAtFrontOfQueue") }
        }.start()
    }
}
