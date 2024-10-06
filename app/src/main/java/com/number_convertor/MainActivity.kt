package com.number_convertor

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var inputEditText: EditText
    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var convertButton: Button
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputEditText = findViewById(R.id.editText)
        spinnerFrom = findViewById(R.id.spinnerFrom)
        spinnerTo = findViewById(R.id.spinnerTo)
        convertButton = findViewById(R.id.buttonConvert)
        resultTextView = findViewById(R.id.textViewResult)

        setupSpinners()
        setupConvertButton()
    }

    private fun setupSpinners() {
        val numberSystems = arrayOf("Binary", "Decimal", "Octal", "Hexadecimal")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, numberSystems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter
    }

    @SuppressLint("SetTextI18n")
    private fun setupConvertButton() {
        convertButton.setOnClickListener {
            val inputNumber = inputEditText.text.toString().trim()
            val fromSystem = spinnerFrom.selectedItem.toString()
            val toSystem = spinnerTo.selectedItem.toString()

            val result = if (inputNumber.isNotEmpty()) {
                convertNumber(inputNumber, fromSystem, toSystem)
            } else {
                getString(R.string.enter_number)
            }

            resultTextView.text = "${getString(R.string.result)} $result"
        }
    }

    private fun convertNumber(input: String, from: String, to: String): String {
        return try {

            val number: Double = when (from) {
                "Binary" -> Integer.parseInt(input.split(".")[0], 2).toDouble()
                "Decimal" -> input.toDouble()
                "Octal" -> Integer.parseInt(input.split(".")[0], 8).toDouble()
                "Hexadecimal" -> Integer.parseInt(input.split(".")[0], 16).toDouble()
                else -> throw IllegalArgumentException("Unsupported 'from' base")
            }


            when (to) {
                "Binary" -> number.toInt().toString(2)
                "Decimal" -> number.toString()
                "Octal" -> number.toInt().toString(8)
                "Hexadecimal" -> number.toInt().toString(16).uppercase()
                else -> throw IllegalArgumentException("Unsupported 'to' base")
            }
        } catch (e: NumberFormatException) {
            getString(R.string.invalid_number_format)
        } catch (e: IllegalArgumentException) {
            e.message ?: getString(R.string.error)
        } catch (e: Exception) {
            getString(R.string.error)
        }
    }
}
