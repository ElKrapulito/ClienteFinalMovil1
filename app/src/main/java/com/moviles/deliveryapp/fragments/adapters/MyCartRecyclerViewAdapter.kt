package com.moviles.deliveryapp.fragments.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.clientefinal.R


import com.moviles.deliveryapp.fragments.CartFragment.OnListFragmentInteractionListener
import com.moviles.deliveryapp.models.Producto

import kotlinx.android.synthetic.main.fragment_cart.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyCartRecyclerViewAdapter(
    private val mValues: List<Producto>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyCartRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private val mBtnDeleteListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Producto
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListCartInteraction(item)
        }
        mBtnDeleteListener = View.OnClickListener {
            val item = it.tag as Producto

            mListener?.onBtnDeleteInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_cart, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mIdView.text = item.cantidad.toString()
        holder.mContentView.text = item.nombre

        with(holder.mView.btnSubtract) {
            tag = item
            setOnClickListener(mOnClickListener)
        }

        with(holder.mView.btnDelete){
            tag = item
            setOnClickListener(mBtnDeleteListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content
    }
}
