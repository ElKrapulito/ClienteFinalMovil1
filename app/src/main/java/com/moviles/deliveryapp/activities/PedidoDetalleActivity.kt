package com.moviles.deliveryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.clientefinal.R
import com.moviles.deliveryapp.api.ApiCliente
import com.moviles.deliveryapp.fragments.ProductoDetalleFragment
import com.moviles.deliveryapp.models.PedidoDetalle
import com.moviles.deliveryapp.models.ProductoDetalle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_pedido_detalle.*
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PedidoDetalleActivity : AppCompatActivity(), ProductoDetalleFragment.OnListFragmentInteractionListener {

    private var pedidoId:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido_detalle)
        pedidoId = intent.getIntExtra("pedidoId",0)
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val pedido = getPedidoDetalle(pedidoId)
        val fragment = ProductoDetalleFragment.newInstance(pedido)
        fragmentTransaction.add(R.id.frameDetalle, fragment)

        fragmentTransaction.commit()
        Picasso.get().load("http://delivery.jmacboy.com/img/restaurantes/${pedido.restaurante.id}.jpg").into(imgRestaurantDetalle)
        txtRestaurantDetalle.text = pedido.restaurante.nombre
        txtTotalDetalle.text = "Total: ${pedido.totalAPagar}"

    }

    private fun getPedidoDetalle(pedidoId: Int):PedidoDetalle {
        val retrofit = Retrofit.Builder()
            .baseUrl(MainActivity.apiURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(ApiCliente::class.java)
        val request:PedidoDetalle
        runBlocking {
            request = api.getPedidoDetalle(pedidoId).data
        }

        return request
    }

    override fun onListFragmentInteraction(item: ProductoDetalle?) {

    }
}
