package com.ideasfactory.greenprojectapp.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ideasfactory.greenprojectapp.dataBase.PlantDao
import com.ideasfactory.greenprojectapp.model.Plant
import com.ideasfactory.greenprojectapp.repositoires.PlantRepository

class PlantsViewModel(application: Application) : AndroidViewModel(application) {

    //Todo -> 1. create a mutable live data list of Object. mutable, could change
    var mFruits = MutableLiveData<MutableList<Plant>>()
    var mPlantRepo : PlantRepository

    init{
        //todo -> 10. get the list of fruits that I have get from internet --> go to UI controller
      mPlantRepo = PlantRepository().getInstance()!!
        mFruits = mPlantRepo.getFruits()
    }

    //todo -> 2. LiveData, unmutable, can not change, but it is observable (go to UI controller to tell him about viewModel)
    fun getFruits() : LiveData<MutableList<Plant>> {
    return mFruits
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("FruitsViewModel", "FruitsViewModel destroyed")
    }
}