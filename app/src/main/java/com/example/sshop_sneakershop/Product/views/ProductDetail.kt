package com.example.sshop_sneakershop.Product.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sshop_sneakershop.Cart.controllers.CartController
import com.example.sshop_sneakershop.Homepage.ItemClickListener
import com.example.sshop_sneakershop.Product.controllers.ProductController
import com.example.sshop_sneakershop.Product.models.Product
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.Review.models.Review
import com.example.sshop_sneakershop.Review.views.ReviewAdapter
import com.example.sshop_sneakershop.databinding.ActivityItemDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import java.text.SimpleDateFormat

class ProductDetail : AppCompatActivity(), ItemClickListener, IProductView {
    private lateinit var binding: ActivityItemDetailBinding
    private lateinit var productList: ArrayList<Product>
    private val reviewList: ArrayList<Review> = ArrayList()

    private lateinit var productController: ProductController
    private lateinit var cartController: CartController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        productController = ProductController(this)

        binding = ActivityItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productList = ArrayList()

        val mainActivity = this
        binding.recyclerViewRelated.apply {
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = ProductAdapter(productList, mainActivity)
        }

        binding.recyclerViewReview.apply {
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = ReviewAdapter(reviewList)
        }
        binding.itemDetailToolbar.setNavigationOnClickListener {
            finish()
        }

        cartController = CartController()

        //Get item-data base on ID here
        val id = intent.getStringExtra("item-id").toString()
        productController.onGetProductById(id)

        // Get size from radio group
        val radioGroup = binding.radioGroup
        radioGroup.check(radioGroup.getChildAt(0).id)
        var size = "US7"
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val radioButton = radioGroup.findViewById<RadioButton>(checkedId)
            if (radioButton != null) {
                size = radioButton.text.toString()
            }
        }

        binding.itemDetailButtonAddToCart.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                cartController.addToCart(id, size)
            }
        }

    }

    override fun onClick(product: Product) {
        val intent = Intent(applicationContext, ProductDetail::class.java)
        intent.putExtra("itemID", product.id)
        startActivity(intent)
        finish()
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged", "SetTextI18n", "SimpleDateFormat")
    private fun getProductDetail(id: String) {
        GlobalScope.launch {
            val product = productController.getProductById(id)

            if (product.reviews != null) {
                reviewList.addAll(product.reviews!!)
            }

            withContext(Dispatchers.Main) {
                if (TextUtils.isEmpty(product.image)) {
                    binding.backgroundImageView.setImageResource(R.drawable.shoe)
                } else {
                    Picasso.get().load(product.image).into(binding.backgroundImageView)
                }

                binding.pictureTitle.text = product.brand
                binding.categoryTag.text = product.category
                binding.productName.text = product.name
                val priceValue = product.price - 1.0
                binding.productPrice.text = "$$priceValue"
                val priceOldValue = product.price
                binding.productOldPrice.text = "$$priceOldValue"
                val ratingValue = product.rating
                binding.rating.text = "$ratingValue/5.00"
                binding.descriptionContent.text = product.description


                val formatter = SimpleDateFormat("dd-MM-yyyy")
                val releaseDate = formatter.format(product.releaseDate)
                binding.infoContent.text =
                    "Origin: ${product.origin}\nStyle: ${product.category}\nReleased date: $releaseDate"

                binding.recyclerViewReview.adapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onShowAllProducts(products: ArrayList<Product>) {
        TODO("Not yet implemented")
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged", "SetTextI18n", "SimpleDateFormat")
    override fun onShowProductDetail(product: Product) {
        if (product.reviews != null) {
            reviewList.addAll(product.reviews!!)
        }

        if (TextUtils.isEmpty(product.image)) {
            binding.backgroundImageView.setImageResource(R.drawable.shoe)
        } else {
            Picasso.get().load(product.image).into(binding.backgroundImageView)
        }

        binding.pictureTitle.text = product.brand
        binding.categoryTag.text = product.category
        binding.productName.text = product.name
        val priceValue = product.price - 1.0
        binding.productPrice.text = "$$priceValue"
        val priceOldValue = product.price
        binding.productOldPrice.text = "$$priceOldValue"
        val ratingValue = product.rating
        binding.rating.text = "$ratingValue/5.00"
        binding.descriptionContent.text = product.description


        val formatter = SimpleDateFormat("dd-MM-yyyy")
        val releaseDate = formatter.format(product.releaseDate)
        binding.infoContent.text =
            "Origin: ${product.origin}\nStyle: ${product.category}\nReleased date: $releaseDate"

        binding.recyclerViewReview.adapter?.notifyDataSetChanged()

        productController.onGetProductsByCategory(product.category)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onShowProductsByCategory(products: ArrayList<Product>) {
        productList.addAll(products)
        binding.recyclerViewRelated.adapter?.notifyDataSetChanged()
    }
}