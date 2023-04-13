package com.arduino.access.room.data

import android.util.Log
import com.arduino.access.room.domain.model.Room
import com.arduino.access.utils.DEBUG
import com.arduino.access.utils.observeValue
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import com.it.access.util.Resource
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.transform

class RoomRep(
    private val db: DatabaseReference
) {
    fun getRooms() = db.observeValue().transform {
        if(it is Resource.Success) {
            val res = it.data.children.map { room ->
                Room(room.key!!, room.getValue<RoomResp>()!!)
            }
            emit(Resource.Success(res))
        } else if (it is Resource.Error)
            emit(Resource.Error(it.message))
    }
}