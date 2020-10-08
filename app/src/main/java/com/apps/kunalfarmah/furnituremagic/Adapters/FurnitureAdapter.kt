package com.apps.kunalfarmah.furnituremagic.Adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Paint
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.apps.kunalfarmah.furnituremagic.Adapters.FurnitureAdapter.FurnitureViewHolder
import com.apps.kunalfarmah.furnituremagic.R
import com.apps.kunalfarmah.furnituremagic.Room.Furniture
import com.apps.kunalfarmah.furnituremagic.ViewModels.FurnitureViewModel
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception

class FurnitureAdapter(
    var mContext: Context,
    var data: List<Furniture?>?,
    var viewModel: FurnitureViewModel?
) : RecyclerView.Adapter<FurnitureViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FurnitureViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FurnitureViewHolder(inflater.inflate(R.layout.furniture_item, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FurnitureViewHolder, position: Int) {
        val item = data!![position]

        if (item!!.discountedPrice == "") {
            item!!.discountedPrice = item!!.price
        }
        holder.name!!.text = item!!.name
        holder.type!!.text = item.type
        holder.disc_price!!.text = mContext.getString(R.string.rs) + item.discountedPrice
        holder.price?.apply {
            paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            text = mContext.getString(R.string.rs) + item.price
        }
        try {
            holder.image!!.setImageURI(Uri.parse(item.image))
        } catch (ex: Exception) {
            Log.d("Error", ex.message.toString())
        }

        holder.delete!!.setOnClickListener {
            if (item.user == FirebaseAuth.getInstance().currentUser!!.uid) {
                var dialog: AlertDialog.Builder = AlertDialog.Builder(mContext)
                dialog.setTitle("Are you sure that you want to delete this offer")
                dialog.setPositiveButton(
                    "YES",
                    DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                        viewModel!!.deleteItem(data!![position])
                    })
                dialog.setNegativeButton(
                    "GO BACK",
                    DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                    })

                var d = dialog.create()
                d.show()
            } else
                Toast.makeText(
                    mContext,
                    mContext.getString(R.string.can_not_Delete),
                    Toast.LENGTH_SHORT
                ).show()
        }
    }

    override fun getItemCount(): Int {
        return if (data == null) 0 else data!!.size
    }

    inner class FurnitureViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var name: TextView? = null
        var price: TextView? = null
        var disc_price: TextView? = null
        var type: TextView? = null
        var image: ImageView? = null
        var delete: ImageButton? = null
        var lyt_parent: CardView? = null

        init {
            lyt_parent = itemView.findViewById(R.id.main_layout)
            name = itemView.findViewById(R.id.name)
            price = itemView.findViewById(R.id.actual_price)
            disc_price = itemView.findViewById(R.id.price)
            type = itemView.findViewById(R.id.type)
            image = itemView.findViewById(R.id.image)
            delete = itemView.findViewById(R.id.delete)
        }

    }

}