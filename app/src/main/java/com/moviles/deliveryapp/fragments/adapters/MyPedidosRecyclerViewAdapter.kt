package com.moviles.deliveryapp.fragments.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.clientefinal.R


import com.moviles.deliveryapp.fragments.PedidosFragment.OnListFragmentInteractionListener
import com.moviles.deliveryapp.models.Pedido

import kotlinx.android.synthetic.main.fragment_pedidos.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyPedidosRecyclerViewAdapter(
    private val mValues: List<Pedido>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyPedidosRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Pedido
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_pedidos, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        var estado = ""
        if (item.estado == "0"){
            estado = "Enviado"
        } else if(item.estado == "1"){
            estado = "En cocina"
        } else if(item.estado == "2"){
            estado = "En camino"
        } else if(item.estado == "3"){
            estado = "Etregado"
        } else {
            estado = "Se perdio"
        }

        holder.mIdView.text = estado
        holder.mContentView.text = item.totalAPagar.toString()

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content

    }
}
