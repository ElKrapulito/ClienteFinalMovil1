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
import com.moviles.deliveryapp.fragments.adapters.MyCartRecyclerViewAdapter

import com.moviles.deliveryapp.models.Producto
import kotlin.collections.ArrayList

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [CartFragment.OnListFragmentInteractionListener] interface.
 */
class CartFragment : Fragment() {

    // TODO: Customize parameters
    //private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null
    private lateinit var products: ArrayList<Producto>
    private lateinit var mAdapter: MyCartRecyclerViewAdapter
    /*private var restaurantId = 0
    private var usuarioId = 0*/
   // private var total = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*arguments?.let {
            restaurantId = it.getInt(ARG_RESTAURANT_ID)
            usuarioId = it.getInt(ARG_USUARIO_ID)
        }*/

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        products = arrayListOf()
        mAdapter =
            MyCartRecyclerViewAdapter(
                products,
                listener
            )
        val view = inflater.inflate(R.layout.fragment_cart_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager =
                   LinearLayoutManager(context)
                adapter =
                    mAdapter
            }
        }
        return view
    }

    fun addProduct(product: Producto){
        /*if(!products.contains(product)){
            products.add(product)
        }*/
       // total += product.precio.toDouble()

        val p = products.find{ producto -> product.id == producto.id }
        if(p != null){
            p.cantidad++
        } else {
            product.producto_id = product.id
            product.precioProducto = product.precio
            product.cantidad++
            products.add(product)
        }

        mAdapter.notifyDataSetChanged()
    }

    fun getProducts():ArrayList<Producto>{
        return products
    }

    fun subtractItem(product:Producto){
        val p = products.find{ producto -> product.id == producto.id }
        if(p != null && p.cantidad > 1){
            p.cantidad--
        } else if (p != null && p.cantidad <= 1){
            p.cantidad = 0
           products.remove(product)
        }
        mAdapter.notifyDataSetChanged()
    }

    fun deleteItem(product: Producto){
        val p = products.find{ producto -> product.id == producto.id }
        if(p != null){
            product.cantidad = 0
            products.remove(product)
        }
        mAdapter.notifyDataSetChanged()
    }


    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("Codigos","Request:${requestCode} Result:${resultCode}")
        if(requestCode == resultCode){
            if(data != null){
                val location = data.getParcelableExtra("location") as LatLng
                //Toast.makeText(this, "${location.latitude} ${location.longitude}", Toast.LENGTH_SHORT).show()
                val pedido = Pedido(0,usuarioId,restaurantId,total,
                    Calendar.getInstance().toString(),"Por ahi!",location.latitude,location.longitude,"0",products,0)
                makePedido(pedido)
            }
        }
    }

    private fun makePedido(pedido: Pedido) {
        val retrofit = Retrofit.Builder()
            .baseUrl(MainActivity.apiURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val caseService = retrofit.create(ApiRestaurant::class.java)
        val request = caseService.makePedido(pedido)
        request.enqueue(object: Callback<ResponseGeneric<String>> {
            override fun onFailure(call: Call<ResponseGeneric<String>>, t: Throwable) {
                Log.e("Error","${t.message}")
            }

            override fun onResponse(call: Call<ResponseGeneric<String>>, response: Response<ResponseGeneric<String>>) {
                val test = response.body()?.message
                //Toast.makeText(this,test,Toast.LENGTH_LONG).show()
            }
        })
    }*/

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
        fun onListCartInteraction(item: Producto?)
        fun onBtnDeleteInteraction(item: Producto?)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_RESTAURANT_ID = "restaurantId"
        const val ARG_USUARIO_ID = "restaurantId"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance()=
            CartFragment().apply {
                /*arguments = Bundle().apply {
                    putInt(ARG_RESTAURANT_ID, restaurantId)
                    putInt(ARG_USUARIO_ID, usuarioId)
                }*/
            }
    }
}
