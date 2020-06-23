package com.ideasfactory.greenprojectapp.repositoires

import androidx.lifecycle.MutableLiveData
import com.ideasfactory.greenprojectapp.model.Plant

class PlantRepository {

    //todo 6-> here is where we are going to retreave the data from web services or cash.
    val dataSetPlants: MutableList<Plant> = mutableListOf()
    private var instance: PlantRepository? = null

    //todo 7 -> singleton pattern
    fun getInstance(): PlantRepository? {
        if (instance == null) {
            instance = PlantRepository()
        }
        return instance
    }


    //Method to make database quertys or acces your cache
    ////todo 9 -> pretend to get data from a webservice or online source-->
    fun getFruits() : MutableLiveData<MutableList<Plant>>{
        setFruits()//-> todo 8. online source!!
        var data : MutableLiveData<MutableList<Plant>> =  MutableLiveData<MutableList<Plant>>()
        data.value = dataSetPlants
        return data
    }

    //todo 8 -> set the data. retrive a data from a database. (now this is hardCoding)
    fun setFruits(){
        dataSetPlants.add(Plant(0L,
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRrdUwkmHkabisijk3c_G7jOQAuxyNhOI8eVXV4MIiNvGjlEDH4&usqp=CAU",
            "Marihuana","",""
        ))
            dataSetPlants.add(Plant(1L,"https://publicdomainvectors.org/photos/eco_plant_green_icon.png", "Plant","",""))
            dataSetPlants.add(Plant(2L,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRrdUwkmHkabisijk3c_G7jOQAuxyNhOI8eVXV4MIiNvGjlEDH4&usqp=CAU",
                "Marihuana","",""
            ))
            dataSetPlants.add(Plant(3L,"https://publicdomainvectors.org/photos/eco_plant_green_icon.png", "Plant","",""))
            dataSetPlants.add(Plant(
                4L,"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRrdUwkmHkabisijk3c_G7jOQAuxyNhOI8eVXV4MIiNvGjlEDH4&usqp=CAU",
                "Marihuana","",""
            ))
            dataSetPlants.add(Plant(5L,"https://publicdomainvectors.org/photos/eco_plant_green_icon.png", "Plant","",""))
            dataSetPlants.add(Plant(6L,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRrdUwkmHkabisijk3c_G7jOQAuxyNhOI8eVXV4MIiNvGjlEDH4&usqp=CAU",
                "Marihuana","",""
            ))
            dataSetPlants.add(Plant(7L,"https://publicdomainvectors.org/photos/eco_plant_green_icon.png", "Plant","",""))
            dataSetPlants.add(Plant(
                8L,"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRrdUwkmHkabisijk3c_G7jOQAuxyNhOI8eVXV4MIiNvGjlEDH4&usqp=CAU",
                "Marihuana","",""
            ))
            dataSetPlants.add(Plant(9L,"https://publicdomainvectors.org/photos/eco_plant_green_icon.png", "Plant","",""))
            dataSetPlants.add(Plant(
                10L,"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRrdUwkmHkabisijk3c_G7jOQAuxyNhOI8eVXV4MIiNvGjlEDH4&usqp=CAU",
                "Marihuana","",""
            ))
        dataSetPlants.add(Plant(11L,"https://publicdomainvectors.org/photos/eco_plant_green_icon.png", "Plant","",""))
        dataSetPlants.add(Plant(
                12L,"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRrdUwkmHkabisijk3c_G7jOQAuxyNhOI8eVXV4MIiNvGjlEDH4&usqp=CAU",
                "Marihuana","",""
            ))
        dataSetPlants.add(Plant(13L,"https://publicdomainvectors.org/photos/eco_plant_green_icon.png", "Plant","",""))
        dataSetPlants.add(Plant(
                14L,"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRrdUwkmHkabisijk3c_G7jOQAuxyNhOI8eVXV4MIiNvGjlEDH4&usqp=CAU",
                "Marihuana","",""
            ))
        dataSetPlants.add(Plant(15L,"https://publicdomainvectors.org/photos/eco_plant_green_icon.png", "Plant","",""))
        dataSetPlants.add(Plant(
                16L,"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRrdUwkmHkabisijk3c_G7jOQAuxyNhOI8eVXV4MIiNvGjlEDH4&usqp=CAU",
                "Marihuana","",""
            ))
        dataSetPlants.add(Plant(17L,"https://publicdomainvectors.org/photos/eco_plant_green_icon.png", "Plant","","")
        )
    }
}