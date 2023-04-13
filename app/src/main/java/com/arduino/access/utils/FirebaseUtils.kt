package com.arduino.access.utils

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.it.access.util.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun DatabaseReference.observeValue() =
    callbackFlow {
        val listener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
//                close(error.toException())
                trySend(Resource.Error(error.message))
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                trySend(Resource.Success(snapshot))
            }
        }
        addValueEventListener(listener)
        awaitClose { removeEventListener(listener) }
    }