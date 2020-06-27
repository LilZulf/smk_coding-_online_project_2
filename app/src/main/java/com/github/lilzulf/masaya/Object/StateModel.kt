package com.github.lilzulf.masaya.Object

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = "my_state" )
data class StateModel(
    var state : String,
    @PrimaryKey var key : String
){
    constructor () : this ( "" , ""
    )
}
