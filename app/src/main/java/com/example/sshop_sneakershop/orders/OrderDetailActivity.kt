package com.example.sshop_sneakershop.orders

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.checkout.ProductItem
import com.example.sshop_sneakershop.checkout.ProductItemAdapter

class OrderDetailActivity : AppCompatActivity() {
    lateinit var products: ArrayList<ProductItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        val productRecyclerView = findViewById<RecyclerView>(R.id.order_detail_recycler_view)

        val myProduct = ProductItem("",100.0, "Shoe", "image url", "Description", 2)
        products = listOf(myProduct, myProduct, myProduct,myProduct, myProduct, myProduct,myProduct, myProduct, myProduct).toCollection(ArrayList())

        val adapter = ProductItemAdapter(products)

        productRecyclerView.adapter = adapter

        productRecyclerView.layoutManager = LinearLayoutManager(this) //GridLayoutManager(this, 2)
    }
}