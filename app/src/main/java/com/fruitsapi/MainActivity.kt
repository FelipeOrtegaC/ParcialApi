package com.fruitsapi

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.GridLayout
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
        val fruits = listOf("Manzana", "Banana", "Cereza", "Dátil", "Elderberry",
            "Higo", "Grosella", "Higo chumbo", "Melón", "Jaca",
            "Kiwi", "Limón", "Mango", "Nectarina", "Naranja",
            "Papaya", "Piña", "Granada", "Frambuesa", "Fresa",
            "Tamarindo", "Uva", "Sandía", "Xigua", "Yaca",
            "Zarzamora", "Aguacate", "Bilimbi", "Carambola", "Durazno")
        val linearLayout = findViewById<GridLayout>(R.id.mi_linear_layout)

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
            button.setBackgroundColor(Color.parseColor("#EBEBE2"))
            // Cambia el estilo del texto a negrita
            button.setTypeface(null, Typeface.BOLD)
            val layoutParams = GridLayout.LayoutParams()
            // Convierte 5dp a píxeles
            val marginsInDp = 5
            val marginsInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginsInDp.toFloat(), resources.displayMetrics).toInt()
            // Establece los márgenes del LayoutParams
            layoutParams.setMargins(marginsInPx, marginsInPx, marginsInPx, marginsInPx)
            button.layoutParams = layoutParams
            button.setOnClickListener {
                val intent = Intent(this@MainActivity, NutrientesActivity::class.java)
                startActivity(intent)
            }
            linearLayout.addView(button)
        }

    }
}