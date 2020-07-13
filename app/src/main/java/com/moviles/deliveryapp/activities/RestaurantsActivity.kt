package com.moviles.deliveryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.clientefinal.R
import com.moviles.deliveryapp.fragments.RestaurantFragment
import com.moviles.deliveryapp.models.Restaurant
import com.moviles.deliveryapp.models.Usuario
import kotlinx.android.synthetic.main.activity_restaurants.*

class RestaurantsActivity : AppCompatActivity(), RestaurantFragment.OnListFragmentInteractionListener {

    private lateinit var usuario: Usuario
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurants)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()


        val fragment = RestaurantFragment.newInstance()
        fragmentTransaction.add(R.id.restaurants_container, fragment)

        fragmentTransaction.commit()

        usuario = intent.getSerializableExtra("usuario") as Usuario
        setUpListeners()
    }

    override fun onListFragmentInteraction(item: Restaurant?) {
        val intent = Intent(this, MenuActivity::class.java)
        intent.putExtra("restaurantId", item?.id)
        intent.putExtra("usuarioId", usuario.id)
        startActivity(intent)
    }

    private fun setUpListeners(){
        btnPedidos.setOnClickListener {
            val intent = Intent(this, PedidosActivity::class.java)
            intent.putExtra("usuarioId",usuario.id)
            startActivity(intent)
        }
    }

}
