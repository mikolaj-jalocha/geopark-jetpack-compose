package com.geoparkcompose

import com.geoparkcompose.data.CardData
import com.geoparkcompose.data.DataOrException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LocationsRepository @Inject constructor(
    private val queryLocationsByName: Query
) {
    suspend fun getProductsFromFirestore(): DataOrException<List<CardData>, Exception> {
        val dataOrException = DataOrException<List<CardData>, Exception>()
        try {
            dataOrException.data = queryLocationsByName.get().await().map { document ->
                document.toObject(CardData::class.java)
            }
        } catch (e: FirebaseFirestoreException) {
            dataOrException.e = e
        }
        return dataOrException
    }
}