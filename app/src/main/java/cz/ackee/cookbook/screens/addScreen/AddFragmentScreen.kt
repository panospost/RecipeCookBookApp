package cz.ackee.cookbook.screens.addScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import cz.ackee.cookbook.App
import cz.ackee.cookbook.R
import cz.ackee.cookbook.databinding.AddFragmentLayoutBinding
import cz.ackee.cookbook.network.NetworkRecipeDataSource
import javax.inject.Inject


class AddFragmentScreen: Fragment() {

    lateinit var viewModel : AddFragmentViewModel
    lateinit var binding: AddFragmentLayoutBinding

    @Inject
    lateinit var networkRecipeDataSource: NetworkRecipeDataSource

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Get a reference to the binding object and inflate the fragment views.
         binding = DataBindingUtil.inflate(
                inflater, R.layout.add_fragment_layout, container, false)
        App.allComponent.inject(this)

        activity?.actionBar?.setDisplayShowHomeEnabled(true)

        val viewModelFactory = AddViewModelFactory(networkRecipeDataSource)

        viewModel =
                ViewModelProviders.of(
                        this, viewModelFactory).get(AddFragmentViewModel::class.java)


        binding.viewmodel = viewModel
        viewModel.requestResult.observe(viewLifecycleOwner, Observer { result->
            if(result == "OK"){
                this.findNavController().navigate(R.id.action_addFragmentScreen_to_listCookBookFragment)
            }
        })
        return binding.root
    }

    //enable options menu in this fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                viewModel.recipeToSend.ingredients.add("something")
                viewModel.sendRecipe(viewModel.recipeToSend)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}