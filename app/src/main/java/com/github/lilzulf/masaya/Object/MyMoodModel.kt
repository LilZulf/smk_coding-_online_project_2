package com.github.lilzulf.masaya.Object

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity( tableName = "my_mood" )
data class MyMoodModel(
    var state : String,
    var date : String,
    @PrimaryKey var key : String?
){
    constructor () : this ( "" , "",""
    )
}