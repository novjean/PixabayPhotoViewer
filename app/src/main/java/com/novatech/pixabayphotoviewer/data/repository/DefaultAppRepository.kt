package com.novatech.pixabayphotoviewer.data.repository

import androidx.lifecycle.LiveData
import com.novatech.pixabayphotoviewer.common.Resource
import com.novatech.pixabayphotoviewer.data.local.UserDao
import com.novatech.pixabayphotoviewer.data.local.UserEntity
import com.novatech.pixabayphotoviewer.data.remote.PixabayAPI
import com.novatech.pixabayphotoviewer.data.remote.dto.PixabayResponse
import javax.inject.Inject

class DefaultAppRepository @Inject constructor(
    private val userDao: UserDao,
    private val pixabayAPI: PixabayAPI
): AppRepository {
    override suspend fun insertUser(userEntity: UserEntity) {
        userDao.insertUser(userEntity)
    }

    override suspend fun deleteUser(userEntity: UserEntity) {
        userDao.deleteUser(userEntity)
    }

    override fun observeUsers(): LiveData<List<UserEntity>> {
        return userDao.observeAllUsers()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<PixabayResponse> {
        return try {
//            val response = pixabayAPI.fetchImages(imageQuery)
//
//            if(response.isSuccessful){
//                response.body()?.let {
//                    return@let Resource.success(it)
//                } ?: Resource.error("An unknown error occurred", null)
//            } else {
//                return Resource.error("An unknown error occurred", null)
//            }
            Resource.error("Unimplemented", null)
        } catch(e: Exception){
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }
    }
}