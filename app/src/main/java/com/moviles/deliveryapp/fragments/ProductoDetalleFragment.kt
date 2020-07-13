package com.moviles.deliveryapp.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.clientefinal.R
import com.moviles.deliveryapp.fragments.adapters.MyProductoDetalleRecyclerViewAdapter

import com.moviles.deliveryapp.models.PedidoDetalle
import com.moviles.deliveryapp.models.ProductoDetalle

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [ProductoDetalleFragment.OnListFragmentInteractionListener] interface.
 */
class ProductoDetalleFragment : Fragment() {

    private lateinit var pedido:PedidoDetalle

    private var listener: OnListFragmentInteractionListener? = null
    private lateinit var products: ArrayList<ProductoDetalle>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            pedido = it.getSerializable(ARG_PEDIDO) as PedidoDetalle
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pedido_detalle_list, container, false)
        //TODO: Pedir la lista de products
        products = pedido.detalle
        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager =
                    LinearLayoutManager(context)

                adapter =
                    MyProductoDetalleRecyclerViewAdapter(
                        products,
                        listener
                    )
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: ProductoDetalle?)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_PEDIDO = "pedido"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(pedido: PedidoDetalle) =
            ProductoDetalleFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PEDIDO, pedido)
                }
            }
    }
}
