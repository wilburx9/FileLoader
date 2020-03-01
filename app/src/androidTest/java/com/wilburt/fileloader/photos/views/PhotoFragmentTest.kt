package com.wilburt.fileloader.photos.views


import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wilburt.fileloader.R
import com.wilburt.fileloader.photos.models.Status
import com.wilburt.fileloader.photos.viewmodels.PhotoViewModel
import com.wilburt.fileloader.common.EspressoIdlingResource
import com.wilburt.fileloader.photos.views.common.Utils.atPosition
import io.mockk.spyk
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by Wilberforce on 2020-02-25 at 20:38.
 */
@RunWith(AndroidJUnit4::class)
class PhotoFragmentTest {
    private lateinit var scenario: FragmentScenario<PhotoFragment>
    private lateinit var viewModel: PhotoViewModel

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource())
        scenario = launchFragmentInContainer<PhotoFragment>()
        scenario.onFragment {
            viewModel = spyk(it.viewModel)
        }
    }

    @Test
    fun testResponse() {
        // For some reason, Idling resources doesn't seem to be working.
        // Though discouraged, I will be using thread sleeping for now.
        Thread.sleep(4000)
        val value = viewModel.photosResponse.value!!
        if (value.status == Status.Success && value.photos.isNotEmpty()) {
            onView(withId(R.id.photosRV))
                .check(matches(atPosition(0, hasDescendant(withText(value.photos.first().photographer)))))
        } else if (value.status == Status.Error) {
            onView(withId(R.id.infoContent)).check(matches(isDisplayed()))
        } else {
            onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
        }
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource())
    }

}

