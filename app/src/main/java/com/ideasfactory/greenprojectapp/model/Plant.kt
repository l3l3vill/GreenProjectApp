package com.ideasfactory.greenprojectapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "plant_table")
// Todo Room -> 1. we Add entity to design the room table
//todo Room -> 2. we asign a name to the table
open class Plant (
    //todo Room -> 3. define primary key to our table
    @PrimaryKey (autoGenerate = true)
    val plantId :Long = 0L,
    //todo Room -> 4. define columns of our table -> 5. go to
    @ColumnInfo(name = "photo_plant")
    val photoPlant : String,
    @ColumnInfo(name = "name_plant")
    val namePlant : String,
    @ColumnInfo(name = "type_plant")
    val typePlant : String,
    @ColumnInfo(name = "description_plant")
    val descriptionPlant : String
) {


}