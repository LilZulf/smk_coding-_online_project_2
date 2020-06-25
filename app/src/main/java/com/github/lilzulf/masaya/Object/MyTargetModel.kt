package com.github.lilzulf.masaya.Object

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity( tableName = "my_target" )
data class MyTargetModel(
    var tittle : String,
    var date : String,
    @PrimaryKey(autoGenerate = true) var key : String?
){
    constructor () : this ( "" , "",""
    )
}
