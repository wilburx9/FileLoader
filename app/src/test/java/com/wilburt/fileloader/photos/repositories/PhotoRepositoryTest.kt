package com.wilburt.fileloader.photos.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.wilburt.fileloader.photos.common.MockResponseFileReader
import com.wilburt.fileloader.photos.datasource.PhotosDataSource
import com.wilburt.fileloader.photos.models.Status
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


/**
 * Created by Wilberforce on 2020-02-25 at 01:16.
 */
@RunWith(JUnit4::class)
class PhotoRepositoryTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var dataSource: PhotosDataSource
    private lateinit var repository: PhotoRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = PhotoRepository(dataSource)

    }

    @After
    fun tearDown() {
    }

    @Test
    fun `photos is empty when data source returns invalid data`() {

        every { dataSource.getJson() } returns ""

        val actual = repository.fetchPhotos()

        assert(actual.photos.isEmpty())
    }

    @Test
    fun `response status is error when data source returns invalid data`() {

        every { dataSource.getJson() } returns ""

        val actual = repository.fetchPhotos()

        assert(actual.status == Status.Error)
    }

    @Test
    fun `response status is success when data source returns valid data`() {

        every { dataSource.getJson() } returns MockResponseFileReader("double_photos_response.json").content

        val actual = repository.fetchPhotos()

        assert(actual.status == Status.Success)
    }

    @Test
    fun `photos is same length as data in valid json`() {

        every { dataSource.getJson() } returns MockResponseFileReader("double_photos_response.json").content

        val actual = repository.fetchPhotos()

        assert(actual.photos.size == 2)
    }
}