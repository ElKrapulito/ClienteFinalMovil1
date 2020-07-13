package com.moviles.deliveryapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.clientefinal.R
import com.moviles.deliveryapp.activities.MainActivity
import com.moviles.deliveryapp.api.ApiCliente
import com.moviles.deliveryapp.fragments.adapters.MyPedidosRecyclerViewAdapter

import com.moviles.deliveryapp.models.Pedido
import com.moviles.deliveryapp.models.ResponseGeneric
import com.moviles.deliveryapp.models.Usuario
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [PedidosFragment.OnListFragmentInteractionListener] interface.
 */
class PedidosFragment : Fragment() {

    // TODO: Customize parameters
    private var usuarioId:Int = 0
    private var listener: OnListFragmentInteractionListener? = null
    private lateinit var pedidos:ArrayList<Pedido>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            usuarioId = it.getInt(ARG_USER_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pedidos_list, container, false)
        pedidos = getPedidos(usuarioId)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager =
                        LinearLayoutManager(context)

                adapter =
                    MyPedidosRecyclerViewAdapter(
                        pedidos,
                        listener
                    )
            }
        }
        return view
    }

    private fun getPedidos(usuarioId:Int):ArrayList<Pedido> {
        val retrofit = Retrofit.Builder()
            .baseUrl(MainActivity.apiURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val caseService = retrofit.create(ApiCliente::class.java)
        val request: ResponseGeneric<Usuario>
        runBlocking {
            request = caseService.getPedidos(usuarioId)
        }
        Log.d("getPedidos","Response: ${request.data.usuario}")
        return request.data.pedidos!!
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
        fun onListFragmentInteraction(item: Pedido?)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_USER_ID = "usuarioId"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(usuarioId: Int) =
            PedidosFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_USER_ID, usuarioId)
                }
            }
    }
}
