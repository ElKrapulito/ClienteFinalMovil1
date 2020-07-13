package com.moviles.deliveryapp.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moviles.deliveryapp.activities.MainActivity
import com.example.clientefinal.R
import com.moviles.deliveryapp.api.ApiCliente
import com.moviles.deliveryapp.fragments.adapters.MyRestaurantRecyclerViewAdapter
import com.moviles.deliveryapp.models.Restaurant
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [RestaurantFragment.OnListFragmentInteractionListener] interface.
 */
class RestaurantFragment : Fragment() {

    // TODO: Customize parameters

    private var listener: OnListFragmentInteractionListener? = null
    private lateinit var listita:ArrayList<Restaurant>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getAllRestaurants()
        val view = inflater.inflate(R.layout.fragment_restaurant_list, container, false)
        //val lstRestaurant = view.findViewById<RecyclerView>(R.id.lstRestaurants)
        // Set the adapter

        if(view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter =
                    MyRestaurantRecyclerViewAdapter(
                        listita,
                        listener
                    )
            }
        }
        return view
    }

     private fun getAllRestaurants() = runBlocking {
        val retrofit = Retrofit.Builder()
            .baseUrl(MainActivity.apiURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val caseService = retrofit.create(ApiCliente::class.java)
        val request = caseService.getAllRestaurants()
         listita = request.data
        /*request.enqueue(object : Callback<ResponseGeneric<ArrayList<Restaurant>>> {
            override fun onFailure(call: Call<ResponseGeneric<ArrayList<Restaurant>>>, t: Throwable) {
                //
            }

            override fun onResponse(
                call: Call<ResponseGeneric<ArrayList<Restaurant>>>,
                response: Response<ResponseGeneric<ArrayList<Restaurant>>>
            ) {

            }
        })*/
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
        fun onListFragmentInteraction(item: Restaurant?)
    }

    companion object {



        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance() =
            RestaurantFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
