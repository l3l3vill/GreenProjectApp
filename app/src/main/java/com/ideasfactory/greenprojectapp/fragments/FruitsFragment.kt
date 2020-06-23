package com.ideasfactory.greenprojectapp.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.ideasfactory.greenprojectapp.adapters.FriutsRecyclerAdapter

import com.ideasfactory.greenprojectapp.R
import com.ideasfactory.greenprojectapp.viewModels.PlantsViewModel
import com.ideasfactory.greenprojectapp.databinding.FragmentFruitsBinding
import kotlinx.android.synthetic.main.fragment_fruits.*

/**
 * A simple [Fragment] subclass.
 */
class FruitsFragment : Fragment() {

    lateinit var binding : FragmentFruitsBinding
    lateinit var adapter : FriutsRecyclerAdapter
    lateinit var mPlantsViewModel : PlantsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fruits, container, false)


        //todo Room -> 14 instanciate ViewmodelFactory-----------------------------
/*
        val application = requireNotNull(this.activity).application
        val dataSource = PlantsDataBase.getInstance(application).plantDao
        val viewModelFactory = PlantsViewModelFactory(dataSource,application)
*/

        //todo->------------------------------------------------------------------
        //todo-> 3. instanciate viewModel. Associate viewModel whit the UI controller (we added viewModelFactory)
        //odo-> 3. instanciate viewModel. Associate viewModel whit the UI controller
        mPlantsViewModel = ViewModelProviders.of(this).get(PlantsViewModel::class.java)
        //mPlantsViewModel = ViewModelProviders.of(this,viewModelFactory).get(PlantsViewModel::class.java)
        //binding.lifecycleOwner

        //binding.plantsViewModel = mPlantsViewModel



        //todo-> 4. observe the data from the viewModel
        mPlantsViewModel.mFruits.observe(this, Observer { newFruitList ->
            adapter.notifyDataSetChanged()
        })


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        SetAdapter()
    }

    fun SetAdapter(){
        adapter = FriutsRecyclerAdapter()
        //todo-> 5 pass the list (value) from ViewModel, to the adapter -> now is ready to recibe the data. --> create Repository
        adapter.setList(mPlantsViewModel.getFruits().value!!)//-> we initialise mutableLiveData as null, so here we know is not nut
        recycler_explore.layoutManager = GridLayoutManager(requireContext(),3,GridLayoutManager.VERTICAL ,false)
        recycler_explore.adapter = adapter

    }




}
