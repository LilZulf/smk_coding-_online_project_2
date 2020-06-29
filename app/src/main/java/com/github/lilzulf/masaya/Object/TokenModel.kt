package com.github.lilzulf.masaya.Object

import androidx.room.Entity
import androidx.room.PrimaryKey

class TokenModel {

    data class TokenModel(
        var token : String,
        var email : String,
        var key : String
    ){
        constructor () : this ( "","",""
        )
    }


}