package com.nico.mydrinks.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
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
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment(), DrinkAdapter.OnDrinkClickListener {

    private val viewModel by viewModels<MainViewModel> {
        MainViewModelFactory(
            RepositoryImplementation(DataSource(AppDatabase.getDatabase(requireActivity().applicationContext)))
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearchView()
        setupObservers()
        goToFavorites.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_favoritesFragment)
        }
    }

    private fun setupObservers() {
        viewModel.fetchDrinksList.observe(viewLifecycleOwner, Observer {result ->
            when(result){
                is Resource.Loading -> {
                    pbLoading.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    pbLoading.visibility = View.GONE
                    rvDrinks.adapter = DrinkAdapter(requireContext(), result.data, this)
                }
                is Resource.Failure -> {
                    pbLoading.visibility = View.GONE
                    Toast.makeText(requireContext(),"Ha ocurrido un error al mostrar los datos ${result.exception}", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
    private fun setupSearchView(){
        searchDrinks.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.setDrink(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }


    private fun setupRecyclerView() {
        rvDrinks.layoutManager = LinearLayoutManager(requireContext())
        rvDrinks.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }

    override fun onDrinkClick(drink: Drink) {
        val bundle = Bundle()
        bundle.putParcelable("drink", drink)
        findNavController().navigate(R.id.action_mainFragment_to_drinkDetailsFragment, bundle)
    }

}