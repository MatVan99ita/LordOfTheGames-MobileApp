package com.example.lordofthegames.Community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lordofthegames.Utilities
import com.example.lordofthegames.databinding.FragmentAddDiscussionBinding


class DiscussionCreateFragment: Fragment() {


    private lateinit var bind: FragmentAddDiscussionBinding
    private lateinit var viewm: DiscussionViewModel
    private var isAllFabsVisible = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewm = ViewModelProvider(requireActivity())[DiscussionViewModel::class.java]
        bind = FragmentAddDiscussionBinding.inflate(layoutInflater, container, false);
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val interpolator = OvershootInterpolator()

        // Now set all the FABs and all the action name
        // texts as GONE

        bind.addPhotoFab.visibility = View.GONE;
        bind.addImageFab.visibility = View.GONE;
        bind.addImageActionText.visibility = View.GONE;
        bind.addPhotoActionText.visibility = View.GONE;

        // make the boolean variable as false, as all the
        // action name texts and all the sub FABs are
        // invisible
        isAllFabsVisible = false;

        // We will make all the FABs and action name texts
        // visible only when Parent FAB button is clicked So
        // we have to handle the Parent FAB button first, by
        // using setOnClickListener you can see below
        bind.fabAttachment.setOnClickListener {
            if (!isAllFabsVisible) {

                // when isAllFabsVisible becomes
                // true make all the action name
                // texts and FABs VISIBLE.

                bind.addImageFab.show()
                bind.addPhotoFab.show()
                bind.addImageActionText.visibility = View.VISIBLE
                bind.addPhotoActionText.visibility = View.VISIBLE

                //bind.fabAttachment.rotation = 90.0F
                ViewCompat.animate(bind.fabAttachment)
                    .rotation(90f)
                    .withLayer()
                    .setDuration(300)
                    .setInterpolator(interpolator).start()

                // make the boolean variable true as
                // we have set the sub FABs
                // visibility to GONE
                isAllFabsVisible = true;
            } else {

                // when isAllFabsVisible becomes
                // true make all the action name
                // texts and FABs GONE.

                bind.addImageFab.hide()
                bind.addPhotoFab.hide()
                bind.addImageActionText.visibility = View.GONE
                bind.addPhotoActionText.visibility = View.GONE


                ViewCompat.animate(bind.fabAttachment)
                    .rotation(0f)
                    .withLayer()
                    .setDuration(300)
                    .setInterpolator(interpolator).start()

                // make the boolean variable false
                // as we have set the sub FABs
                // visibility to GONE
                isAllFabsVisible = false;
            }
        }

        // below is the sample action to handle add person
        // FAB. Here it shows simple Toast msg. The Toast
        // will be shown only when they are visible and only
        // when user clicks on them
        bind.addPhotoFab.setOnClickListener{
            Utilities.showaToast(requireContext(), "PHOTO SESSO")
        }

        // below is the sample action to handle add alarm
        // FAB. Here it shows simple Toast msg The Toast
        // will be shown only when they are visible and only
        // when user clicks on them
        bind.addImageFab.setOnClickListener{
            Utilities.showaToast(requireContext(), "IMAGE SESSO")
        }


    }



}