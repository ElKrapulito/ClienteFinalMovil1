package com.moviles.deliveryapp.fragments.adapters

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.clientefinal.R


import com.moviles.deliveryapp.fragments.RestaurantFragment.OnListFragmentInteractionListener
import com.moviles.deliveryapp.models.Restaurant
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.fragment_restaurant.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyRestaurantRecyclerViewAdapter (
    private val mValues: List<Restaurant>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyRestaurantRecyclerViewAdapter.RestaurantViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Restaurant
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            Log.d("ITEM", item.nombre)
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_restaurant, parent, false)
        return RestaurantViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val item = mValues[position]
        holder.mContentView.text = item.nombre
        Picasso.get().load("http://delivery.jmacboy.com/img/restaurantes/${item.id}.jpg").into(holder.mImg)

        with(holder.itemView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mContentView: TextView = itemView.content
        val mImg: ImageView = itemView.imgRestaurant
    }

    override fun getItemCount(): Int = mValues.size

}
