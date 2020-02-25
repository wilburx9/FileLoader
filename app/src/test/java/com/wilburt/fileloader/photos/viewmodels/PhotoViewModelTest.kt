package com.wilburt.fileloader.photos.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.wilburt.fileloader.photos.common.MainCoroutineRule
import com.wilburt.fileloader.photos.common.TestContextProvider
import com.wilburt.fileloader.photos.models.Photo
import com.wilburt.fileloader.photos.models.PhotosResponse
import com.wilburt.fileloader.photos.models.Status
import com.wilburt.fileloader.photos.repositories.PhotoRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by Wilberforce on 2020-02-25 at 03:15.
 */

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class PhotoViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()


    @MockK
    lateinit var repository: PhotoRepository
    lateinit var viewModel: PhotoViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = PhotoViewModel(TestContextProvider(), repository)
    }

    @Test
    fun `photos is empty and status is error when data source returns invalid data`() =
        mainCoroutineRule.runBlockingTest {

            val expected = PhotosResponse(Status.Error)
            every { repository.fetchPhotos() } returns expected

            viewModel.fetchPhotos()
            val actual = viewModel.photosResponse.value

            assert(actual == expected)

        }

    @Test
    fun `photos has actual length of data in json and status is success when data source returns invalid data`() =
        mainCoroutineRule.runBlockingTest {

            val expected = PhotosResponse(Status.Error, getDummyPhotos())

            every { repository.fetchPhotos() } returns expected

            viewModel.fetchPhotos()
            val actual = viewModel.photosResponse.value

            assert(actual == expected)

        }

    private fun getDummyPhotos(): List<Photo> {
        return listOf(
            Photo("1", "Ken Jon", "http://g.jpg", "http://g.png"),
            Photo("5", "Adam Sophia", "http://q.jpg", "http://f.png"),
            Photo("3", "Judas Mark", "http://1.jpg", "http://l.png")
        )
    }
}
