package com.example.gapsiproyect.daos

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

/**
 * @id : StringNumber
 * @Rating :the Rating number of the product
 * @price :the Price of the product
 * @image : Image Of the product
 * @title : Title Product
 *
 * @Parcelize
data class ProductsDao(
val id : String,
val rating : BigDecimal,
val price : BigDecimal,
val image : String,
val title : String
): Parcelable

 *
 *
 *
 *
 */
// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString), Root.class); */

@Serializable
data class ProductsDao (
    @field:SerializedName("id")
    var id : Int ,
    @field:SerializedName("name")
    var name: String? ,
    @field:SerializedName("tagline")
    var tagline: String? ,
    @field:SerializedName("first_brewed")
    var first_brewed: String? ,
    @field:SerializedName("description")
    var description: String? ,
    @field:SerializedName("image_url")
    var image_url: String? ,
    @field:SerializedName("abv")
    var abv : Double,
    @field:SerializedName("ibu")
    var ibu : Double,
    @field:SerializedName("target_fg")
    var target_fg :Int,
    @field:SerializedName("target_og")
    var target_og :Double,
    @field:SerializedName("ebc")
    var ebc :Int,
    @field:SerializedName("srm")
    var srm : Double,
    @field:SerializedName("ph")
    var ph : Double,
    @field:SerializedName("attenuation_level")
    var attenuation_level :Double,
    @field:SerializedName("volume")
    var volume: Volume? ,
    @field:SerializedName("boil_volume")
    var boil_volume: BoilVolume? ,
    @field:SerializedName("method")
    var method: Method? ,
    @field:SerializedName("ingredients")
    var ingredients: Ingredients? ,
    @field:SerializedName("food_pairing")
    var food_pairing: List<String>? ,
    @field:SerializedName("brewers_tips")
    var brewers_tips: String? ,
    @field:SerializedName("contributed_by")
    var contributed_by: String?
)



@Serializable
data class Volume (
    var value :Int,
    var unit: String?
)

@Serializable
data class BoilVolume (
    var value : Int,
    var unit: String?
)

@Serializable
data class Temp (
    var value : Int,
    var unit: String?
)

@Serializable
data class MashTemp (
    var temp: Temp? ,
    var duration : Int
)

@Serializable
data class Fermentation (
    var temp: Temp?
)

@Serializable
data class Method (
    var mash_temp: List<MashTemp>? ,
    var fermentation: Fermentation? ,
    var twist: String?
)

@Serializable
data class Amount (
    var value :Double,
    var unit: String?
)

@Serializable
data class Malt (
    var name: String? ,
    var amount: Amount? ,
)

@Serializable
data class Hop (
    var name: String?,
    var amount: Amount? ,
    var add: String? ,
    var attribute: String?
)

@Serializable
data class Ingredients (
    var malt: List<Malt>? = null,
    var hops: List<Hop>? = null,
    var yeast: String? = null
)



