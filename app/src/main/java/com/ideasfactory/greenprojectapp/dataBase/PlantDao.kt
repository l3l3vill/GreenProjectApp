package com.ideasfactory.greenprojectapp.dataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ideasfactory.greenprojectapp.model.Plant

@Dao
//todo Room -> 5. Anotate this interface with @Dao anotation
interface PlantDao {
    //todo Room -> 6. insert data 6.1 create @Insert Anotation 6.2 create the funtion anotation (to use it on or code). that will create a row of a new fruit in our data base table
    @Insert
    fun insert (plant : Plant)

    //todo Room -> 7. create the quertyes according to our needs (@Detele, @Insert, @Update, @Querty(sql argument to make quertyes)
    @Query("SELECT * from plant_table WHERE plantId = :key")//this key is the parametrer of the function
    fun getFruit(key : Long) : Plant

    @Query("SELECT * FROM plant_table ORDER BY plantId DESC")
    fun getAllFruits(): LiveData<List<Plant>> //-> as it retunrs liveData object, we just need to add observer and information will be updated with no need of re call it.

    @Query("SELECT * FROM plant_table ORDER BY plantId DESC LIMIT 1")// -> Limint 1 selects one
    fun getLastFruit(): Plant? //-> value could be null if we delete list

    //todo Room 8-> create database abstract Class and extends : RoomDatabase()
}