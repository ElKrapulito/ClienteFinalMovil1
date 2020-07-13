package com.moviles.deliveryapp.models

data class ProductoDetalle (
    val id:Int,
    val usuario_id: String,
    val pedido_id: String,
    val cantidad: String,
    val precioProducto: String,
    val producto: Producto
)