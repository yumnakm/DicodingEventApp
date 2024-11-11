package com.dicoding.dicodingeventapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EventEntity(
    @PrimaryKey
    var id: String = "",
    var mediaCover: String,
    var title: String,
    var owner: String,
    var city: String,
    var category: String,
    var quota: Int,
    var register: Int,
    var beginTime: String,
    var eventStatus: Boolean,
    var isFavorite: Boolean,
)