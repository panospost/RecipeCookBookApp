package cz.ackee.cookbook.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes_local_database_version")
data class RecipesObject(
        @PrimaryKey
        var id: String,
        @ColumnInfo(name = "name")
        var name: String,
        @ColumnInfo(name = "duration")
        var duration: String,
        @ColumnInfo(name = "score")
        var score: Int)
