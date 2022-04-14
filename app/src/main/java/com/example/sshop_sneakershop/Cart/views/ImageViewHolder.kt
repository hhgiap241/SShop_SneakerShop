package com.example.sshop_sneakershop.Cart.views


import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop.Product.models.ProductInCart
import com.example.sshop_sneakershop.R
import com.example.sshop_sneakershop.databinding.CartListItemBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso


class ImageViewHolder(
    private val ImageCellBinding: CartListItemBinding,
    private val clickListener: CartClickListener
) : RecyclerView.ViewHolder(ImageCellBinding.root) {
    fun bindItem(product: ProductInCart) {

        if (TextUtils.isEmpty(product.image)) ImageCellBinding.productImage.setImageResource(R.drawable.shoe) else Picasso.get()
            .load(product.image).into(ImageCellBinding.productImage)

        ImageCellBinding.itemTitle.text =
            if (product.name.length > 20) "${product.name.substring(13)}..." else product.name

        val price = "$" + product.price.toString()
        ImageCellBinding.itemPrice.text = price

        val size = product.size
        ImageCellBinding.itemSize.text = size

        ImageCellBinding.quantity.setText(product.quantity.toString())

        ImageCellBinding.quantity.doOnTextChanged { text, start, before, count ->
            if (text.toString() == "0") {
                MaterialAlertDialogBuilder(ImageCellBinding.root.context)
                    .setTitle("Delete")
                    .setMessage("Are you sure you want to delete this item?")
                    .setPositiveButton("Yes") { dialog, which ->
                        //DELETE ITEM
                        TODO("Not yet implemented")

                    }
                    .setNegativeButton("No") { dialog, which ->
                        ImageCellBinding.quantity.setText("1")
                    }
                    .show()
            } else if (text.toString() == "") {
                ImageCellBinding.quantity.error = "Quantity cannot be empty"
                ImageCellBinding.quantity.setText("1")
            }
        }

        ImageCellBinding.productImage.setOnClickListener {
            clickListener.onClick(product)
        }

        ImageCellBinding.minus.setOnClickListener {
            val quantity: Int = Integer.valueOf(ImageCellBinding.quantity.text.toString()) - 1
            if (quantity == 0) {
                val alertDialog = MaterialAlertDialogBuilder(this.itemView.context)
                    .setTitle("Delete")
                    .setMessage("Are you sure you want to delete this item?")
                    .setPositiveButton("Yes") { dialog, which ->
                        //DELETE ITEM
                        TODO("Not yet implemented")

                    }
                    .setNegativeButton("No") { dialog, which ->
                        ImageCellBinding.quantity.setText("1")
                    }
                    .show()
            } else{
                ImageCellBinding.quantity.setText(quantity.toString())
            }
        }

        ImageCellBinding.plus.setOnClickListener {
            val quantity = Integer.valueOf(ImageCellBinding.quantity.text.toString()) + 1
            ImageCellBinding.quantity.setText(quantity.toString())
        }
    }
}