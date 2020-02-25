package com.wilburt.fileloader.photos.views

import android.animation.Animator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.wilburt.fileloader.R
import com.wilburt.fileloader.common.crossFadeWidth
import com.wilburt.fileloader.common.fadeOut
import com.wilburt.fileloader.photos.viewmodels.PhotoViewModel
import com.wilburt.fileloader.photos.models.Status
import kotlinx.android.synthetic.main.info_layout.*
import kotlinx.android.synthetic.main.photo_fragment.*
import timber.log.Timber

class PhotoFragment : Fragment(), View.OnClickListener {

    private lateinit var viewModel: PhotoViewModel
    private var animator: Animator? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.photo_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PhotoViewModel::class.java)
        setupViews()
        observeData()
    }

    private fun observeData() {
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

                }
                Status.Error -> {
                    Timber.i("observeData: Error")
                   animator = infoContent.crossFadeWidth(progressBar)
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
