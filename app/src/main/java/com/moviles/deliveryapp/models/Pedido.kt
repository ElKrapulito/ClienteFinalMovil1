package com.moviles.deliveryapp.models

data class Pedido (
    val id:Int,
    val usuario_id: Int,
    val restaurante_id: Int,
    val totalAPagar: Double,
    val fechaHora: String,
    val direccionEntrega:String,
    val latitud: Double,
    val longitud: Double,
    val estado: String,
    val detalle: ArrayList<Producto>,
    val moto_id:Int?
)