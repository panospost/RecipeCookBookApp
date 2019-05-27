package cz.ackee.cookbook.screens.addScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cz.ackee.cookbook.network.NetworkRecipeDataSource


class AddViewModelFactory(
        val nwdataSource: NetworkRecipeDataSource
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddFragmentViewModel::class.java)) {
            return AddFragmentViewModel(nwdataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}