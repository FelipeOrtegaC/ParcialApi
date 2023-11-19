package com.fruitsapi.viewModel
import com.frutas.model.Nutrientes
import com.frutas.model.Fruit

val listFilters = listOf("Calorías","Grasas","Azúcar","Carbohidratos","Proteína")
var defaultFruit = Fruit(
    "Default Fruit",
    0,"None",
    "None",
    "None",
    Nutrientes(
        0,
        0.0,
        0.0,
        0.0,
        0.0
    )
)