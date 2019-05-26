package cz.ackee.cookbook.screens.listCookBookFragment

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import cz.ackee.cookbook.R
import cz.ackee.cookbook.databinding.LayoutListCookBookFragmentBinding
import cz.ackee.cookbook.localDatabase.GetAllRecipesDao
import cz.ackee.cookbook.localDatabase.RecipesLocalDatabase
import cz.ackee.cookbook.localDatabase.Repository
import cz.ackee.cookbook.network.NetworkRecipeDataSource
import cz.ackee.cookbook.network.RecipesApiService

class ListCookBookFragment : Fragment() {

    lateinit var viewModel: ListCookBookViewModel
    lateinit var recipesApiService: RecipesApiService
    lateinit var networkRecipeDataSource: NetworkRecipeDataSource

    lateinit var repository: Repository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Get a reference to the binding object and inflate the fragment views.
        val binding: LayoutListCookBookFragmentBinding = DataBindingUtil.inflate(
                inflater, R.layout.layout_list_cook_book_fragment, container, false)

        val database = RecipesLocalDatabase
        recipesApiService =  RecipesApiService(activity!!.applicationContext)
         networkRecipeDataSource = NetworkRecipeDataSource(recipesApiService)
        repository = Repository(networkRecipeDataSource, database.getInstance(activity!!.applicationContext).getAllRecipesDao)


        val viewModelFactory = ListCookViewModelFactory(repository)

        viewModel =
                ViewModelProviders.of(
                        this, viewModelFactory).get(ListCookBookViewModel::class.java)

        binding.lifecycleOwner = this

        val adapter = ListAdapterCookBook(ItemListListener {recipeId->
               viewModel.getTheRecipeDetails(recipeId)
        })

        binding.recipesList.adapter = adapter
        binding.recipesList.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        viewModel.responseList.observe(viewLifecycleOwner, Observer {
            it->
            it?.let {
                adapter.submitList(it)
                viewModel.saveRecipes(it)
            }
        })

        viewModel.isInitialised.observe(this, Observer {isInitialised->
            if(isInitialised){
              this.findNavController().navigate(ListCookBookFragmentDirections
                        .actionListCookBookFragmentToDetailsFragment(viewModel.getSpecificRecipe()))

                viewModel.clearIsInitialised()
            }


        })

        binding.viewModel = viewModel
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
                this.findNavController().navigate(R.id.action_listCookBookFragment_to_addFragmentScreen)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}