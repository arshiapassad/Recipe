package academy.nouri.s4_recipeapp.data.database.entity

import academy.nouri.s4_recipeapp.models.detail.ResponseDetail
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipe.utils.Constants

@Entity(tableName = Constants.FAVORITE_TABLE_NAME)
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val result: ResponseDetail
)
