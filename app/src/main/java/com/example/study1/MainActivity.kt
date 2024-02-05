package com.example.study1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class MainActivity : AppCompatActivity() {

    private lateinit var fashionItemAdapter: FashionItemAdapter



    val items = listOf(
        FashionItem("Casual T-Shirt", "https://i.imgur.com/ztAzmi6.jpeg", price = 19.99, size = "M", brand = "SouthPark"),
        FashionItem("Stylish Jeans", "https://i.imgur.com/FopaVcm.jpeg", price = 19.95, size = "L", brand = "Century"),
        FashionItem("Classic Jacket", "https://i.imgur.com/3iboz78.png", price = 59.99, size = "XL", brand = "NASA"),
        FashionItem("Trainers", "https://i.imgur.com/Aq2baWI.jpeg", price = 99.99, size = "46", brand = "Nike"),
        FashionItem("Trainers", "https://i.imgur.com/drORXsS.png", price = 99.99, size = "46", brand = "Nike")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.casual_man)

        val recyclerView = findViewById<RecyclerView>(R.id.fashionItemRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fashionItemAdapter = FashionItemAdapter(items)
        recyclerView.adapter = fashionItemAdapter
    }
}