package com.ideasfactory.greenprojectapp.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ideasfactory.greenprojectapp.dataBase.PlantDao

class PlantsViewModelFactory(
    private val dataSource : PlantDao,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("uncheced_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PlantsViewModel::class.java)){
            return PlantsViewModel( application) as T
        }
        throw IllegalArgumentException("unknown Viewmodel Class")
    }

    //Todo Room -> 15, lets implement the factory in our Fragment
}
