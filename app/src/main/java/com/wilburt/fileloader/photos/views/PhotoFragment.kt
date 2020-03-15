package com.wilburt.fileloader.photos.views

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource
import com.wilburt.fileloader.R
import com.wilburt.fileloader.common.*
import com.wilburt.fileloader.photos.models.Photo
import com.wilburt.fileloader.photos.models.PhotosResponse
import com.wilburt.fileloader.photos.models.Status
import com.wilburt.fileloader.photos.viewmodels.PhotoViewModel
import kotlinx.android.synthetic.main.info_layout.*
import kotlinx.android.synthetic.main.photo_fragment.*
import timber.log.Timber
import javax.inject.Inject

class PhotoFragment : Fragment(), View.OnClickListener {

    lateinit var viewModel: PhotoViewModel
    private var animator: Animator? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var idlingResource : EspressoIdlingResource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as App).appComponent.inject(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.photo_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PhotoViewModel::class.java)
        setupViews()
        observeData()
    }

    private fun observeData() {
        Timber.i("observeData: ")
        idlingResource.increment()
        viewModel.photosResponse.observeForever {
            animator?.cancel()
            when (it.status) {
                Status.Loading -> {
                    Timber.i("observeData: Loading")
                    animator = if (infoContent.visibility == View.VISIBLE) {
                        progressBar.crossFadeWidth(infoContent)
                    } else {
                        progressBar.crossFadeWidth(photosRV)
                    }
                }
                Status.Success -> {
                    Timber.i("observeData: Success ${it.photos.size}")
                    (photosRV.adapter as PhotoAdapter).updateItems(it.photos)
                    animator = photosRV.crossFadeWidth(progressBar)
                    infoContent.fadeOut()
                    Thread.sleep(2000)
                    idlingResource.decrement()

                }
                Status.Error -> {
                    Timber.i("observeData: Error")
                    animator = infoContent.crossFadeWidth(progressBar)
                    idlingResource.decrement()
                }
            }

        }
        viewModel.fetchPhotos()
    }

    private fun setupViews() {
        photosRV.adapter = PhotoAdapter()
        retryButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.retryButton -> viewModel.fetchPhotos()
        }
    }

    override fun onDestroyView() {
        animator?.cancel()
        super.onDestroyView()
    }


}
