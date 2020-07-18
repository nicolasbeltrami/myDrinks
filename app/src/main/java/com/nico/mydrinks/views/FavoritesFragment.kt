package com.nico.mydrinks.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nico.mydrinks.R
import com.nico.mydrinks.data.AppDatabase
import com.nico.mydrinks.data.DataSource
import com.nico.mydrinks.data.model.Drink
import com.nico.mydrinks.network.RepositoryImplementation
import com.nico.mydrinks.resource.Resource
import com.nico.mydrinks.viewmodel.MainViewModel
import com.nico.mydrinks.viewmodel.MainViewModelFactory
import com.nico.mydrinks.views.adapter.DrinkAdapter
import kotlinx.android.synthetic.main.fragment_favorites.*


class FavoritesFragment : Fragment(), DrinkAdapter.OnDrinkClickListener {

    private val viewModel by viewModels<MainViewModel> {
        MainViewModelFactory(
            RepositoryImplementation(DataSource(AppDatabase.getDatabase(requireActivity().applicationContext)))
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFavoriteList()
        setupObservers()
    }

    private fun setupObservers(){
        viewModel.getFavoriteDrinks().observe(viewLifecycleOwner, Observer {result ->
            when(result){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    val list: List<Drink> = result.data.map {
                        Drink(it.drinkId, it.image, it.name, it.description)
                    }
                    rvFavorites.adapter = DrinkAdapter(requireContext(), list,this)
                }
                is Resource.Failure -> {}
            }
        })
    }

    private fun setupFavoriteList(){
        rvFavorites.layoutManager = LinearLayoutManager(requireContext())
        rvFavorites.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }

    override fun onDrinkClick(drink: Drink) {
        TODO("Not yet implemented")
    }


}