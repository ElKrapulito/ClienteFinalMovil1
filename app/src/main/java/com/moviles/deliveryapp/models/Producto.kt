package com.moviles.deliveryapp.models

data class Producto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val restaurante_id:String,
    val precio: String,
    var producto_id: Int?,
    var precioProducto: String?,
    var cantidad:Int
)