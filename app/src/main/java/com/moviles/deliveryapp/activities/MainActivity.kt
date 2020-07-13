package com.moviles.deliveryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.clientefinal.R
import com.moviles.deliveryapp.api.ApiCliente
import com.moviles.deliveryapp.models.ResponseGeneric
import com.moviles.deliveryapp.models.Usuario
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(){

    private lateinit var token:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("MainActivity", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                token = task.result?.token.toString()

                //Toast.makeText(this, token, Toast.LENGTH_SHORT).show()
                // Log and toast
                /*val msg = getString(R.string.msg_token_fmt, token)
                Log.d("MainActivity", msg)
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()*/
            })
        FirebaseMessaging.getInstance().subscribeToTopic("weather")
            .addOnCompleteListener { task ->
                var msg = "Suscribe!"
                if (!task.isSuccessful) {
                    msg = "Error not Suscribe!"
                }
                Log.d("MainActivity", msg)
                //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            }

        setUpListener()
    }

    private fun setUpListener(){

        btnLogin.setOnClickListener {
            var usuario = Usuario(
                0,
                txtUsername.text.toString(),
                txtPass.text.toString(),
                null,
                null,
                token
            )
            val result = login(usuario)

           // Log.d("UserInfo","User:${usuario.usuario} Pass:${usuario.password}")
          //  Log.d("Result", result.res)

            if(result.res.equals("success")){
                usuario = result.data[0]
                val intent = Intent(this, RestaurantsActivity::class.java)
                intent.putExtra("usuario", usuario)
                startActivity(intent)
            }
        }
    }

    private fun login(usuario: Usuario): ResponseGeneric<ArrayList<Usuario>> {
        val retrofit = Retrofit.Builder()
            .baseUrl(apiURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val caseService = retrofit.create(ApiCliente::class.java)
        val request: ResponseGeneric<ArrayList<Usuario>>
        runBlocking {
            request = caseService.login(usuario)
        }
        return request
    }

    companion object {
        const val apiURL = "http://delivery.jmacboy.com/api/"
    }


}


