package cz.ackee.cookbook.screens.detailsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cz.ackee.cookbook.models.DetailRecipeObject


class DetailsViewModelFactory(
        private val recipe: DetailRecipeObject
       ) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(recipe) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}