package cz.ackee.cookbook.screens.detailsScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cz.ackee.cookbook.R
import cz.ackee.cookbook.databinding.DetailsFragmentLayoutBinding


class DetailsFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: DetailsFragmentLayoutBinding = DataBindingUtil.inflate(
                inflater, R.layout.details_fragment_layout, container, false)
        binding.lifecycleOwner = this

        val recipeIdarg = DetailsFragmentArgs.fromBundle(arguments!!)

        val viewModelFactory = DetailsViewModelFactory(recipeIdarg.recipe)

        val detailsViewModel =
                ViewModelProviders.of(
                        this, viewModelFactory).get(DetailsViewModel::class.java)
         binding.viewModel = detailsViewModel


       binding.detailsModel = detailsViewModel.recipe
        return binding.root
    }



}