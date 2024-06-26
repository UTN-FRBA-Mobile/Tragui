package com.vasco.tragui.dataManagment

import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import java.util.Locale
import java.util.logging.Logger

object FirebaseGetter {
    val logger = Logger.getLogger("FirebaseGetter")
    val db = Firebase.firestore
    fun pruebaLog(){
        val hola = db.collection("coctail").whereArrayContainsAny("ingredients", listOf("Gin"))
            .get().addOnSuccessListener { document ->
            if (document != null) {
                document.documents.forEach({ trago -> logger.info("${trago["name"]}") })
            } else {
                logger.info("${document}")
            }
        }
    }

    suspend fun getCocktailsByBottles(bottles: List<String>): MutableList<Cocktail> {
        var tragosDisponibles: MutableList<Cocktail> = mutableListOf()
        var tragos = db.collection("coctail").whereArrayContainsAny("ingredients", bottles).get().await()
        tragos.forEach({ trago ->
                        logger.info("${trago.id}")
                        var ingredients = trago["ingredients"] as List<String>
                        var measures = trago["measures"] as List<String>
                        var cocktail = Cocktail("${trago["name"]}", trago.id, "${trago["id_api"]}", "${trago["alternativeName"]}", "${trago["category"]}", "${trago["IBA"]}","${trago["glassType"]}", "${trago["image"]}", ingredients, "${trago["instructions"]}", measures, "${trago["tags"]}","${trago["thumbnail"]}","${trago["type"]}")
                        tragosDisponibles.add(cocktail)
        })

        return tragosDisponibles
    }

    suspend fun getCocktailsByName(nombre: String): MutableList<Cocktail> {
        var tragosBuscados: MutableList<Cocktail> = mutableListOf()
        var name = nombre.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        var tragos = db.collection("coctail").where(Filter.greaterThanOrEqualTo("name",name)).where(Filter.lessThanOrEqualTo("name",name+"\uf8ff")).get().await()
        tragos.forEach({ trago ->

            var ingredients = trago["ingredients"] as List<String>
            var measures = trago["measures"] as List<String>
            var cocktail = Cocktail("${trago["name"]}", trago.id, "${trago["id_api"]}", "${trago["alternativeName"]}", "${trago["category"]}", "${trago["IBA"]}","${trago["glassType"]}", "${trago["image"]}", ingredients, "${trago["instructions"]}", measures, "${trago["tags"]}","${trago["thumbnail"]}","${trago["type"]}")
            tragosBuscados.add(cocktail)
        })

        tragosBuscados.forEach({ trago -> logger.info(trago.name)})
        return tragosBuscados
    }

    suspend fun getCocktailById(id: String): Cocktail{
        var tragosBuscados: MutableList<Cocktail> = mutableListOf()
        var trago = db.collection("coctail").document(id).get().await()
        var ingredients = trago["ingredients"] as List<String>
        var measures = trago["measures"] as List<String>
        return Cocktail("${trago["name"]}", trago.id, "${trago["id_api"]}", "${trago["alternativeName"]}", "${trago["category"]}", "${trago["IBA"]}","${trago["glassType"]}", "${trago["image"]}", ingredients, "${trago["instructions"]}", measures, "${trago["tags"]}","${trago["thumbnail"]}","${trago["type"]}")
    }

    suspend fun getBottles(): MutableList<String>{
        var botellasDisponibles: MutableList<String> = mutableListOf()
        var botellas = db.collection("bebidas").get().await()
        botellas.forEach({ botella ->
            botellasDisponibles.add("${botella["nombre"]}")
        })

        return botellasDisponibles
    }


    suspend fun getRandomCocktail(): Cocktail {
        val tragosBuscados: MutableList<Cocktail> = mutableListOf()
        val tragos = db.collection("coctail").get().await()
        tragos.forEach({ trago ->
            var ingredients = trago["ingredients"] as List<String>
            var measures = trago["measures"] as List<String>
            var cocktail = Cocktail("${trago["name"]}", trago.id, "${trago["id_api"]}", "${trago["alternativeName"]}", "${trago["category"]}", "${trago["IBA"]}","${trago["glassType"]}", "${trago["image"]}", ingredients, "${trago["instructions"]}", measures, "${trago["tags"]}","${trago["thumbnail"]}","${trago["type"]}")
            tragosBuscados.add(cocktail)
        })
        // Return a random cocktail or null if the list is empty
        return tragosBuscados.random()
    }

}