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
import com.moviles.deliveryapp.activities.MainActivity
import com.moviles.deliveryapp.api.ApiCliente
import com.moviles.deliveryapp.fragments.adapters.MyMenuRecyclerViewAdapter

import com.moviles.deliveryapp.models.Producto
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [MenuFragment.OnListFragmentInteractionListener] interface.
 */
class MenuFragment : Fragment() {

    // TODO: Customize parameters
    private var restaurantId = 0

    private var listener: OnListFragmentInteractionListener? = null

    lateinit var products: ArrayList<Producto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            restaurantId = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getRestaurant(restaurantId)
        val view = inflater.inflate(R.layout.fragment_menu_list, container, false)
        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter =
                    MyMenuRecyclerViewAdapter(
                        products,
                        listener
                    )
            }
        }
        return view
    }

    private fun getRestaurant(restaurantId:Int) = runBlocking {
        val retrofit = Retrofit.Builder()
            .baseUrl(MainActivity.apiURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val caseService = retrofit.create(ApiCliente::class.java)
        val request = caseService.getRestaurant(restaurantId)
        products = request.data.productos as ArrayList<Producto>
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
        fun onListFragmentInteraction(item: Producto?)
        fun onInfoBtnINteraction(item:Producto?)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "restaurantId"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(id: Int) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, id)
                }
            }
    }
}
