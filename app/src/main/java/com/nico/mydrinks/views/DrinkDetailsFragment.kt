package com.nico.mydrinks.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.nico.mydrinks.R
import com.nico.mydrinks.data.AppDatabase
import com.nico.mydrinks.data.DataSource
import com.nico.mydrinks.data.model.Drink
import com.nico.mydrinks.data.model.DrinkEntity
import com.nico.mydrinks.network.RepositoryImplementation
import com.nico.mydrinks.viewmodel.MainViewModel
import com.nico.mydrinks.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.fragment_drink_details.*


class DrinkDetailsFragment : Fragment() {

    private val viewModel by activityViewModels<MainViewModel>{
        MainViewModelFactory(RepositoryImplementation(DataSource(AppDatabase.getDatabase(requireActivity().applicationContext))))
    }
    private lateinit var drink: Drink

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().let {
            drink = it.getParcelable<Drink>("drink")!!
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drink_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireContext()).load(drink.image).centerCrop().into(imgDrink)
        drinkTitle.text = drink.name
        drinkDescription.text = drink.description

        fabFavoriteDrink.setOnClickListener {
            viewModel.addFavorite(DrinkEntity(drink.drinkId, drink.image, drink.name, drink.description))
            Toast.makeText(requireContext(), "Trago guardado en favoritos", Toast.LENGTH_SHORT).show()
        }
    }


}