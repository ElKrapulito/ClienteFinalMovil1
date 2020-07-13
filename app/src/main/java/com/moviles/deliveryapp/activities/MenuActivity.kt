package com.moviles.deliveryapp.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.clientefinal.R
import com.moviles.deliveryapp.api.ApiCliente
import com.moviles.deliveryapp.fragments.CartFragment
import com.moviles.deliveryapp.fragments.MenuFragment
import com.moviles.deliveryapp.models.Pedido
import com.moviles.deliveryapp.models.Producto
import com.moviles.deliveryapp.models.ResponseGeneric
import com.google.android.gms.maps.model.LatLng
import com.moviles.deliveryapp.activities.box.InfoBox
import kotlinx.android.synthetic.main.activity_menu.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MenuActivity : AppCompatActivity(),
    MenuFragment.OnListFragmentInteractionListener,
    CartFragment.OnListFragmentInteractionListener {

    private lateinit var cart: CartFragment
    private var total:Double = 0.0
    private var restaurantId:Int = 0
    private var usuarioId:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        restaurantId = intent.getIntExtra("restaurantId",0)
        usuarioId = intent.getIntExtra("usuarioId",0)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val fragment = MenuFragment.newInstance(restaurantId)
        fragmentTransaction.add(R.id.frame_products, fragment)

        cart = CartFragment.newInstance()
        fragmentTransaction.add(R.id.frame_cart, cart)

        fragmentTransaction.commit()

        setUpListener()
    }

    override fun onListFragmentInteraction(item: Producto?) {
        if(item== null){
            return
        }
        val price = item.precio.toDouble()
        total += price
        total_price.text = "Total: ${total}"
        cart.addProduct(item)
    }

    override fun onInfoBtnINteraction(item: Producto?) {
        if(item != null){
            InfoBox(item).show(supportFragmentManager,"Info")
        }
    }


    fun setUpListener(){
        btnBuy.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivityForResult(intent,0)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("Codigos","Request:${requestCode} Result:${resultCode}")
        if(requestCode == resultCode){
            if(data != null){
                val location = data.getParcelableExtra("location") as LatLng
                //Toast.makeText(this, "${location.latitude} ${location.longitude}", Toast.LENGTH_SHORT).show()
                val pedido = Pedido(
                    0,
                    usuarioId,
                    restaurantId,
                    total,
                    Calendar.getInstance().toString(),
                    "Por ahi!",
                    location.latitude,
                    location.longitude,
                    "0",
                    cart.getProducts(),
                    0
                )
                makePedido(pedido)
            }
        }
        finish()
        //Toast.makeText(this, "ON Activity Result",Toast.LENGTH_LONG).show()
    }

    private fun makePedido(pedido: Pedido) {
        val retrofit = Retrofit.Builder()
            .baseUrl(MainActivity.apiURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val caseService = retrofit.create(ApiCliente::class.java)
        val request = caseService.makePedido(pedido)
        request.enqueue(object: Callback<ResponseGeneric<String>>{
            override fun onFailure(call: Call<ResponseGeneric<String>>, t: Throwable) {
                Log.e("Error","${t.message}")
            }

            override fun onResponse(call: Call<ResponseGeneric<String>>, response: Response<ResponseGeneric<String>>) {
                /*Log.d("makePedido","${response.body()?.res}: ${response.body()?.data}")
                if(response.body()?.res == "success"){
                    Toast.makeText(applicationContext,"Pedido Realizado!",Toast.LENGTH_LONG).show()
                    finishActivity(200)
                }*/
            }
        })
    }

    override fun onListCartInteraction(item: Producto?) {
        if(item != null){
            cart.subtractItem(item)
            val price = item.precio.toDouble()
            total -= price
            total_price.text = "Total: ${total}"
        }
    }

    override fun onBtnDeleteInteraction(item: Producto?) {
        if(item != null){
            val price = item.precio.toDouble()
            total -= price * item.cantidad
            total_price.text = "Total: ${total}"
            cart.deleteItem(item)
        }
    }
}
