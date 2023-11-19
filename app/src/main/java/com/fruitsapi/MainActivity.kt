package com.fruitsapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import com.fruitsapi.viewModel.listFilters
import com.frutas.model.ApiService

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fruits = listOf("Apple", "Banana", "Cherry")
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
            button.text = fruit
            button.id = View.generateViewId()
            linearLayout.addView(button)
        }

    }
}