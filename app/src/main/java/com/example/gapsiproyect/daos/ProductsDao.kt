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
   @SerializedName("id")
    var id : Int ,
   @SerializedName("name")
    var name: String? ,
   @SerializedName("tagline")
    var tagline: String? ,
   @SerializedName("first_brewed")
    var first_brewed: String? ,
   @SerializedName("description")
    var description: String? ,
   @SerializedName("image_url")
    var image_url: String? ,
   @SerializedName("abv")
    var abv : Double,
   @SerializedName("ibu")
    var ibu : Double,
   @SerializedName("target_fg")
    var target_fg :Int,
   @SerializedName("target_og")
    var target_og :Double,
   @SerializedName("ebc")
    var ebc :Int,
   @SerializedName("srm")
    var srm : Double,
   @SerializedName("ph")
    var ph : Double,
   @SerializedName("attenuation_level")
    var attenuation_level :Double,
   @SerializedName("volume")
    var volume: Volume? ,
   @SerializedName("boil_volume")
    var boil_volume: BoilVolume? ,
   @SerializedName("method")
    var method: Method? ,
   @SerializedName("ingredients")
    var ingredients: Ingredients? ,
   @SerializedName("food_pairing")
    var food_pairing: List<String>? ,
   @SerializedName("brewers_tips")
    var brewers_tips: String? ,
   @SerializedName("contributed_by")
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



