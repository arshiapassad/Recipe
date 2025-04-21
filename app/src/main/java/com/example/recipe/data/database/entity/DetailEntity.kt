package academy.nouri.s4_recipeapp.data.database.entity

import academy.nouri.s4_recipeapp.models.detail.ResponseDetail
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipe.utils.Constants

@Entity(tableName = Constants.DETAIL_TABLE_NAME)
data class DetailEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val result: ResponseDetail
)
