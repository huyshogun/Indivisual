package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvDisplay: TextView
    private var currentNumber = ""
    private var operator = ""
    private var firstNumber: Int? = null
    private var secondNumber: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDisplay = findViewById(R.id.tvDisplay)

        // Initialize buttons
        val numberButtons = listOf(
            findViewById<Button>(R.id.btn0),
            findViewById<Button>(R.id.btn1),
            findViewById<Button>(R.id.btn2),
            findViewById<Button>(R.id.btn3),
            findViewById<Button>(R.id.btn4),
            findViewById<Button>(R.id.btn5),
            findViewById<Button>(R.id.btn6),
            findViewById<Button>(R.id.btn7),
            findViewById<Button>(R.id.btn8),
            findViewById<Button>(R.id.btn9)
        )
        val operators = mapOf(
            R.id.btnPlus to "+",
            R.id.btnMinus to "-",
            R.id.btnMultiply to "*",
            R.id.btnDivide to "/"
        )

        numberButtons.forEachIndexed { index, button ->
            button.setOnClickListener { appendNumber(index.toString()) }
        }

        findViewById<Button>(R.id.btnC).setOnClickListener { clear() }
        findViewById<Button>(R.id.btnCE).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.btnBS).setOnClickListener { backspace() }
        findViewById<Button>(R.id.btnPlusMinus).setOnClickListener { toggleSign() }
        findViewById<Button>(R.id.btnEqual).setOnClickListener { calculate() }

        operators.forEach { (id, symbol) ->
            findViewById<Button>(id).setOnClickListener { setOperator(symbol) }
        }
    }

    private fun appendNumber(number: String) {
        currentNumber += number
        tvDisplay.text = currentNumber
    }

    private fun setOperator(op: String) {
        if (currentNumber.isNotEmpty()) {
            firstNumber = currentNumber.toInt()
            operator = op
            currentNumber = ""
        }
    }

    private fun calculate() {
        if (currentNumber.isNotEmpty() && firstNumber != null) {
            secondNumber = currentNumber.toInt()
            val result = when (operator) {
                "+" -> firstNumber!! + secondNumber!!
                "-" -> firstNumber!! - secondNumber!!
                "*" -> firstNumber!! * secondNumber!!
                "/" -> if (secondNumber == 0) 0 else firstNumber!! / secondNumber!!
                else -> 0
            }
            tvDisplay.text = result.toString()
            clearAll()
        }
    }

    private fun toggleSign() {
        if (currentNumber.isNotEmpty()) {
            currentNumber = (currentNumber.toInt() * -1).toString()
            tvDisplay.text = currentNumber
        }
    }

    private fun backspace() {
        if (currentNumber.isNotEmpty()) {
            currentNumber = currentNumber.dropLast(1)
            tvDisplay.text = currentNumber.ifEmpty { "0" }
        }
    }

    private fun clear() {
        currentNumber = ""
        tvDisplay.text = "0"
    }

    private fun clearAll() {
        clear()
        firstNumber = null
        secondNumber = null
        operator = ""
    }
}
