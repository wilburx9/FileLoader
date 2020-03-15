package com.wilburt.fileloader.photos.views


import android.service.autofill.Validators.not
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wilburt.fileloader.R
import com.wilburt.fileloader.photos.models.Status
import com.wilburt.fileloader.photos.viewmodels.PhotoViewModel
import com.wilburt.fileloader.photos.views.common.Utils.atPosition
import io.mockk.spyk
import org.hamcrest.Matcher
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
    private val TEST_TITLE ="Lucas Gallone"
    private lateinit var photoFragment : PhotoFragment

    @Before
    fun setUp() {
        scenario = launchFragmentInContainer<PhotoFragment>()
        scenario.onFragment {
            this.photoFragment = it
            //The IdlingRegistry help handle loooooooong running background thread.
            //It will wait until the threads are done
            IdlingRegistry.getInstance().register(photoFragment.idlingResource.getIdlingResource())
            viewModel = spyk(it.viewModel)
        }
    }

    /*
     The IdlingResources will be very effective when the API is slow to respond
     Also, I removed {val value = viewModel.photosResponse.value!!} because during the test, the list
     might be null
     */
    @Test
    fun testSuccessResponse() {

        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))

        onView(withId(R.id.photosRV)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(5, scrollTo()))
            .check(matches(atPosition(5, TEST_TITLE)))

        onView(withId(R.id.infoContent)).check(matches(withEffectiveVisibility(Visibility.GONE)))

    }

    @Test
    fun testFailedResponse(){
        //This will pass only if the network call failed. Else, it will never pass
        onView(withId(R.id.infoContent)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }


    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(photoFragment.idlingResource.getIdlingResource())
    }

}

