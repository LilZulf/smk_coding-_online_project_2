package com.github.lilzulf.masaya.Object

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity( tableName = "my_grate" )
data class MyGrateModel(
    var text : String,
    var date : String,
    @PrimaryKey var key : String?
){
    constructor () : this ( "" , "",""
    )
}
