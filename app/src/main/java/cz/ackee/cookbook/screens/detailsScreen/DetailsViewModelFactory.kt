package cz.ackee.cookbook.screens.detailsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cz.ackee.cookbook.localDatabase.Repository
import cz.ackee.cookbook.models.DetailRecipeObject


class DetailsViewModelFactory(
        private val recipe: DetailRecipeObject,val repository: Repository
       ) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(recipe,repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}