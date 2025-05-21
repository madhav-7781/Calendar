package com.example.calendar

import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val spinner = findViewById<Spinner>(R.id.spinner2)
        val gridView = findViewById<GridView>(R.id.gridView)

        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, months)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long
            ) {
                val selectedMonth = position
                val daysInMonth = getDaysInMonth(selectedMonth)
                val days = (1..daysInMonth).map { it.toString() }

                val dayAdapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, days)
                gridView.adapter = dayAdapter

                gridView.setOnItemClickListener { _, _, pos, _ ->
                    val day = days[pos]
                    Toast.makeText(this@MainActivity, "Selected: ${months[position]} $day", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinner.setSelection(Calendar.getInstance().get(Calendar.MONTH))
    }

    private fun getDaysInMonth(month: Int): Int {
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val calendar = Calendar.getInstance()
        calendar.set(year, month, 1)
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }
}
