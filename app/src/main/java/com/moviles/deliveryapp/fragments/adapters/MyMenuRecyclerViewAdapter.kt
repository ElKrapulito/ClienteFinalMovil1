package com.moviles.deliveryapp.fragments.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.clientefinal.R


import com.moviles.deliveryapp.fragments.MenuFragment.OnListFragmentInteractionListener
import com.moviles.deliveryapp.models.Producto
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.fragment_menu.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyMenuRecyclerViewAdapter(
    private val mValues: List<Producto>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyMenuRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private val mInfoListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Producto
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
        mInfoListener = View.OnClickListener {
            val item = it.tag as Producto

            mListener?.onInfoBtnINteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_menu, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mContentView.text = item.nombre
        holder.mPrice.text = item.precio
        Picasso.get().load("http://delivery.jmacboy.com/img/productos/${item.id}.jpg").into(holder.mImg)

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }

        with(holder.mView.btnInfo){
            tag = item
            setOnClickListener(mInfoListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mContentView: TextView = mView.content
        val mPrice: TextView = mView.item_price
        val mImg: ImageView = mView.imgProduct

    }
}
