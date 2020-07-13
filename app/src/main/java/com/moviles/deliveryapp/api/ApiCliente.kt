package com.moviles.deliveryapp.api

import com.moviles.deliveryapp.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiCliente {

    @GET("restaurantes")
    suspend fun getAllRestaurants(): ResponseGeneric<ArrayList<Restaurant>>

    @GET("restaurantes/{id}")
    suspend fun getRestaurant(@Path("id") id:Int): ResponseGeneric<Restaurant>

    @GET("usuarios/{id}")
    suspend fun getPedidos(@Path("id") id:Int): ResponseGeneric<Usuario>

    @GET("pedidos/{id}")
    suspend fun getPedidoDetalle(@Path("id") id: Int): ResponseGeneric<PedidoDetalle>

    @POST("pedidos")
    fun makePedido(@Body pedido: Pedido): Call<ResponseGeneric<String>>

    @POST("usuarios/login")
    suspend fun login(@Body usuario: Usuario): ResponseGeneric<ArrayList<Usuario>>


}