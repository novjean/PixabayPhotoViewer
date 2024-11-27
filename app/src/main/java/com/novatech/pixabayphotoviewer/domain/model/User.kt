package com.novatech.pixabayphotoviewer.domain.model

data class User(
    val email: String,
    val password: String,
    val age: Int? = null // Nullable for login use case
)
