package com.novatech.pixabayphotoviewer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    val email: String,
    val password: String,
    val age: Int? = null // Nullable for login use case
)
