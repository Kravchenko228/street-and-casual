package com.example.study1
import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.content.Context
import androidx.appcompat.app.AlertDialog
import android.view.MotionEvent
import android.widget.Button
import android.view.animation.AnimationUtils


class FashionItemAdapter(private val items: List<FashionItem>,private val onItemAddToCart: (FashionItem) -> Unit,
                         private val isFirstOpening: Boolean) : RecyclerView.Adapter<FashionItemAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.findViewById(R.id.itemTitle)
        val imageView: ImageView = view.findViewById(R.id.itemImage)
        val priceView: TextView = view.findViewById(R.id.itemPrice)
        val brandView: TextView = view.findViewById(R.id.itemBrand)
        val addToCartButton: Button = view.findViewById(R.id.addToCartButton)

    }

    private var enlargedDialog: AlertDialog? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fashion, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val context = holder.itemView.context
        holder.addToCartButton.setOnClickListener {
            onItemAddToCart(item)
        }
        if (isFirstOpening) {
            val animation = AnimationUtils.loadAnimation(context, R.anim.scale)
            holder.itemView.startAnimation(animation)
        }

        holder.titleView.text = item.title
        holder.priceView.text = "Price: \$${item.price}"
        holder.brandView.text = "Brand: ${item.brand}"

        Glide.with(holder.imageView.context).load(item.imageUrl).into(holder.imageView)


        holder.imageView.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {

                    showEnlargedDialog(holder.imageView.context, item)
                    true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {

                    enlargedDialog?.dismiss()
                    true
                }
                else -> false
            }
        }

        holder.itemView.setOnClickListener {
            showEnlargedDialog(context, item)
        }

    }


    @SuppressLint("SetTextI18n")
    private fun showEnlargedDialog(context: Context, item: FashionItem) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.enlarged_image, null)
        val imageView: ImageView = dialogView.findViewById(R.id.enlargedImage)
        val textView: TextView = dialogView.findViewById(R.id.enlargedText)

        Glide.with(context).load(item.imageUrl).into(imageView)
        textView.text = "${item.title}\nPrice: \$${item.price}\nBrand: ${item.brand}"

        enlargedDialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .create()

        enlargedDialog?.setCanceledOnTouchOutside(true)

        enlargedDialog?.show()
    }

    override fun getItemCount(): Int = items.size
}


data class FashionItem(val title: String, val imageUrl: String,val price: Double, val size: String,val brand: String,var isSelected: Boolean = false)
