package com.moviles.deliveryapp.models

import java.io.Serializable

data class PedidoDetalle(
    val id:Int,
    val usuario_id: Int,
    val restaurante_id: Int,
    val totalAPagar: Double,
    val fechaHora: String,
    val direccionEntrega:String,
    val latitud: Double,
    val longitud: Double,
    val estado: String,
    val detalle: ArrayList<ProductoDetalle>,
    val restaurante: Restaurant,
    val moto_id:Int?
) : Serializable