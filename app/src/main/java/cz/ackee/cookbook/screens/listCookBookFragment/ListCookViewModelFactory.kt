package cz.ackee.cookbook.screens.listCookBookFragment

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cz.ackee.cookbook.localDatabase.GetAllRecipesDao
import cz.ackee.cookbook.localDatabase.Repository


class ListCookViewModelFactory(
        val repository: Repository
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListCookBookViewModel::class.java)) {
            return ListCookBookViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}