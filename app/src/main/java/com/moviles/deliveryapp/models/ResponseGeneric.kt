package com.moviles.deliveryapp.models

class ResponseGeneric<T> (
    val res:String,
    val data:T,
    val message:String?
)