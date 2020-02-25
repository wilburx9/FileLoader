package com.wilburt.fileloader.photos.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.whenever
import com.wilburt.fileloader.photos.common.MockResponseFileReader
import com.wilburt.fileloader.photos.datasource.PhotosDataSource
import com.wilburt.fileloader.photos.models.Status
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.stubbing.Answer


/**
 * Created by Wilberforce on 2020-02-25 at 01:16.
 */
@RunWith(MockitoJUnitRunner::class)
class PhotoRepositoryTest {

    @get:Rule
    public val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dataSource: PhotosDataSource
    private lateinit var repository: PhotoRepository

    @Before
    fun setUp() {
        repository = PhotoRepository(dataSource)

    }

    @After
    fun tearDown() {
    }

    @Test
    fun `photos is empty when data source returns invalid data`() {
        whenever(dataSource.getJson()).thenAnswer(Answer {
            return@Answer ""
        })
        runBlocking {
            repository.fetchPhotos()
        }
        assert(repository.photosResponse.value!!.photos.isEmpty())
    }

    @Test
    fun `response status is error when data source returns invalid data`() {
        whenever(dataSource.getJson()).thenAnswer(Answer {
            return@Answer ""
        })
        runBlocking {
            repository.fetchPhotos()
        }
        assert(repository.photosResponse.value!!.status == Status.Error)
    }

    @Test
    fun `response status is success when data source returns valid data`() {

        whenever(dataSource.getJson()).thenAnswer(Answer {
            return@Answer MockResponseFileReader("double_photos_response.json").content
        })
        runBlocking {
            repository.fetchPhotos()
        }

        assert(repository.photosResponse.value!!.status == Status.Success)
    }

    @Test
    fun `photos is same length as data in valid json`() {

        whenever(dataSource.getJson()).thenAnswer(Answer {
            return@Answer MockResponseFileReader("double_photos_response.json").content
        })
        runBlocking {
            repository.fetchPhotos()
        }
        assert(repository.photosResponse.value!!.photos.size == 2)
    }
}