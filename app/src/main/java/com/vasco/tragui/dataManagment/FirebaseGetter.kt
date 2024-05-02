package com.vasco.tragui.dataManagment

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import java.util.logging.Logger

object FirebaseGetter {
    val logger = Logger.getLogger("VascoLogger")
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
                        var ingredients = trago["ingredients"] as List<String>
                        var measures = trago["measures"] as List<String>
                        var cocktail = Cocktail("${trago["name"]}", "${trago["id_api"]}", "${trago["alternativeName"]}", "${trago["category"]}", "${trago["IBA"]}","${trago["glassType"]}", "${trago["image"]}", ingredients, "${trago["instructions"]}", measures, "${trago["tags"]}","${trago["thumbnail"]}","${trago["type"]}")
                        //logger.info(cocktail.name)
                        tragosDisponibles.add(cocktail)
        })

        //tragosDisponibles.forEach({ trago -> logger.info(trago.name)})
        return tragosDisponibles
    }

    suspend fun getCocktailsByName(name: String): MutableList<Cocktail> {
        var tragosBuscados: MutableList<Cocktail> = mutableListOf()
        var tragos = db.collection("coctail").whereEqualTo("name", name).get().await()
        tragos.forEach({ trago ->
            var ingredients = trago["ingredients"] as List<String>
            var measures = trago["measures"] as List<String>
            var cocktail = Cocktail("${trago["name"]}", "${trago["id_api"]}", "${trago["alternativeName"]}", "${trago["category"]}", "${trago["IBA"]}","${trago["glassType"]}", "${trago["image"]}", ingredients, "${trago["instructions"]}", measures, "${trago["tags"]}","${trago["thumbnail"]}","${trago["type"]}")
            //logger.info(cocktail.name)
            tragosBuscados.add(cocktail)
        })

        tragosBuscados.forEach({ trago -> logger.info(trago.name)})
        return tragosBuscados
    }

    suspend fun getBottles(): MutableList<String>{
        var botellasDisponibles: MutableList<String> = mutableListOf()
        var botellas = db.collection("bebidas").get().await()
        botellas.forEach({ botella ->
            botellasDisponibles.add("${botella["nombre"]}")
        })

        return botellasDisponibles
    }


}