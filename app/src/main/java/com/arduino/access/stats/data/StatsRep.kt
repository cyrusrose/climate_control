package com.arduino.access.stats.data

import com.arduino.access.stats.domain.model.Stats
import com.arduino.access.utils.observeValue
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import com.it.access.util.Resource
import kotlinx.coroutines.flow.*

class StatsRep(
    private val db: DatabaseReference
) {
    fun getStats(name: String) = db.child(name)
        .observeValue().transform {
            if(it is Resource.Success) {
                val params = it.data.children.map { param ->
                    Stats(
                        id = param.key!!,
                        resp = param.getValue<StatsResp>()!!
                    )
                }
                emit(Resource.Success(params))
            }

            if (it is Resource.Error)
                emit(Resource.Error(it.message))
        }
}