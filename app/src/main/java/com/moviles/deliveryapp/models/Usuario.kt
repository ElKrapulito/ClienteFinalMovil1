package com.moviles.deliveryapp.models

import java.io.Serializable

data class Usuario(
    val id:Int,
    val usuario:String,
    val password:String?,
    val tipoUsuario: String?,
    val pedidos: ArrayList<Pedido>?,
    val token: String?
):Serializable