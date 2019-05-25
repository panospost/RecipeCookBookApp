package cz.ackee.cookbook.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DetailRecipeObject(
        var name: String,
        var duration: Int,
        var description: String,
        var info: String,
        var ingredients: MutableList<String>,
var score: Int): Parcelable