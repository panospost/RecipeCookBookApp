package cz.ackee.cookbook

import android.widget.TextView
import androidx.databinding.BindingAdapter
import cz.ackee.cookbook.models.DetailRecipeObject

@BindingAdapter("intDurationToStringConvert")
fun TextView.intDurationToStringConvert(item: DetailRecipeObject?) {
    item?.let {
        val x = item.duration.toString() + " min"
        text  = x
    }
}