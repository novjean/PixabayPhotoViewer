package com.novatech.pixabayphotoviewer.data.datasource

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.novatech.pixabayphotoviewer.data.remote.PixabayAPI
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(AndroidJUnit4::class)
class RemoteDataSourceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var pixabayAPI: PixabayAPI
    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setUp() {
        // Start MockWebServer
        mockWebServer = MockWebServer()
        mockWebServer.start()

        // Set up Retrofit and MockWebServer
        val baseUrl = mockWebServer.url("https://pixabay.com").toString()
        pixabayAPI = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PixabayAPI::class.java)

        // Set up RemoteDataSource with mock API
        remoteDataSource = RemoteDataSource(pixabayAPI)
    }

    @Test
    fun testFetchImages_success() = runBlocking {
        // When: Fetch images from the data source
        val result = remoteDataSource.fetchImages(1)

        // Log the actual result for debugging
        Log.d("RemoteDataSourceTest", "Result: $result")

        // Then: Verify the result using Truth assertions
        assertThat(result.isSuccess).isTrue()  // Ensure success result

        val images = result.getOrNull()
        assertThat(images).hasSize(20)
    }

    @Test
    fun testFetchImagesBadQuery_failure() = runBlocking {
        // When: Fetch images from the data source
        val result = remoteDataSource.fetchImages(-1)

        assertThat(result.isFailure).isTrue()

        // Verify that the error is of type Exception
        val exception = result.exceptionOrNull()
        assertThat(exception).isInstanceOf(HttpException::class.java)
    }

    @After
    fun tearDown() {
        // Shut down the MockWebServer
        mockWebServer.shutdown()
    }
}
