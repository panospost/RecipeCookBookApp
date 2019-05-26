package cz.ackee.cookbook.screens.detailsScreen

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginLeft
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
        activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
      activity?.actionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        //activity?.actionBar?.
        activity?.actionBar?.setBackgroundDrawable( ColorDrawable(Color.parseColor("#80000000")));
        activity?.window?.statusBarColor = Color.TRANSPARENT;

       binding.detailsModel = detailsViewModel.recipe
        addIngredientsToScreen(binding.ingredientsContainer,detailsViewModel.recipe.ingredients)
        return binding.root
    }

    private fun addIngredientsToScreen(layout: LinearLayout,ingredients: List<String>) {
        var number = 0
            for (i in ingredients) {
                val tv = TextView(activity) // Prepare textview object programmatically
                tv.text = "\u2022 $i"
                tv.id = number
                layout.addView(tv) // Add to your ViewGroup using this method
                number++
            }
    }


}