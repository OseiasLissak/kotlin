package com.example.smallbusinessmanager

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class StockActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var stockListView: ListView
    private lateinit var stockList: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)

        db = FirebaseFirestore.getInstance()
        stockListView = findViewById(R.id.stockListView)
        stockList = mutableListOf()

        val itemNameEditText = findViewById<EditText>(R.id.itemNameEditText)
        val itemQuantityEditText = findViewById<EditText>(R.id.itemQuantityEditText)
        val addItemButton = findViewById<Button>(R.id.addItemButton)

        addItemButton.setOnClickListener {
            val itemName = itemNameEditText.text.toString()
            val itemQuantity = itemQuantityEditText.text.toString().toInt()
            addItemToStock(itemName, itemQuantity)
        }

        loadStockItems()
    }

    private fun addItemToStock(itemName: String, quantity: Int) {
        val stockItem = hashMapOf(
            "name" to itemName,
            "quantity" to quantity
        )

        db.collection("stock").add(stockItem)
            .addOnSuccessListener {
                Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show()
                loadStockItems()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to Add Item", Toast.LENGTH_LONG).show()
            }
    }

    private fun loadStockItems() {
        db.collection("stock").get()
            .addOnSuccessListener { result ->
                stockList.clear()
                for (document in result) {
                    val item = "${document.getString("name")} - ${document.getLong("quantity")}"
                    stockList.add(item)
                }
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, stockList)
                stockListView.adapter = adapter
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load stock", Toast.LENGTH_LONG).show()
            }
    }
}
