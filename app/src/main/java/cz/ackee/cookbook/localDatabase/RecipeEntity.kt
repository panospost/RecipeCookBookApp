package cz.ackee.cookbook.localDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "local_recipes_database")
data class RecipeEntity(
        @PrimaryKey
        var id: String,
        @ColumnInfo(name = "name")
        var name: String,
        @ColumnInfo(name = "duration")
        var duration: String,
        @ColumnInfo(name = "score")
        var score: Int)