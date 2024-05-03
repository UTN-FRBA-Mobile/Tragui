package com.vasco.tragui.dataManagment

public class Cocktail {
    val name: String
    val id_firebase: String
    val id_api: String
    var alternativeName: String? = null
    val category: String
    var IBA: String? = null
    val glassType: String
    var image: String? = null
    val ingredients: List<String>
    var instructions: String? = null
    val measures: List<String>
    var tags: String? = null
    val thumbnail: String // string con link
    val type: String

    constructor(name: String, id_firebase: String, id_api: String, alternativeName: String, category: String, IBA: String, glassType: String, image: String, ingredients: List<String>, instructions: String, measures:List<String>, tags: String, thumbnail: String, type: String ) {
        this.name = name
        this.id_firebase = id_firebase
        this.id_api = id_api
        this.alternativeName = alternativeName
        this.category = category
        this.IBA = IBA
        this.glassType = glassType
        this.image = image
        this.ingredients = ingredients
        this.instructions = instructions
        this.measures = measures
        this.tags = tags
        this.thumbnail = thumbnail
        this.type = type
    }
}
