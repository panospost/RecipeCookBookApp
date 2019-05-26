package cz.ackee.cookbook.screens.detailsScreen


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.ackee.cookbook.models.DetailRecipeObject


class DetailsViewModel(val recipe: DetailRecipeObject): ViewModel(){
    private val _starRated = MutableLiveData<Int>()
    val starsRated: LiveData<Int>
        get() = _starRated

    fun rateThis5star(){
        _starRated.value = 5
    }
    fun rateThis4star(){
        _starRated.value = 4
    }
    fun rateThis3star(){
        _starRated.value = 3
    }
    fun rateThis2star(){
        _starRated.value = 2
    }
    fun rateThis1star(){
        _starRated.value = 1
    }

}