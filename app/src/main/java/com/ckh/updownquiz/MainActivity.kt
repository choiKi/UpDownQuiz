package com.ckh.updownquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import java.util.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.ckh.updownquiz.databinding.ActivityMainBinding
import kotlin.math.absoluteValue
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val random = java.util.Random()
    private var answered = random.nextInt(100)
    private var count = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val randomNumber = binding.randomNumber
        var upDown = binding.upDown
        val numberPicker1 = binding.numberPicker1.apply {
            minValue = 0
            maxValue = 9
        }
        val numberPicker2 = binding.numberPicker2.apply {
            minValue = 0
            maxValue = 9
        }
        val guessBtn = binding.guessBtn
        val startBtn = binding.startBtn

        startBtn.setOnClickListener {
            Log.d("main", "${answered}")
            guessBtn.isVisible = true
            startBtn.isVisible = false
            binding.count.apply {
                isVisible = true
                text = "Count : 0"
            }
            Toast.makeText(this, "게임이 시작되었습니다.", Toast.LENGTH_SHORT).show()
            upDown.text = "Select Number"
            randomNumber.text = "???"
            return@setOnClickListener
        }
        guessBtn.setOnClickListener {
            val quizValue: Int = "${numberPicker1.value}${numberPicker2.value}".toInt()
            Log.d("main", quizValue.toString())
            count += 1
            binding.count.text = "Count : ${count}"
            if (quizValue < answered) {
                upDown.text = "UP ▲"
            } else if (quizValue > answered) {
                upDown.text = "DOWN ▼"
            } else {
                Toast.makeText(this, "정답!", Toast.LENGTH_SHORT).show()
                upDown.text = "Congraturation"
                randomNumber.text = "${answered}"
                guessBtn.text = "exit"
                return@setOnClickListener
            }
            if (count == 5) {
                var hint: String? = null
                when (answered % 2) {
                    0 -> {
                        hint = "정답은 짝수"
                    }
                    else -> {
                        hint = "정답은 홀수"
                    }
                }
                AlertDialog.Builder(this)
                    .setTitle("5회 시도")
                    .setMessage("Hint : ${hint}")
                    .setPositiveButton("확인") { _, _ -> }
                    .create()
                    .show()
            }
        }

    }
}