package com.fruitsapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.compose.runtime.remember
import com.fruitsapi.viewModel.listFilters
import com.frutas.model.ApiService
import com.frutas.model.Fruit
import com.frutas.viewModel.RetrofitService

class MainActivity : AppCompatActivity() {
    private val service = RetrofitService()
    var filter = 0
    var checked = false
    var sizeSearch = 0
    var allFruitsByFilter = emptyList<Fruit>()
    var listFruit = emptyList<Fruit>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val fruits = listOf(service.fruits)
        val linearLayout = findViewById<LinearLayout>(R.id.mi_linear_layout)

        // Obtén una referencia al Spinner desde el XML
        val spinner: Spinner = findViewById(R.id.spinner)

        // Crea un adaptador utilizando un array de la lista de filtros
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listFilters)

        // Especifica el diseño para la lista de opciones (puedes usar el diseño predeterminado o personalizarlo)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Asigna el adaptador al Spinner
        spinner.adapter = adapter

        for (fruit in fruits) {
            val button = Button(this)
            //button.text = fruit
            button.id = View.generateViewId()
            button.setOnClickListener {
                val intent = Intent(this@MainActivity, NutrientesActivity::class.java)
                startActivity(intent)
            }
            linearLayout.addView(button)
        }

    }
}