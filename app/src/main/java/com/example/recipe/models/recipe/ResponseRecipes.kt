package com.example.recipe.models.recipe


import com.google.gson.annotations.SerializedName

data class ResponseRecipes(
    @SerializedName("number")
    val number: Int?, // 10
    @SerializedName("offset")
    val offset: Int?, // 0
    @SerializedName("results")
    val results: List<Result>?,
    @SerializedName("totalResults")
    val totalResults: Int? // 5158
) {
    data class Result(
        @SerializedName("aggregateLikes")
        val aggregateLikes: Int?, // 32767
        @SerializedName("cheap")
        val cheap: Boolean?, // false
        @SerializedName("cookingMinutes")
        val cookingMinutes: Int?, // 20
        @SerializedName("creditsText")
        val creditsText: String?, // blogspot.com
        @SerializedName("cuisines")
        val cuisines: List<String?>?,
        @SerializedName("dairyFree")
        val dairyFree: Boolean?, // true
        @SerializedName("diets")
        val diets: List<String?>?,
        @SerializedName("dishTypes")
        val dishTypes: List<String?>?,
        @SerializedName("gaps")
        val gaps: String?, // no
        @SerializedName("glutenFree")
        val glutenFree: Boolean?, // true
        @SerializedName("healthScore")
        val healthScore: Int?, // 0
        @SerializedName("id")
        val id: Int?, // 945221
        @SerializedName("image")
        val image: String?, // https://img.spoonacular.com/recipes/945221-312x231.jpg
        @SerializedName("imageType")
        val imageType: String?, // jpg
        @SerializedName("license")
        val license: String?, // CC BY-SA 3.0
        @SerializedName("lowFodmap")
        val lowFodmap: Boolean?, // false
        @SerializedName("occasions")
        val occasions: List<String?>?,
        @SerializedName("preparationMinutes")
        val preparationMinutes: Int?, // 20
        @SerializedName("pricePerServing")
        val pricePerServing: Double?, // 23.36
        @SerializedName("readyInMinutes")
        val readyInMinutes: Int?, // 45
        @SerializedName("servings")
        val servings: Int?, // 16
        @SerializedName("sourceName")
        val sourceName: String?, // blogspot.com
        @SerializedName("sourceUrl")
        val sourceUrl: String?, // http://watching-what-i-eat.blogspot.com/2012/06/peanut-butter-banana-oat-breakfast.html
        @SerializedName("spoonacularScore")
        val spoonacularScore: Double?, // 36.30006790161133
        @SerializedName("spoonacularSourceUrl")
        val spoonacularSourceUrl: String?, // https://spoonacular.com/watching-what-i-eat-peanut-butter-banana-oat-breakfast-cookies-with-carob-chocolate-chips-945221
        @SerializedName("summary")
        val summary: String?, // If you want to add more <b>gluten free and dairy free</b> recipes to your repertoire, Watching What I Eat: Peanut Butter Bananan Oat Breakfast Cookies with Carob / Chocolate Chips might be a recipe you should try. This recipe makes 16 servings with <b>103 calories</b>, <b>4g of protein</b>, and <b>5g of fat</b> each. For <b>23 cents per serving</b>, this recipe <b>covers 3%</b> of your daily requirements of vitamins and minerals. This recipe from watching-what-i-eat.blogspot.com has 902934 fans. Head to the store and pick up bananas, vanillan extract, chocolate chips, and a few other things to make it today. It works well as a very affordable morn meal. From preparation to the plate, this recipe takes roughly <b>roughly 45 minutes</b>. With a spoonacular <b>score of 44%</b>, this dish is good. If you like this recipe, you might also like recipes such as <a href="https://spoonacular.com/recipes/peanut-butter-banana-oat-breakfast-cookies-with-carob-chocolate-chips-666592">Peanut Butter Bananan Oat Breakfast Cookies with Carob/Chocolate Chips</a>, <a href="https://spoonacular.com/recipes/peanut-butter-banana-honey-oat-chocolate-chip-cookies-618265">Peanut Butter, Banana, Honey & Oat Chocolate Chip Cookies</a>, and <a href="https://spoonacular.com/recipes/banana-oatmeal-cookies-with-peanut-butter-and-chocolate-chips-203278">Bananan Oatmeal Cookies with Peanut Butter and Chocolate Chips</a>.
        @SerializedName("sustainable")
        val sustainable: Boolean?, // false
        @SerializedName("title")
        val title: String?, // Watching What I Eat: Peanut Butter Banana Oat Breakfast Cookies with Carob / Chocolate Chips
        @SerializedName("vegan")
        val vegan: Boolean?, // false
        @SerializedName("vegetarian")
        val vegetarian: Boolean?, // false
        @SerializedName("veryHealthy")
        val veryHealthy: Boolean?, // false
        @SerializedName("veryPopular")
        val veryPopular: Boolean?, // true
        @SerializedName("weightWatcherSmartPoints")
        val weightWatcherSmartPoints: Int? // 3
    )
}