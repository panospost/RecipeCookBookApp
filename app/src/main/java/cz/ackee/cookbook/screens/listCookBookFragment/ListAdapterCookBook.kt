package cz.ackee.cookbook.screens.listCookBookFragment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.ackee.cookbook.databinding.LayoutItemOfListCookBookBinding
import cz.ackee.cookbook.models.RecipesObject

class ListAdapterCookBook( val clickListener: ItemListListener) : PagedListAdapter<RecipesObject, ListAdapterCookBook.ViewHolder>(RecipesDiffUtill()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position)!!)
    }

    class ViewHolder private constructor(val binding: LayoutItemOfListCookBookBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: ItemListListener, item: RecipesObject) {
            binding.hourToMake.text = item.duration
            binding.aboutRecipe.text = item.name
            //binding.clickListener = clickListener
            binding.root.setOnClickListener {
                Log.i("11click", "i am clicked")

                clickListener.onClick(item.id)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutItemOfListCookBookBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }


}

class RecipesDiffUtill : DiffUtil.ItemCallback<RecipesObject>() {
    override fun areItemsTheSame(oldItem: RecipesObject, newItem: RecipesObject): Boolean {
        return oldItem.id === newItem.id
    }

    override fun areContentsTheSame(oldItem: RecipesObject, newItem: RecipesObject): Boolean {
        return oldItem == newItem
    }

}

class ItemListListener(val clickListener: (recipeId: String) -> Unit) {
    fun onClick(recipe1: String) = clickListener(recipe1)
}

