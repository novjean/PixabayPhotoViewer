package com.novatech.pixabayphotoviewer.data.repository

import androidx.lifecycle.LiveData
import com.novatech.pixabayphotoviewer.common.Resource
import com.novatech.pixabayphotoviewer.data.local.UserEntity
import com.novatech.pixabayphotoviewer.data.remote.dto.ImageResponse

interface AppRepository {

    suspend fun insertUser(userEntity: UserEntity)

    suspend fun deleteUser(userEntity: UserEntity)

    fun observeUsers(): LiveData<List<UserEntity>>

    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>
}