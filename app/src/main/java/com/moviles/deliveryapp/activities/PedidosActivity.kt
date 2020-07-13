package com.moviles.deliveryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.clientefinal.R
import com.moviles.deliveryapp.fragments.PedidosFragment
import com.moviles.deliveryapp.models.Pedido

class PedidosActivity : AppCompatActivity(), PedidosFragment.OnListFragmentInteractionListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedidos)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val usuarioId = intent.getIntExtra("usuarioId",0)
        val fragment = PedidosFragment.newInstance(usuarioId)
        fragmentTransaction.add(R.id.frame_pedidos, fragment)
        fragmentTransaction.commit()
    }

    override fun onListFragmentInteraction(item: Pedido?) {
        if(item != null){
            val intent = Intent(this, PedidoDetalleActivity::class.java)
            intent.putExtra("pedidoId", item.id)
            startActivity(intent)
        }
    }
}
