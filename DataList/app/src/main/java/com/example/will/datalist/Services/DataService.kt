package com.example.will.datalist.Services

import com.example.will.datalist.Models.FoodItem
import com.example.will.datalist.Models.FoodGroup

object DataService {
    var fruits = listOf(
        FoodItem("蘋果", "img_apple"),
        FoodItem("香蕉", "img_banana"),
        FoodItem("鳳梨", "img_pineapple"),
        FoodItem("芭樂", "img_guava"),
        FoodItem("芒果", "img_mango"),
        FoodItem("蓮霧", "img_wax_apple")
    )

    val groups = listOf(
        FoodGroup("五穀", "img_various_grains", null),
        FoodGroup("蔬菜", "img_vegetables", null),
        FoodGroup("水果", "img_fruits", fruits),
        FoodGroup("奶類", "img_dairy", null),
        FoodGroup("肉類", "img_meats", null)
    )
}