package com.fruitsapi.View

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.fruitsapi.R
import com.fruitsapi.databinding.ActivityMainBinding
import com.fruitsapi.viewModel.listFilters
import com.frutas.model.Fruit
import com.frutas.viewModel.RetrofitService


class MainActivity : AppCompatActivity() {
    private val viewModel: RetrofitService by viewModels()
    private val service = RetrofitService()
    var filter = 0
    var checked = false
    var sizeSearch = 0
    var allFruitsByFilter = emptyList<Fruit>()
    var listFruit = emptyList<Fruit>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // Observa los cambios en fruits y actualiza la interfaz de usuario

        viewModel.iniciarProcesoAsincrono()


        val spinner: Spinner = findViewById(R.id.spinner)
        // Crea un adaptador utilizando un array de la lista de filtros
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listFilters)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Asigna el adaptador al Spinner
        spinner.adapter = adapter


        viewModel.fruits.observe(this, Observer { fruits ->
            if (spinner.isActivated){

            }else{
                updateUIWithFruits(fruits)
            }

        })

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                val selectedFilterPosition = position
                orderFruitBynutreint(selectedFilterPosition, viewModel.fruits.value ?: emptyList())
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }

        var Edtxt:EditText = findViewById(R.id.editTextText)
        Edtxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val NameFruit = s.toString()
                showResult(NameFruit)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })


    }

    private fun orderFruitBynutreint(Index: Int, fruits: List<Fruit>){
        when (Index) {
            0 -> {
                // Ordenar por calorías
                val sortedFruits = fruits.sortedBy { it.nutritions.calories }
                updateUIWithFruits(sortedFruits)
            }
            1 -> {
                // Ordenar por grasas
                val sortedFruits = fruits.sortedBy { it.nutritions.fat }
                updateUIWithFruits(sortedFruits)
            }
            2 -> {
                // Ordenar por azucares
                val sortedFruits = fruits.sortedBy { it.nutritions.sugar }
                updateUIWithFruits(sortedFruits)
            }
            3 -> {
                // Ordenar por carbohidratos
                val sortedFruits = fruits.sortedBy { it.nutritions.carbohydrates }
                updateUIWithFruits(sortedFruits)
            }
            4 -> {
                // Ordenar por proteinas
                val sortedFruits = fruits.sortedBy { it.nutritions.protein }
                updateUIWithFruits(sortedFruits)
            }
            else -> {
                updateUIWithFruits(fruits)
            }
        }
    }

    private fun updateUIWithFruits(fruits: List<Fruit>) {
        val linearLayout = findViewById<GridLayout>(R.id.mi_linear_layout)
        linearLayout.removeAllViews()

        var cont: Int = 0

        for (fruit in fruits) {
            cont++
            val button = Button(this)
            button.text = fruit.name
            button.id = View.generateViewId()
            button.setBackgroundColor(Color.parseColor("#FFFFFF"))
            button.setTextColor(Color.BLACK)
            // Resto del código...
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
                intent.putExtra("Fruta", fruit.name)
                startActivity(intent)
            }
            linearLayout.addView(button)
        }

        val txtNumProd : TextView = findViewById(R.id.totalprod)
        txtNumProd.text = cont.toString() + " Productos Encontrados"
    }

    private fun showResult(name: String) {
        val linearLayout = findViewById<GridLayout>(R.id.mi_linear_layout)
        linearLayout.removeAllViews()

        val matchingFruit = viewModel.searchFruitsByName(name)

        if (matchingFruit != null) {
            val button = Button(this)
            button.text = name.uppercase()
            button.id = View.generateViewId()
            button.setBackgroundColor(Color.parseColor("#FFFFFF"))
            button.setTextColor(Color.BLACK)
            button.setTypeface(null, Typeface.BOLD)

            val layoutParams = GridLayout.LayoutParams()
            // Convierte 5dp a píxeles
            val marginsInDp = 5
            val marginsInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginsInDp.toFloat(), resources.displayMetrics).toInt()
            // Establece los márgenes del LayoutParams
            layoutParams.setMargins(marginsInPx, marginsInPx, marginsInPx, marginsInPx)
            button.layoutParams = layoutParams

            button.setOnClickListener {
                // Lógica para manejar el clic en el botón, por ejemplo, abrir NutrientesActivity
                val intent = Intent(this@MainActivity, NutrientesActivity::class.java)
                intent.putExtra("Fruta", name)
                startActivity(intent)
            }

            linearLayout.addView(button)

            val txtNumProd : TextView = findViewById(R.id.totalprod)
            txtNumProd.text = "1 Producto Encontrado"
        } else {
            // Manejar el caso en que no se encuentre ninguna fruta
            val txtNumProd : TextView = findViewById(R.id.totalprod)
            txtNumProd.text = "No se encontraron productos"
        }
    }

}



