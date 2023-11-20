package com.fruitsapi.View

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.fruitsapi.R
import com.fruitsapi.databinding.ActivityMainBinding
import com.fruitsapi.databinding.ActivityNutrientesBinding
import com.frutas.model.Fruit
import com.frutas.viewModel.RetrofitService

class NutrientesActivity : AppCompatActivity() {

    private val viewModel: RetrofitService by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrientes)

        val name: String? = intent.getStringExtra("Fruta")

        val binding: ActivityNutrientesBinding = DataBindingUtil.setContentView(this, R.layout.activity_nutrientes)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.iniciarProcesoAsincrono()

        viewModel.fruits.observe(this@NutrientesActivity, Observer { fruits ->

            if (name != null) {
                fruitNutrient(name, fruits)
            }
            else{
                val txt: TextView = findViewById(R.id.fruitname)
                txt.text = "Esta fruta no existe!!!"
            }
        })


        var back : Button = findViewById(R.id.buttonBack)

        back.setOnClickListener{
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }

    private fun fruitNutrient(name:String, fruit: List<Fruit>){
        for (fr in fruit){
            if (name.uppercase() == fr.name.uppercase()){
                var txt: TextView

                txt = findViewById(R.id.fruitname)
                txt.text = fr.name

                txt = findViewById(R.id.fruitvaluecal)
                txt.text = fr.nutritions.calories.toString()

                txt = findViewById(R.id.fruitvaluefat)
                txt.text = fr.nutritions.fat.toString()

                txt = findViewById(R.id.fruitvaluesug)
                txt.text = fr.nutritions.sugar.toString()

                txt = findViewById(R.id.fruitvaluecarb)
                txt.text = fr.nutritions.carbohydrates.toString()

                txt = findViewById(R.id.fruitvalueprot)
                txt.text = fr.nutritions.protein.toString()
            }
        }
    }
}