package com.example.study1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.app.AlertDialog
import android.view.LayoutInflater
import android.widget.Toast





class MainActivity : AppCompatActivity() {

    private lateinit var fashionItemAdapter: FashionItemAdapter
    private lateinit var recyclerView: RecyclerView
    private val cartItems = mutableListOf<FashionItem>()
    private var isFirstOpening = true


    val items = listOf(
        FashionItem("Casual T-Shirt", "https://i.imgur.com/ztAzmi6.jpeg", price = 19.99, size = "M", brand = "SouthPark", isSelected = false),
        FashionItem("Stylish Jeans", "https://i.imgur.com/FopaVcm.jpeg", price = 19.95, size = "L", brand = "Century", isSelected = false),
        FashionItem("Classic Jacket", "https://i.imgur.com/3iboz78.png", price = 59.99, size = "XL", brand = "NASA", isSelected = false),
        FashionItem("Trainers", "https://i.imgur.com/Aq2baWI.jpeg", price = 99.99, size = "46", brand = "Nike", isSelected = false),
        FashionItem("Trainers", "https://i.imgur.com/drORXsS.png", price = 99.99, size = "46", brand = "Nike", isSelected = false)
    )

    fun updateCartCount(newCount: Int) {
        val cartItemCount = findViewById<TextView>(R.id.cartItemCount)
        cartItemCount.text = "$newCount"
    }
    fun onItemAddToCart(item: FashionItem) {
        cartItems.add(item)
        updateCartCount(cartItems.size)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.casual_man)


        recyclerView = findViewById<RecyclerView>(R.id.fashionItemRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fashionItemAdapter = FashionItemAdapter(items, this::onItemAddToCart, isFirstOpening)
        recyclerView.adapter = fashionItemAdapter

        isFirstOpening = false

        recyclerView.post {
            fashionItemAdapter.notifyDataSetChanged()
        }



        val checkoutButton = findViewById<Button>(R.id.checkoutButton)
        checkoutButton.setOnClickListener {
            var totalPrice = 0.0
            val itemsStringBuilder = StringBuilder()
            cartItems.forEach { item ->
                totalPrice += item.price
                itemsStringBuilder.append("${item.title} - $${item.price}\n")
            }


            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_purchase, null)


            val textViewItemList = dialogView.findViewById<TextView>(R.id.textViewItemList)
            val textViewTotalPrice = dialogView.findViewById<TextView>(R.id.textViewTotalPrice)
            textViewItemList.text = itemsStringBuilder.toString()
            textViewTotalPrice.text = "Total Price: $${totalPrice}"


            AlertDialog.Builder(this)
                .setTitle("Complete Purchase")
                .setView(dialogView)
                .setPositiveButton("Buy") { dialog, which ->

                    val purchaseDialogView = LayoutInflater.from(this).inflate(R.layout.confirming_purchase, null)

                        val textViewPrice = purchaseDialogView.findViewById<TextView>(R.id.textViewPrice)
                        textViewPrice.text = " Price: $${totalPrice}"

                    AlertDialog.Builder(this)
                        .setTitle("Confirm Purchase")
                        .setView(purchaseDialogView)
                        .setPositiveButton("Confirm") { purchaseDialog, which ->
                            cartItems.clear()
                            updateCartCount(cartItems.size)
                            Log.d("MainActivity", "Purchase confirmed")
                            Toast.makeText(this@MainActivity, "Purchase completed successfully!", Toast.LENGTH_LONG).show()
                        }
                        .setNegativeButton("Cancel", null)
                        .show()
                }
                .setNegativeButton("Cancel") {
                        dialog, which -> Log.d("MainActivity", "Purchase cancelled")
                    cartItems.clear()
                    updateCartCount(cartItems.size)
                }
                .show()
        }


    }
}
