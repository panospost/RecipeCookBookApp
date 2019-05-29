package cz.ackee.cookbook.screens.detailsScreen


import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cz.ackee.cookbook.App
import cz.ackee.cookbook.R
import cz.ackee.cookbook.databinding.DetailsFragmentLayoutBinding
import cz.ackee.cookbook.localDatabase.Repository
import javax.inject.Inject


class DetailsFragment : Fragment() {
    lateinit var binding: DetailsFragmentLayoutBinding

    lateinit var detailsViewModel: DetailsViewModel

    @Inject
    lateinit var repository: Repository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        binding = DataBindingUtil.inflate(
                inflater, R.layout.details_fragment_layout, container, false)
        binding.lifecycleOwner = this
        App.allComponent.inject(this)

        val recipeIdarg = DetailsFragmentArgs.fromBundle(arguments!!)

        val viewModelFactory = DetailsViewModelFactory(recipeIdarg.recipe, repository)

        detailsViewModel =
                ViewModelProviders.of(
                        this, viewModelFactory).get(DetailsViewModel::class.java)
        binding.viewModel = detailsViewModel
        binding.lifecycleOwner = this


        binding.detailsModel = detailsViewModel.recipe
        detailsViewModel.starsRated.observe(this, Observer { starRated ->
            rateTheRecipe(starRated)

        })
        addIngredientsToScreen(binding.ingredientsContainer, detailsViewModel.recipe.ingredients)
        ratingMedian(detailsViewModel.recipe.score)
        return binding.root
    }

    private fun ratingMedian(score: Int) {
        when (score) {
            0 -> {
            }
            1 -> {
                binding.imageView11.alpha = 1.0f
            }
            2 -> {
                binding.imageView11.alpha = 1.0f
                binding.imageView12.alpha = 1.0f
            }
            3 -> {
                binding.imageView11.alpha = 1.0f
                binding.imageView12.alpha = 1.0f
                binding.imageView13.alpha = 1.0f
            }
            4 -> {
                binding.imageView11.alpha = 1.0f
                binding.imageView12.alpha = 1.0f
                binding.imageView13.alpha = 1.0f
                binding.imageView14.alpha = 1.0f
            }
            5 -> {
                binding.imageView11.alpha = 1.0f
                binding.imageView12.alpha = 1.0f
                binding.imageView13.alpha = 1.0f
                binding.imageView14.alpha = 1.0f
                binding.imageView15.alpha = 1.0f
            }

        }
    }

    private fun rateTheRecipe(starRated: Int) {
        when (starRated) {
            1 -> {
                binding.imageView21.alpha = 1.0f
            }
            2 -> {
                binding.imageView21.alpha = 1.0f
                binding.imageView22.alpha = 1.0f
            }
            3 -> {
                binding.imageView21.alpha = 1.0f
                binding.imageView22.alpha = 1.0f
                binding.imageView23.alpha = 1.0f
            }
            4 -> {
                binding.imageView21.alpha = 1.0f
                binding.imageView22.alpha = 1.0f
                binding.imageView23.alpha = 1.0f
                binding.imageView24.alpha = 1.0f
            }
            5 -> {
                binding.imageView21.alpha = 1.0f
                binding.imageView22.alpha = 1.0f
                binding.imageView23.alpha = 1.0f
                binding.imageView24.alpha = 1.0f
                binding.imageView25.alpha = 1.0f
            }

        }
        binding.imageView21.isClickable = false
        binding.imageView22.isClickable = false
        binding.imageView23.isClickable = false
        binding.imageView24.isClickable = false
        binding.imageView25.isClickable = false
        detailsViewModel.recipe.score = starRated
        detailsViewModel.sendRating()

    }

    private fun addIngredientsToScreen(layout: LinearLayout, ingredients: List<String>) {
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