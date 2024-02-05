package com.example.study1
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

class FashionItemAdapter(private val items: List<FashionItem>) : RecyclerView.Adapter<FashionItemAdapter.ViewHolder>() {

    // Define the ViewHolder class
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.findViewById(R.id.itemTitle) // ID from your item layout
        val imageView: ImageView = view.findViewById(R.id.itemImage)
        val priceView: TextView = view.findViewById(R.id.itemPrice)
        val brandView: TextView = view.findViewById(R.id.itemBrand)
    }

    private var enlargedDialog: AlertDialog? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fashion, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.titleView.text = item.title
        Glide.with(holder.itemView.context).load(item.imageUrl).into(holder.imageView)
        holder.priceView.text = "Price: \$${item.price}"
        holder.brandView.text = "Brand: ${item.brand}"

        holder.imageView.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Show the dialog here
                    showEnlargedDialog(view.context, item)
                    true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Dismiss the dialog here
                    enlargedDialog?.dismiss()
                    true
                }
                else -> false
            }
        }
    }

    private fun showEnlargedDialog(context: Context, item: FashionItem) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.enlarged_image, null)
        val imageView: ImageView = dialogView.findViewById(R.id.enlargedImage)
        val textView: TextView = dialogView.findViewById(R.id.enlargedText)

        Glide.with(context).load(item.imageUrl).into(imageView)
        textView.text = "${item.title}\nPrice: \$${item.price}\nBrand: ${item.brand}"

        enlargedDialog= AlertDialog.Builder(context)
            .setView(dialogView)
            .show()
    }

    override fun getItemCount(): Int = items.size
}


data class FashionItem(val title: String, val imageUrl: String,val price: Double, val size: String,val brand: String)
